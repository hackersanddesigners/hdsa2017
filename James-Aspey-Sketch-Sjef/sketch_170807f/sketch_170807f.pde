import processing.video.*; //import video
Capture cam; //import capture cam

PFont myFont; //var font
int count=0;

void setup() { //void setup
  size(1200,700);
  myFont = createFont("Univers", 100); //size of shape
  textFont(myFont, 100); //size of shape
  String[] cameras = Capture.list(); //camera-string

  if (cameras.length == 0) {
    println("Get a camera mate."); //print in for no camera
    exit(); //if camera doesnt work
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(cameras[i]);
    }

    // The camera can be initialized directly using an 
    // element from the array returned by list():
    cam = new Capture(this, cameras[0]);
    cam.start();
  }
}
void draw() {
  if (mousePressed) {
    for (int i=300; i<800; i=i+1)
    {
      //if (count%2==0)
      //{
      //int x = int(random(abs(i-250)+i));

      //int y = int(random(abs(i-250)+i));
      //color c =cam.get(x, y);
      //fill(c); //yellow?
      //text(".", x, y);
      //fill(c);
      //rect(25, 25, 50, 50);
      //rotate((PI/3.0)+i);
      //} else
      //{
      int x = int(random(abs(i-250)+i));

      int y = int(random(abs(i-250)+i));
      color c =cam.get(x, y);
      fill(c); //yellow?
      pushMatrix();
      translate(x, y);
      rotate((PI/10.0)+i);
      text(".", 0, 0);
      popMatrix();
      //}
    }
    count++;
  } else {  
    image(cam, 0, 0);
  }
  ellipse(mouseX, mouseY, 50, 50);
  fill(255, 255, 0);
  strokeWeight(0);

  if (cam.available() == true) {
    cam.read();
  }

  // The following does the same, and is faster when just drawing the image
  // without any additional resizing, transformations, or tint.
  //set(0, 0, cam);

  color c = cam.get(mouseX, mouseY);
  fill(c);
  rect(25, 25, 100, 100);
 
}