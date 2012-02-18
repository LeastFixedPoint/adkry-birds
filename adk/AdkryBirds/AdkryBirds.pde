/**
 * Angry Birds ADK project
 * for Global Android DevCon @Kyiv
 * 
 * Author: Taras Sheremeta
 * Date: 18.02.2012
 * Licence: Apache License 2.0
 */

#include <Wire.h>
#include <Servo.h>

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

#include <CapSense.h>


int pushServo = 9;

AndroidAccessory acc("AdkryBird, Inc.",
  "AdkryBird",
  "AdkryBird Arduino Board",
  "1.0",
  "http://sheremetat.name",
  "0000000012345678");

void setup(){
  acc.powerOn();
}

void loop(){

}


