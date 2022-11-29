import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.hardware.Battery;

public class MainTest {
	//8 Button droit
	//16 Button gauche
	public static void main(String[] args) throws SocketException, UnknownHostException {
		Camera c = new Camera();
		
		double[] coordCed = new double[] {30,150};
		
		//Capteur s = new Capteur();
		Actionneur a = new Actionneur();
		//a.ouverturePince();
		a.fermeturePince(true);

		//Camera c = new Camera();
		/*
		System.out.println("Fênetre BD,Mur BG");
		Button.waitForAnyPress();
		if (Button.getButtons() == 8) {
			coordCed = a.getCoords(s,300,true);
		}
		if (Button.getButtons() == 16) {
			coordCed = a.getCoords(s,300,false);
		}
		*/
		//String[] coordsCamera = c.coordsCamera();
		//System.out.println(coordsCamera.length);
		
		
		//int angle = (int) OutilsMath.angleOptimal(coordsCamera, coordCed, 270, false);
		//System.out.println(angle);
		//System.out.println(angle);
		//a.rotateSC(angle, 300, false);
		//while(s.getTouch())
		//	a.avance();
	}

}
