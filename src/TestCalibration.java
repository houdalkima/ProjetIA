package src;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import lejos.hardware.Button;

public class TestCalibration {
	
	public static int genererInt(int borneInf, int borneSup){
		   Random random = new Random();
		   int nb;
		   nb = borneInf+random.nextInt(borneSup-borneInf);
		   return nb;
		}
	
	public static void TestRotation(Actionneur a, int v) {
		for (int j=0; j<10;j++) {
			a.rotateSC(genererInt(-360,360), v, false);
		}
		int Compass = a.getCompass();
		System.out.println(Compass);
		if (-180 < Compass & Compass < 180) {
			a.rotateSC(-Compass, v, false);
		}
		else {
			if (Compass <= -180) {
				a.rotateSC(-(360+Compass), v, false);
			}
			else {
				a.rotateSC(360-Compass, v, false);
			}
		}
	}
	
	public static boolean TestDistance(Capteur c, int error) {
		System.out.println("15 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(0.15*error <= c.getDistance() & c.getDistance() <0.15/error)) {
			return false;
		}
		System.out.println("30 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(0.3*error <= c.getDistance() && c.getDistance() <0.3/error)) {
			return false;
		}
		System.out.println("50 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(0.5*error <= c.getDistance() && c.getDistance() <0.50/error)) {
			return false;
		}
		System.out.println("80 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(0.8*error <= c.getDistance() && c.getDistance() <0.8/error)) {
			return false;
		}
		return true;
		
	}
	public static boolean TestColor(Capteur c) {
		float[] Color = new float[3];
		System.out.println("Blanc");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (Color[0] != 0.2755 || Color[1] != 0.2794 || Color[2] !=  0.2451) {
			
		}
		System.out.println("Bleu");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (Color[0] != 0.0235 || Color[1] != 0.0441 || Color[2] !=  0.0696) {
			
		}
		
		return true;
	}
	
	public static boolean TestTouch(Capteur c) {
		System.out.println("Press");
		Button.ENTER.waitForPressAndRelease();
		return c.getTouche();
	}

	public static void main(String[] args) throws SocketException, UnknownHostException {
		//Capteur c = new Capteur();
		Actionneur a = new Actionneur();
		
		TestRotation(a,200);
		

	}

}
