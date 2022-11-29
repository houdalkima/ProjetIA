import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Actionneur {
	
	/* L'attribut d'instance Compass contient l'orientation en degré du robot. */
	 private int Compass;
	
	 /* La constante Wheeldiameter contient le diamètre des roues du robot.
	  * La constante Entreaxe contient la distance entre le centre du moteur du robot et les roues. */
	 private static final int Wheeldiameter = 56;
	 private static final int Entreaxe = 49;
	 
	 /* La constante VA1 contient la vitesse angulaire. */
	 private static final int VA1 = 800;
	 
	 /* Les constantes V1, V2 et V3 contiennent les vitesses linéaires. */
	 private static final int V1 = 200;
	 private static final int V2 = 400;
	 private static final int V3 = 600;
	 
	 /*Vitesse pince */
	 private static final int VP = 800;
	 
	 /* La constante OuverturePince contient l'angle d'ouverture des pinces du robot. */
	 private static final int OuverturePince = 1500;
	 
	 /* Déclaration des actionneurs. */
	 private final EV3MediumRegulatedMotor pince;
	 private final EV3LargeRegulatedMotor moteurDroit;
	 private final EV3LargeRegulatedMotor moteurGauche;
	 private final Wheel wheel1;
	 private final Wheel wheel2;
	 private final Chassis chassis;
	 private final MovePilot pilot;
	 
	 public Actionneur(){
		 /* Initialise la boussole et les actionneurs. */
		 this.Compass=0;
		 pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 pince.setSpeed(VP);
		 moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 moteurGauche  = new EV3LargeRegulatedMotor(MotorPort.C);
		 wheel1  = WheeledChassis.modelWheel(moteurDroit, Wheeldiameter).offset(Entreaxe);
		 wheel2 = WheeledChassis.modelWheel(moteurGauche, Wheeldiameter).offset(-Entreaxe);
		 chassis  = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 pilot  = new MovePilot(chassis);
	 }
	 
	 public int getCompass() {
		 /* Retourne la valeur de la boussole. */
		 return this.Compass;
	 }
	 
	 public void resetCompass() {
		 /* Réinitialise la valeur de la boussole à 0 degré. */
		 this.Compass = 0;
	 }
	 
	 public void returnAxe(int v) {
		 /* Le robot effectue une rotation pour s'orienter en direction de nord.
		  * Réinitialise la valeur de la boussole à 0 degré. */
			int Compass = getCompass();
			if (-180 < Compass & Compass < 180) {
				this.rotateSC(-Compass, v, false);
				resetCompass();
			}
			else {
				if (Compass <= -180) {
					this.rotateSC(-(360+Compass), v, false);
					resetCompass();
				}
				else {
					this.rotateSC(360-Compass, v, false);
					resetCompass();
				}
			}
	 }
	 
	 public void fermeturePince(boolean state) {
		 /* Ferme les pinces du robot. */
		 if (state)
			 pince.rotate(-OuverturePince);
		 else
			 pince.rotate(OuverturePince);
	 }
	 
	 public void ouverturePince() {
		 /* Ouvre les pinces du robot. */
		 fermeturePince(false);
	 }
	 
	 public void avanceDistance(int distance, boolean async) {
		 /* Le robot avance en ligne droite pendant une distance "distance". */
		 pilot.travel(distance,async);
	 }
	 
	 public void avance() {
		 /* Avance de façon infinie*/
		 pilot.forward();
	 }
	 
	 public void recule() {
		 /* Recule de façon infinie*/
		 pilot.backward();
	 }
	 
	 public void rotateSC(int angle, int v, boolean async) {
		 /* Met à jour la valeur de la boussole. 
		  * Le robot effectue une rotation d'angle "angle" à une vitesse "v". */
		 this.Compass = (this.getCompass()+angle)%360;
		 pilot.setAngularSpeed(v);
		 pilot.rotate(angle,async);
	 }
	 
	 public double[] getCoords(Capteur c, int v, boolean fen) {
		 /* Retourne un tableau de float contenant les distances entre le robot et le prochain obstacle  
		  * aux coordonnées nord, sud, est et ouest. */
		 double[] coords = new double[2];
		 double[] measure = new double[4];
		 this.returnAxe(v);
		 measure[0] = c.getDistance();
		 for (int j =1 ; j<4; j++) {
			 this.rotateSC(90, v, false);
			 measure[j] = c.getDistance();
			 Delay.msDelay(500);
		 }
		 if (fen) {
			 coords[0]=(measure[2])*100;
			 coords[1]=(measure[1])*100;
		 }else{
			 coords[0]=(3-measure[2])*100;
			 coords[1]=(measure[3])*100;
		 }
		 return coords;
	 }

	public void stop() {
		pilot.stop();
	}
	 

}

