import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * <p>
 *Cette classe g�re toutes les actions en lien avec le moteur de la pince
 *
 * @author Groupe3
 */
public class Pince {
	public EV3MediumRegulatedMotor m;
	private boolean ouvert;

	/**
	 * Constructeur de la class Pince
	 * <p>
	 * Initialise un objet du type EV3MediumRegulatedMotor avec le port associ� au robot, la vitesse d'ouverture des pinces et une variable bool�enne (ouvert ou non)
	 * @param port Le port sur lequel le moteur de la pince est branch�
	 * 
	 * @see EV3MediumRegulatedMotor
	 * @see EV3MediumRegulatedMotor#setSpeed()
	 */
	public Pince(Port port) {
		m = new EV3MediumRegulatedMotor(port);
		m.setSpeed(1080);
		ouvert=false;
	}

	/**
	 * Ouvre la pince
	 * @see EV3MediumRegulatedMotor#rotate(int))
	 */
	public void open() {
		if (ouvert==false) {
			m.rotate(530);
			ouvert=true;
			System.out.println("Ouverture pince");
		}
	}

	/**
	 * Ferme la pince
	 * @see EV3MediumRegulatedMotor#rotate(int)
	 */
	public void close() {
		m.rotate(-1060);
		System.out.println("Fermeture pince");
	}
	
	public static void main(String[] args){
		Pince p = new Pince(MotorPort.B);
		p.open();
		Delay.msDelay(5000);
		p.close();
	}
}