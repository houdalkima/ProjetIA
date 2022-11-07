package src;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.filter.MeanFilter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Capteur {
	
	 /* Declaration des capteurs. */
	 private EV3TouchSensor uTouch;
	 private EV3UltrasonicSensor ultra;
	 private EV3ColorSensor colorSensor;
	 
	 /* Declaration des coordonnees obtenues via la caméra. */
	 private int[] coordonneesCedric;
	 private int[] coordonneesAdversaire;
	 private ArrayList<int[]> positionsPalets;
	 
	 /* La constante portCam contient le port de la caméra. */
	 private static final int portCam = 8888;
	 
	 public Capteur (boolean camera) throws SocketException, UnknownHostException {
		 /* Initialisation des capteurs et de la caméra. */
		 super();
		 uTouch  = new EV3TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
		 if(camera) {
			 InetAddress serveur = InetAddress.getByName("192.168.1.255");
			 DatagramSocket dsocket = new DatagramSocket(portCam);
			 byte[] buffer = new byte[2048];
			 DatagramPacket packet = new DatagramPacket(buffer, buffer.length,serveur,portCam);
		 }
	 }
	 
	 public float getDistance() {
		 /* Méthode retournant un tableau contenant la distance entre le robot et le palet le plus proche. */
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 return sample[0];
	 }
	 
	public boolean getTouche() {
		/* Méthode retournant true si le capteur de touché est activé. */
		float[] sample = new float[1];
        	uTouch.fetchSample(sample, 0);
		return sample[0] != 0;
	}
	
	public float[] getColor() {
		/* Méthode retournant les proportions RGB captées par le capteur de couleur. */
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
        colorSensor.setFloodlight(Color.WHITE);
        float[] color = new float[average.sampleSize()];
        average.fetchSample(color, 0);        
		return new float[] {color[0],color[1],color[2]};
	}
	
	public boolean isWhite(float[] color) {
		/* Méthode retournant true si la couleur captée par le capteur de couleur du robot est blanche. */
		if(color[0]<280 && color[0]>240) {
			if (color[1]<280 && color[1]>240) {
				if (color[2]<280 && color[2]>240) {
					return true;
				}
			
			}
		}
		return false;
	}
	
}
