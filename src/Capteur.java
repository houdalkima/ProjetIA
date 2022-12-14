package src;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.filter.MeanFilter;
import test.TestCalibration;
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
	 private int[] positionsPalets;// on a enlever le ArraysList sources d'erreur et pas vu l'utilité
	 
	 /* La constante portCam contient le port de la caméra. */
	 private static final int portCam = 8888;
	 
	 public Capteur (boolean camera) throws SocketException, UnknownHostException {
		 /* Initialisation des capteurs et de la caméra. */
		 super();
		 uTouch  = new EV3TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
		 colorSensor.setFloodlight(true);
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
        float[] color = new float[average.sampleSize()];
        average.fetchSample(color, 0);  
		return new float[] {color[0],color[1],color[2]};
	}
	
	public boolean isWhite(float[] color) {
		/* Méthode retournant true si la couleur captée par le capteur de couleur du robot est blanche. */
		color=getColor();
		if (colorSensor.getColorID()!=6) {
			return false;
		}
		return true;
	}
	
}
