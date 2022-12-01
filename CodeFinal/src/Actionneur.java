
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Actionneur {
	
	//L'attribut d'instance Boussole contient l'orientation en degres du robot. 
	 private int boussole;
	
	 //La constante Wheeldiameter contient le diamètre des roues du robot.
	 private static final int Wheeldiameter = 56;
	
	 // La constante Entreaxe contient la distance entre le centre du moteur du robot et les roues.
	 private static final int Entreaxe = 49;
	 
	 // La constante V1 contiens la vitesse lineaire.
	 protected static final int V1 = 400;
	 
	 // Vitesse pince 
	 private static final int VP = 800;
	 
	 // La constante OuverturePince contient l'angle d'ouverture des pinces du robot.
	 private static final int OuverturePince = 1000;
	 
	//////////* Declaration des actionneurs. *///////////////////
	 private final EV3MediumRegulatedMotor pince;
	 private final EV3LargeRegulatedMotor moteurDroit;
	 private final EV3LargeRegulatedMotor moteurGauche;
	 private final Wheel roue1;
	 private final Wheel roue2;
	 private final Chassis chassis;
	 private final MovePilot pilot;
	 
	 public Actionneur(){
		 /** Constructeur sans @param de la classe Actionneur
		  * Initialise la boussole et les actionneurs. */
		 this.boussole=0; // remise de la bousole a 0
		 pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 pince.setSpeed(VP);
		 moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 moteurGauche  = new EV3LargeRegulatedMotor(MotorPort.C);
		 roue1  = WheeledChassis.modelWheel(moteurDroit, Wheeldiameter).offset(Entreaxe);
		 roue2 = WheeledChassis.modelWheel(moteurGauche, Wheeldiameter).offset(-Entreaxe);
		 chassis  = new WheeledChassis(new Wheel[] { roue1, roue2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 pilot  = new MovePilot(chassis);
	 }
	 
	 public int getBoussole() {
		 /* Retourne la valeur de la boussole. */
		 return this.boussole;
	 }
	 
	 public void resetBoussole() {
		 /*  Reinitialise la valeur de la boussole a�0 degres. */
		 this.boussole = 0;
	 }
	 
	 public void returnAxe(int v) {
		 /* Le robot effectue une rotation pour s'orienter en direction du nord (camp adverse).
		  * Reinitialise la valeur de la boussole a�0 degres. */
			int boussole = getBoussole();
			if (-180 < boussole & boussole < 180) {
				this.tourneSC(-boussole, v, false);
				resetBoussole();
			}
			else {
				if (boussole <= -180) {
					this.tourneSC(-(360+boussole), v, false);
					resetBoussole();
				}
				else {
					this.tourneSC(360-boussole, v, false);
					resetBoussole();
				}
			}
	 }
	 
	 public void fermeturePince(boolean state) {
		 /** Ferme les pinces du robot. 
		  * @param state indique si la pince est ouverte ou non*/
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
		 /** Le robot avance en ligne droite pendant d'une distance @param distance. 
		  * @param async definit si l'action se fait en synchrone ou asynchrone */
		 pilot.travel(distance,async);
	 }
	 
	 public void avance() {
		 /* Avance de facon infinie*/
		 pilot.forward();
	 }
	 
	 public void recule() {
		 /* Recule de facon infinie*/
		 pilot.backward();
	 }
	 
	 public void tourneSC(int angle, int v, boolean async) {
		 /** Met a� jour la valeur de la boussole. 
		  * Le robot effectue une rotation d'angle @param angle � une vitesse @param v de facon asynchrone ou non @param async. */
		 this.boussole = (this.getBoussole()+angle)%360;
		 pilot.setAngularSpeed(v);
		 pilot.rotate(angle,async);
	 }
	 
	 public double[] getCoords(Capteur c, int v, boolean fen) {
		 /** Retourne un tableau de float contenant les distances entre le robot et le prochain obstacle  
		  * aux coordonnees nord, sud, est et ouest.
		  * @param c designe le capteur concerne,
		  * @param v la vitesse de rotation
		  * @param fen, permet de d'indiquer si le robot se trouve cote fenetre ou non */
		 double[] coords = new double[2];
		 double[] measure = new double[4];
		 this.returnAxe(v);
		 measure[0] = c.getDistance();
		 for (int j =1 ; j<4; j++) {
			 this.tourneSC(90, v, false);
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
		//Arrete le robot
		pilot.stop();
	}
	 

}

