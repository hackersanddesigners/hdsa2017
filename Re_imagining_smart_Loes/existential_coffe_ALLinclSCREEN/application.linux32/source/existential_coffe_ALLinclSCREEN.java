import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.io.*; 
import processing.io.I2C; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class existential_coffe_ALLinclSCREEN extends PApplet {


// 0.96" 128x64 OLED display ("SKU 346540")
SSD1306 oled;

PImage img;


//String[] questions = loadStrings("data/list.txt");
String[] questions;

public void setup() {
  
  img = loadImage("data/images.png");
  // the display can be set to one of these two addresses: 0x3c (default) or 0x3d
  // (they might be listed as 0x7a and 0x7b on the circuit board)

  // you might need to use a different interface on other SBCs
  oled = new SSD1306("i2c-1", 0x3c);
  
  GPIO.pinMode(4, GPIO.OUTPUT);  //leds on pin 4
  GPIO.pinMode(7, GPIO.INPUT);  //button on pin 3, any pin with internal PULLUP resistor
}

int state = 0;
int start = 0;
int cnt = 0;
boolean ledOn = false;


public void draw() {

  background(255);
  image(img,width/4,0);
  oled.sendImage(get());
  
  // state 0 == pose question
  if (state == 0) {

    println(state);
    // pose question pull thing from array

    String[] questions = loadStrings("data/list.txt");
    say(questions[PApplet.parseInt(random(questions.length))]); 
    //println("new question");

    state = 1;    //move to next state
    println(state);

    // state 1 == wait for answer
  } else if (state == 1) {    
    // also blink
    if (start == 0) {
      start = millis();
    }

    if (millis()-start > 350) {
      if (cnt < 7) {
        ledOn = !ledOn;
        if (ledOn) {
          GPIO.digitalWrite(4, GPIO.HIGH);  //turn led on
        } else {
          GPIO.digitalWrite(4, GPIO.LOW);  //turn led off
        }
        start = millis();
        cnt++;
      }
    }

    if (GPIO.digitalRead(7) == GPIO.LOW) {    //read button state, if button is pressed (LOW)
      state = 2;      //move to next state
      cnt = 0;
      ledOn = !ledOn;
      //println("button 1 pressed");
      GPIO.digitalWrite(4, GPIO.LOW);  //turn led off
      println(state);
      } 


      //state 2 == give a response from the array
    } else if (state == 2) {
      String[] responses = loadStrings("data/response.txt");
      say(responses[PApplet.parseInt(random(responses.length))]); 
      //println("responded");
      delay(3000);
      state = 0;
    }
  }


public void say(String what) {
  println(what);
  //for Pi
  Process p = exec("/bin/sh", "-c", "pico2wave -w /tmp/out.wav \"" + what + "\" && aplay /tmp/out.wav");

  //for testing on mac
  //Process p = exec("/bin/sh", "-c", "say \"" + what + "\"");

  try {
    p.waitFor();
  } 
  catch (Exception e) {
  }
}


// SSD1306 is a small, inexpensive 128x64 pixels monochrome OLED display
// available online as "0.96" 128x64 OLED display", SKU 346540
// or from Adafruit
// datasheet: https://www.adafruit.com/datasheets/SSD1306.pdf

class SSD1306 extends I2C {
  int address;

  // there can be more than one device connected to the bus
  // as long as they have different addresses
  SSD1306(String dev, int address) {
    super(dev);
    this.address = address;
    init();
  }

  protected void init() {
    writeCommand(0xae);         // turn display off
    writeCommand(0xa8, 0x3f);   // set multiplex ratio to the highest setting
    writeCommand(0x8d, 0x14);   // enable charge pump
    writeCommand(0x20, 0x00);   // set memory addressing mode to horizontal
    writeCommand(0xd5, 0x80);   // set display clock divide ratio & oscillator frequency to default
    writeCommand(0xd3, 0x00);   // no display offset
    writeCommand(0x40 | 0x00);  // set default display start line

    // use the following two lines to flip the display
    writeCommand(0xa0 | 0x01);  // set segment re-map
    writeCommand(0xc8);         // set COM output scan direction

    writeCommand(0xda, 0x12);   // set COM pins hardware configuration
    writeCommand(0xd9, 0xf1);   // set pre-charge period to 241x DCLK
    writeCommand(0xdB, 0x40);   // set VCOMH deselect level
    writeCommand(0xa4);         // display RAM content (not all-on)
    writeCommand(0xa6);         // set normal (not-inverted) display

    // set this since we don't have access to the OLED's reset pins (?)
    writeCommand(0x21, 0, 127); // set column address
    writeCommand(0x22, 0, 7);   // set page address

    writeCommand(0x81, 0xcf);   // set contrast
    writeCommand(0x2e);         // deactivate scroll
    writeCommand(0xaf);         // turn display on
  }

  public void invert(boolean inverted) {
    if (inverted) {
      writeCommand(0xa7);
    } else {
      writeCommand(0xa6);
    }
  }

  public void sendImage(PImage img) {
    sendImage(img, 0, 0);
  }

  public void sendImage(PImage img, int startX, int startY) {
    byte[] frame = new byte[1024];
    img.loadPixels();
    for (int y=startY; y < height && y-startY < 64; y++) {
      for (int x=startX; x < width && x-startX < 128; x++) {
        if (128 <= brightness(img.pixels[y*img.width+x])) {
          // this isn't the normal (scanline) mapping, but 8 pixels below each other at a time
          // white pixels have their bit turned on
          frame[x + (y/8)*128] |= (1 << (y % 8));
        }
      }
    }
    sendFramebuffer(frame);
  }

  public void sendFramebuffer(byte[] buf) {
    if (buf.length != 1024) {
      System.err.println("The framebuffer should be 1024 bytes long, with one bit per pixel");
      throw new IllegalArgumentException("Unexpected buffer size");
    }

    writeCommand(0x00 | 0x0); // set start address
    writeCommand(0x10 | 0x0); // set higher column start address
    writeCommand(0x40 | 0x0); // set start line

    // send the frame buffer as 16 byte long packets
    for (int i=0; i < buf.length/16; i++) {
      super.beginTransmission(address);
      super.write(0x40);  // indicates data write
      for (int j=0; j < 16; j++) {
        super.write(buf[i*16+j]);
      }
      super.endTransmission();
    }
  }

  protected void writeCommand(int arg1) {
    super.beginTransmission(address);
    super.write(0x00);    // indicates command write
    super.write(arg1);
    super.endTransmission();
  }

  protected void writeCommand(int arg1, int arg2) {
    super.beginTransmission(address);
    super.write(0x00);
    super.write(arg1);
    super.write(arg2);
    super.endTransmission();
  }

  protected void writeCommand(int arg1, int arg2, int arg3) {
    super.beginTransmission(address);
    super.write(0x00);
    super.write(arg1);
    super.write(arg2);
    super.write(arg3);
    super.endTransmission();
  }
}
  public void settings() {  size(128, 64); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "existential_coffe_ALLinclSCREEN" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
