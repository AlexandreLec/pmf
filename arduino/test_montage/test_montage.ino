#include "DHT.h"
#include <math.h>

//DHT sensor
#define DHTPIN 12     
#define DHTTYPE DHT22

// Initialize DHT sensor.
DHT dht(DHTPIN, DHTTYPE);

//LEDs
#define LED_RED 10
#define LED_GREEN 9

//Module Peltier
#define PELTIER 5

//Thermistances
#define THERM_MODULE 0
#define THERM_EXT 1

double Thermistor(int RawADC) {
 double Temp;
 Temp = log(10000.0/(1024.0/RawADC-1)); 
//         log(10000.0*((1024.0/RawADC-1))) // for pull-down configuration
 Temp = 1 / (0.001129148 + (0.000234125 + (0.0000000876741 * Temp * Temp ))* Temp );
 Temp = Temp - 273.15;            // Convert Kelvin to Celcius
 return Temp;
}

void setup() {
  Serial.begin(9600);
  dht.begin();

  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);  

  //Test module peltier
  analogWrite(PELTIER, 255);
}

void loop() {

  delay(2000);

  //Test DHT-22
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  Serial.print("Humidity interieure: ");
  Serial.print(h);
  Serial.print("Temperature interieure: ");
  Serial.println(t);

  //Test LEDs
  digitalWrite(LED_RED, HIGH);
  digitalWrite(LED_GREEN, HIGH);

  Serial.print("Temperature module: ");  
  Serial.println(int(Thermistor(analogRead(THERM_MODULE))));  
  Serial.print("Temperature exterieure: ");  
  Serial.println(int(Thermistor(analogRead(THERM_EXT))));
}
