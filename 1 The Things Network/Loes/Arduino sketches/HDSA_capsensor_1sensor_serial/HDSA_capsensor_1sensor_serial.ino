#include <CapacitiveSensor.h>

CapacitiveSensor   cs_9_8 = CapacitiveSensor(9,8); // 2.7M resistor between pins 9 & 8, pin 8 is sensor pin, add a wire and or foil

void setup()                    
{
   cs_9_8.set_CS_AutocaL_Millis(0xFFFFFFFF);// turn off autocalibrate on channel 1 - just as an example
   Serial.begin(115200);
   pinMode(2,OUTPUT);
}

void loop()                    
{
 long sensor1 =  cs_9_8.capacitiveSensor(50);

    Serial.println(sensor1);  // print sensor output 
   if(sensor1 >= 3000)
   {
    digitalWrite(2,HIGH);
   }
   else{
    digitalWrite(2,LOW);
   }  
   delay(100);
}
