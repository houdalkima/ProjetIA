import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Actionneur {
	 private static final int Wheeldiameter = 56;
	 private static final int Entreaxe = 49;
	 
	 private static final int VA1 = 800;
	 private static final int VA2 = 1500;
	 private static final int VA3 = 25000;
	 
	 private static final int V1 = 200;
	 private static final int V2 = 400;
	 private static final int V3 = 600;
	 
	 private static final int OuverturePince = 2000;
	 
	 private EV3MediumRegulatedMotor pince;
	 private EV3LargeRegulatedMotor moteurDroit;
	 private EV3LargeRegulatedMotor moteurGauche;
	 private Wheel wheel1;
	 private Wheel wheel2;
	 private Chassis chassis;
	 private MovePilot pilot;
	 
	 
	 public Actionneur() {
		 this.pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 this.moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 this.moteurGauche  = new EV3LargeRegulatedMotor(MotorPort.C);
		 this.wheel1  = WheeledChassis.modelWheel(moteurDroit, Wheeldiameter).offset(Entreaxe);
		 this.wheel2 = WheeledChassis.modelWheel(moteurGauche, Wheeldiameter).offset(-Entreaxe);
		 this.chassis  = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 this.pilot  = new MovePilot(chassis);
	 }
	 
	 public void fermeturePince(Boolean state) {
		 if (state) {
			 pince.rotate(-OuverturePince);
		 }else {
			 pince.rotate(OuverturePince);
		 }
	 }
	 
	 public void ouverturePince() {
		 fermeturePince(false);
	 }
	 
	 public void avanceDistance(int distance, boolean async) {
		 pilot.travel(distance,async);
	 }
	 
	 
	 public void rotateSC(int angle, int v,boolean async) {
		pilot.setAngularSpeed(v);
		pilot.rotate(angle,async);
	 }
}
