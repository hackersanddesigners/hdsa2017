
PROJECT OUTLINE

interactive poster to indicate whether we’re on or off the grid and post our grid-status to twitter. 

https://twitter.com/onoroffthegrid 

Part of: The Things Network workshop
Main tutorial: 
https://www.thethingsnetwork.org/labs/story/creating-a-ttn-node

Group: 
Kimberley Halsall
Loes Bogers
William Eckerstein
Sijing Zhang

Sensors
Poster has two capacitive touch sensors made with conductive in (Bare Conductive) and a 2.7M ohm resistor (any between 1M and 10M will work, you will just see bigger or smaller changes in the sensor values) in the voltage divider circuit. 

Tutorial 1: Building capacitive touch sensor with conductive paint:
https://www.bareconductive.com/make/building-a-capacitive-proximity-sensor-using-electric-paint/

Tutorial 2: Design rules of thumb for using conductive paint: 
https://www.bareconductive.com/make/sensor-design-basic-rules-of-thumb/

Library: 
Capacitive touch sensing library: https://playground.arduino.cc/Main/CapacitiveSensor?from=Main.CapSense

Other tutorials: 
tutorial 3: https://www.pjrc.com/teensy/td_libs_CapacitiveSensor.html
tutorial 4: http://www.instructables.com/id/Arduino-Capacitive-Sensor-in-Less-Than-2-Minutes/

Arduino sketches:
HDSA_capsensor_1sensor_serial
To get one cap sensor running and find the right threshold in serial

HDSA_capsensor_2sensors_serial
Get 2 cap sensors running and find the right thresholds in serial

HDSA_2capsensors_w_toggle
Now with toggle to turn values into a on/off switch based on threshold

HDSA_ttn_abp_2capsensors
Sketch to send values to TTN via ABP protocol (we didnt get it working via OTAA, see step “Using Activation by Personalization” in Frank’s tutorial): https://www.thethingsnetwork.org/labs/story/creating-a-ttn-node
Copy in the right addresses (see tutorial)
And use the threshold that works best for your design that you tested in sketches 1 and 2.

LoraWAN & Things network 
to send a 0 or 1 to the TTN console
decoding in TTN console: 

> Payload format > decoder
Put this code into the decoder to convert the 0 and 1s into strings of text to If This Then That 

function Decoder(bytes, port) {
  // Decode an uplink message from a buffer
  // (array) of bytes to an object of fields.
  var decoded = {text: "off the grid"};
  if (bytes[0] == 0x01)
    decoded = {text: "on the grid"};
  
  return decoded;
}

IFTT integration
If This Then That takes the value (0 for off the grid and 1 for on the grid) and tweets the status on the twitter account. 
If you are looking for the IFTTT key, it is at the top right on https://ifttt.com/maker_webhooks under “documentation”.

Twitter handle
@onoroffthegrid
user = onoroffthegrid
pass = onoroffthegrid
