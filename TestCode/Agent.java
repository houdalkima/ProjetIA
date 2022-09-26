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

public class Agent {
	 private EV3MediumRegulatedMotor pince = new EV3MediumRegulatedMotor(MotorPort.B);
	 private EV3LargeRegulatedMotor moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
	 private EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	 private Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-58);
	 private Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(58);
	 private Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	 private MovePilot pilot = new MovePilot(chassis);
	 private TouchSensor uTouch = new TouchSensor(SensorPort.S1);
	 private EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S2);
	 
	 public Agent() {
		 pilot.setAngularSpeed(1000);
		 pince.setSpeed(1000);
		 
	 }
	 
	 public void fermeturePince(Boolean state) {
		 if (state) {
			 pince.rotate(-2000);
		 }
		 pince.rotate(2000);
	 }
	 
	 public void ouverturePince() {
		 fermeturePince(false);
	 }
	 
	 public void avanceDistance(int distance) {
		 pilot.travel(distance);
	 }
	 public void avance() {
		 pilot.forward();
	 }
	 public void rotate(int angle) {
		pilot.rotate(angle);
	 }
	 
	 public float Distance() {
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 return sample[0];
	 }
	 
	public boolean Touche() {
			return uTouch.isPressed();
		}
		
	public static void main(String[] args) {
			Agent cedric = new Agent();
			cedric.avance();
		}

}


