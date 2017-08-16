THE ASPIRATIONATOR

Made in: Reinventing Smart Workshop - Gottfried Haider
https://github.com/gohai/processing/wiki 

A teapot for the aspirational consumer who want to be conscious and aware about their life and the kind of hot drinks they brew. The aspirationator sends you in to a never ending loop of existential questions about your life and consumer choices. It hums understanding responses to the multiple choice answers you input - after due consideration of course.

Installation
Tools > Add Tool… to install the Upload to Pi tool (this allows you to write sketches on your laptop, but have them run on the Pi)
 Sketch > Import Library… 
Install Hardware I/O library (this allows you to use the expansion header pins from Processing)
Install UDP to send messages over the network

Components
Raspberry pi 3 (“cranberry”)
Oled screen
3 push buttons with built-in LEDs
Set of speakers

Data
In the sketch folders are two text files: 
Every new line is a new item in the array generated in the code. 
list.txt
response.txt

The png image is the one that appears on the OLED.

Process
Follow the instructions in the processing wiki to set up the libraries etcetera. 
The pi we used is nicknamed “cranberry”. 
Change the preferences.txt as explained in instructions to cranberry.local

Wiring
See the pinout overview image in folder: 
All buttons together on pin 7 (or any pin with internal pullup) stay away from I2C pins 1-4 SDA & SCL
All leds together on pin 4 with a 220ohm resistor.
Follow OLED marks and connect SDA to SDA, SCL to SCL. Note: connect GND directly to a GND pin on the pi, not to a rail on the breadboard (didnt work somehow). Connect VCC to 3.3V (not 5!)
