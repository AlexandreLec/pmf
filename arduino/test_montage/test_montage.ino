#include "DHT.h"
#include <math.h>

//DHT sensor
#define DHTPIN 11     
#define DHTTYPE DHT22

// Initialize DHT sensor.
DHT dht(DHTPIN, DHTTYPE);

//LEDs
#define LED_RED 10
#define LED_GREEN 9

//Module Peltier
#define PELTIER 5

//Thermistances
#define THERM_MODULE 1
#define THERM_EXT 0

//Steinhart-Hart coef
#define A 0.001125294
#define B 0.000234715
#define C 0.0000000856502

double Thermistor(double RawADC) {
  double Temp;
  Temp = log(10000.0/(1024.0/RawADC-1)); 
//         log(10000.0*((1024.0/RawADC-1))) // for pull-down configuration
  Temp = 1 / (A + (B + (C * Temp * Temp ))* Temp );
  Temp = Temp - 273.15;// Convert Kelvin to Celcius
return Temp;
}

//Consigne send by the java program
double consigne=17;

void setup() {
  Serial.begin(9600);
  dht.begin();

  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);

  pinMode(PELTIER, OUTPUT);
}

void loop() {

  if(Serial.available() >= 1){
    consigne = Serial.read();
  }

  delay(2000);

  //DHT22
  double h = dht.readHumidity();
  double t = dht.readTemperature();

  Serial.print("#");
  double tempModule = double(Thermistor(analogRead(THERM_MODULE)));
  Serial.print(tempModule);
  Serial.print("#");
  double tempExt = double(Thermistor(analogRead(THERM_EXT)));
  Serial.print(tempExt);
  Serial.print("#");
  Serial.print(t);
  Serial.print("#");
  Serial.print(h);
  Serial.println("#");
    

  //Regulation
  if(tempModule > consigne+0.5){
    analogWrite(PELTIER, 255);
  }
  else if(tempModule <= consigne){
    analogWrite(PELTIER, 0);
  }
}
