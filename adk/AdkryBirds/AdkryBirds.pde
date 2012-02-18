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


int pushServoPin = 11;
int sightServoPin = 12;

int firstLedPin = 24;
int secondLedPin = 26;
int threeLedPin = 28;

Servo pushServo;
Servo sightServo;


AndroidAccessory acc("AdkryBird, Inc.",
  "AdkryBird",
  "AdkryBird Arduino Board",
  "1.0",
  "http://sheremetat.name",
  "0000000012345678");


void setup(){
  Serial.begin(115200);
  Serial.print("\r\nStart");
  //init push servo
  pushServo.attach(pushServoPin);
  
  //init sight servo
  //sightServo.attach(sightServo);
  
  //Init light 
  pinMode(firstLedPin, OUTPUT);
  pinMode(secondLedPin, OUTPUT);
  pinMode(threeLedPin, OUTPUT);
  
  // Set HIGHT to led
  digitalWrite(firstLedPin, HIGH);
  digitalWrite(secondLedPin, HIGH);
  digitalWrite(threeLedPin, HIGH);
  
  acc.powerOn();
}

void loop(){
  byte msg[3];
  if (acc.isConnected()) {
    int len = acc.read(msg, sizeof(msg), 1);
    if (len > 0) {
        if (msg[0] == 0x2) {
	  if (msg[1] == 0x0){
	    pushServo.write(msg[2]);
            delay(2000);
            pushServo.write(0);
          } else if (msg[1] == 0x1){
            pushServo.write(msg[2]);
          }  
      }
    }
  }
}


