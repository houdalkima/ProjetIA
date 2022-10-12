package src;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Actionneur {
	 private int Wheeldiameter = 56;
	 private int Entreaxe = 49;
	 
	 static final int VA1 = 800;
	 static final int VA2 = 1500;
	 static final int VA3 = 25000;
	 
	 static final int V1 = 200;
	 static final int V2 = 400;
	 static final int V3 = 600;
	 
	 private EV3MediumRegulatedMotor pince;
	 private EV3LargeRegulatedMotor moteurDroit;
	 private EV3LargeRegulatedMotor moteurGauche;
	 private Wheel wheel1;
	 private Wheel wheel2;
	 private Chassis chassis;
	 private MovePilot pilot;
	 
	 
	 public Actionneur() {
		 pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 moteurGauche  = new EV3LargeRegulatedMotor(MotorPort.C);
		 wheel1  = WheeledChassis.modelWheel(moteurDroit, 56).offset(Entreaxe);
		 wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(-Entreaxe);
		 chassis  = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 pilot  = new MovePilot(chassis);
	 }
	 
	 public void fermeturePince(boolean state) {
		 if (state) {
			 pince.rotate(-2000);
		 }else {
			 pince.rotate(2000);
		 }
	 }
	 
	 public void ouverturePince() {
		 fermeturePince(false);
	 }
	 
	 public void avanceDistance(int distance, boolean async) {
		 pilot.travel(distance,async);
	 }
	 
	 
	 public void rotateSC(int angle, int v) {
		pilot.setAngularSpeed(v);
		pilot.rotate(angle);
	 }
}
