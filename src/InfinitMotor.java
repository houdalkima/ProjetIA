import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

@SuppressWarnings("deprecation")
class Motor{
	private static RegulatedMotor motorR;
	private static RegulatedMotor motorL;
	
	public Motor(){
		motorR = new EV3LargeRegulatedMotor(MotorPort.A);
		
	}
	
	public void avance() {
		motorR.rotate(3600);
	}
	
	public void recule() {
		motorR.rotate(3600);
	}
}
public class InfinitMotor {

	public static void main(String[] args) {

		 EV3MediumRegulatedMotor pince = new EV3MediumRegulatedMotor(MotorPort.B);
		 EV3LargeRegulatedMotor moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
		 EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
		 Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-58);
		 Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(58);
		 Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		 MovePilot pilot = new MovePilot(chassis);
		 TouchSensor uTouch = new TouchSensor(SensorPort.S1);
		 pince.setSpeed(600);
	     pince.rotate(1000);
	     pilot.setLinearSpeed(300);
	     pilot.forward();
	     while(! uTouch.isPressed()) {
	    		
	        }
		 pilot.stop();
		 pince.rotate(-2000);

		

	}
}
