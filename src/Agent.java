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

public class Agent {
	 private final int Wheeldiameter = 56;
	 private final int Entreaxe = 49;
	 
	 static final int VA1 = 200;
	 static final int VA2 = 400;
	 static final int VA3 = 600;
	 
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
	 private TouchSensor uTouch;
	 private EV3UltrasonicSensor ultra;
	
	 
	 
	 public Agent() {
		 pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 moteurGauche  = new EV3LargeRegulatedMotor(MotorPort.C);
		 wheel1  = WheeledChassis.modelWheel(moteurDroit, Wheeldiameter).offset(Entreaxe);
		 wheel2 = WheeledChassis.modelWheel(moteurGauche, Wheeldiameter).offset(-Entreaxe);
		 chassis  = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 pilot  = new MovePilot(chassis);
		 uTouch  = new TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 
	 }
	 
	 public void fermeturePince(Boolean state) {
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
	 
	 
	 
	 public float getDistance() {
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 return sample[0];
	 }

	 
	public boolean getTouche() {
		return uTouch.isPressed();
	}
}
