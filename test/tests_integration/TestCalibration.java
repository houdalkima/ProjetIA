import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import lejos.hardware.Button;

public class TestCalibration {
	private static float[] White = new float[] {0.2755f ,0.2794f,0.2451f};
	private static float[] Blue = new float[] {0.0235f,0.0441f,0.0696f};
	private static float[] Yellow = new float[] {0.2392f,0.2167f,0.0510f};
	private static float[] Red = new float[] {0.1441f,0.0441f,0.0216f};
	private static float[] Green = new float[] {0.0568f,0.1275f,0.04118f};
	private static float[] Black = new float[] {0.0353f,0.0480f,0.0206f};
	
	public static boolean errorrange(float reference, float mesure, float e) {
		if (e<0 || e>1) {
			throw new IllegalArgumentException("Donner une valeur entre 0 et 1");
		}
		return (reference-(reference*e) <= mesure && reference+(reference*e)  >= mesure);
	}
	
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
	
	public static boolean TestFourDistance(Capteur c, float error) {
		System.out.println("15 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(errorrange(0.15f,c.getDistance(),error))) {
			return false;
		}
		System.out.println("30 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!((errorrange(0.30f,c.getDistance(),error)))) {
			return false;
		}
		System.out.println("50 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!((errorrange(0.50f,c.getDistance(),error)))) {
			return false;
		}
		System.out.println("80 cm");
		Button.ENTER.waitForPressAndRelease();
		if (!((errorrange(0.8f,c.getDistance(),error)))) {
			return false;
		}
		return true;
		
	}
	
	public static boolean TestDistance(Capteur c,float distance ,float error) {
		System.out.println(distance*10+" cm");
		Button.ENTER.waitForPressAndRelease();
		if (!(errorrange(distance,c.getDistance(),error))) {
			return false;
		}
		return true;
		
	}
	
	public static boolean TestColor(Capteur c, float error) {
		float[] Color = new float[3];
		System.out.println("Test Blanc");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(White[0],Color[0],error) || !errorrange(White[1],Color[1],error) || !errorrange(White[2],Color[2],error)) {
			return false;
		}
		TestCalibration.White[0]=Color[0];
		TestCalibration.White[1]=Color[1];
		TestCalibration.White[2]=Color[2];
		System.out.println("Blanc mis à jour");
		
		System.out.println("Test Bleu");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(Blue[0],Color[0],error) || !errorrange(Blue[1],Color[1],error) || !errorrange(Blue[2],Color[2],error)) {
			return false;
		}
		System.out.println("Test Jaune");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(Yellow[0],Color[0],error) || !errorrange(Yellow[1],Color[1],error) || !errorrange(Yellow[2],Color[2],error)) {
			return false;
		}
		System.out.println("Test Rouge");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(Red[0],Color[0],error) || !errorrange(Red[1],Color[1],error) || !errorrange(Red[2],Color[2],error)) {
			return false;
		}
		System.out.println("Test Vert");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(Green[0],Color[0],error) || !errorrange(Green[1],Color[1],error) || !errorrange(Green[2],Color[2],error)) {
			return false;
		}
		System.out.println("Test Noir");
		Button.ENTER.waitForPressAndRelease();
		Color = c.getColor();
		if (!errorrange(Black[0],Color[0],error) || !errorrange(Black[1],Color[1],error) || !errorrange(Black[2],Color[2],error)) {
			return false;
		}
		return true;
	}
	
	public static boolean TestTouch(Capteur c) {
		System.out.println("Press");
		Button.ENTER.waitForPressAndRelease();
		return c.getTouche();
	}

	public static void main(String[] args) throws SocketException, UnknownHostException {
	}

}
