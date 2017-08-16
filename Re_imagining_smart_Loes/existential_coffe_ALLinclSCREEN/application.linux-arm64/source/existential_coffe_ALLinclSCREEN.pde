import processing.io.*;
// 0.96" 128x64 OLED display ("SKU 346540")
SSD1306 oled;

PImage img;


//String[] questions = loadStrings("data/list.txt");
String[] questions;

void setup() {
  size(128, 64);
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


void draw() {

  background(255);
  image(img,width/4,0);
  oled.sendImage(get());
  
  // state 0 == pose question
  if (state == 0) {

    println(state);
    // pose question pull thing from array

    String[] questions = loadStrings("data/list.txt");
    say(questions[int(random(questions.length))]); 
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
      say(responses[int(random(responses.length))]); 
      //println("responded");
      delay(3000);
      state = 0;
    }
  }


void say(String what) {
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