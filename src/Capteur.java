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

public class Capteur {
	//Declaration capteurs
	 private EV3TouchSensor uTouch;
	 private EV3UltrasonicSensor ultra;
	 private EV3ColorSensor colorSensor;
	 //Declaration des coordonnees obtenues via la camera
	 private int[] coordonneesCedric;
	 private int[] coordonneesAdversaire;
	 private ArrayList<int[]> positionsPalets;
	 
	 //Port de la camera 
	 private final int portCam = 8888;
	 
	 
	 public Capteur(boolean camera) throws SocketException, UnknownHostException {
		 super();
		 
		//Initialisation des capteurs
		 uTouch  = new EV3TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
		 
		//Initialisation de la camera
		 if(camera) {
			 InetAddress serveur = InetAddress.getByName("192.168.1.255");
			 DatagramSocket dsocket = new DatagramSocket(portCam);
			 byte[] buffer = new byte[2048];
			 DatagramPacket packet = new DatagramPacket(buffer, buffer.length,serveur,portCam);
		 }
	 }
	 
	 public float getDistance() {
		 //Méthode retournant un tableau contenant la distance entre Cédric et le palet le plus proche 
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 return sample[0];
	 }

	 
	public boolean getTouche() {
		//Méthode retournant true si le capteur de touché est activé
		float[] sample = new float[1];
        	uTouch.fetchSample(sample, 0);
		return sample[0] != 0;
	}
	
	public float[] getColor() {
		//Méthode retournant les proportions RGB captés par le cepteur de couleur
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
        colorSensor.setFloodlight(Color.WHITE);
        float[] color = new float[average.sampleSize()];
        average.fetchSample(color, 0);        
		return new float[] {color[0],color[1],color[2]};
	}
	
	
	
	
}
