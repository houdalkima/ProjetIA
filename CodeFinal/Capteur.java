import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.filter.MeanFilter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Capteur {
	//Declaration capteurs
	 private EV3TouchSensor uTouch;
	 private EV3UltrasonicSensor ultra;
	 private EV3ColorSensor colorSensor;
	 
	//Declaration de la camera
	 private DatagramSocket dsocket;
	 private DatagramPacket packet;
	 private byte[] buffer;
	 
	 //Declaration des coordonnees obtenues via la camera
	 private int[] coordonneesCedric;
	 private int[] coordonneesAdversaire;
	 
	 //Valeur par default pour enlever les Infinity de getDistance
	 private int maxDistance = 500;
	 
	 
	 
	 public Capteur(){
		 super();
		 
		//Initialisation des capteurs
		 uTouch  = new EV3TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
		 
		 //Allume la LED en blanc
	     colorSensor.setFloodlight(Color.WHITE);
		 
	 }
	 
	 public float getDistance() {
		 //Méthode retournant un tableau contenant la distance en metre entre Cédric et le palet le plus proche 
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 Float dist = sample[0];
		 if (dist.isInfinite()) {
			 return maxDistance;
		 }
		 return dist;
	 }

	 
	public boolean getTouch() {
		//Méthode retournant true si le capteur de touché est activé
		float[] sample = new float[1];
        	uTouch.fetchSample(sample, 0);
		return sample[0] != 0;
	}
	
	public float[] getColor() {
		//Méthode retournant les proportions RGB captés par le cepteur de couleur
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
        float[] color = new float[average.sampleSize()];
        average.fetchSample(color, 0);        
		return new float[] {color[0],color[1],color[2]};
	}
	
	public boolean isWhite(float[] color) {
        /* Méthode retournant true si la couleur captée par le capteur de couleur du robot est blanche. */
        if (colorSensor.getColorID()!=6) {
            return false;
        }
        return true;
    }
	
		
}