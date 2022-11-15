package src;
import java.lang.ModuleLayer.Controller;
import java.net.SocketException;
import java.net.UnknownHostException;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import test.TestCalibration;

public class Cedric_Strategie {

	/* Déclaration de l'état : 
	 * DEPART signifie que le robot est au point de départ. 
	 * VIDE signifie que le capteur de touché du robot ne touche rien. 
	 * PLEIN signifie que le capteur de touché du robot touche un palet. 
	 * RECUPERE signifie que les pinces du robot sont fermées et contiennent un palet. */
	private String etat;
	private static final int V2 = 400;

	/* Initalisation de la longueur de la table sur laquelle a lieu la compétition.
	 * Initialisation d’une valeur d'angle pour une petite rotation */
	private final static int LONGUEURTABLE = 300;
	private final static int SMALL_ROT = 5;
	private static float[] White;

	/* Déclaration des actionneurs et des capteurs. */
	private static Actionneur actionneur;
	private static Capteur capt;

	public Cedric_Strategie() {
		/* Initialisation de l'état à "DEPART", des actionneurs et des capteurs du robot. */
		this("DEPART");
	}

	public Cedric_Strategie(String e) throws SocketException, UnknownHostException {
		/* Initialisation de l'état à "e", des actionneurs et des capteurs du robot. */
		this.etat = e;
		this.actionneur = new Actionneur();
		try {
			this.capt = new Capteur(false);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	public String getEtat(){
		/* Retourne l'état actuel dans lequel se trouve le robot. */
		return this.etat;
	}

	public void setEtat(String e){
		/* Met à jour l'état dans lequel se trouve le robot. */
		this.etat=e;
	}

	public void strategie() {
		/* Méthode contenant la stratégie utilisée par notre robot pour récupérer un maximum de palets. */ 
		if(etat=="DEPART") {
			recherche();
			etat="RECUPERE";
		}
		else if (etat=="RECUPERE") {
			goToPalet();
			//when touch();
			etat="PLEIN";
		}
		else if (etat=="PLEIN") {
			goToCamp();
			//quartDeTour();
			etat="VIDE";
		}
		else if (etat=="VIDE") {
			recherche();
			etat="RECUPERE";
		}

	}

	public void homologation1() {
		goToCamp();
	}
	public void homologation2() {
		
	}

	private float isPaletProche(float[] tableDistances) {
		return 0f;
		/* La méthode permet de definir le palet le plus proche
		 * Apartir de la camera on */
	}
	private void goToPalet() {
		/* Le robot va à la distance la plus petite jusqu'à toucher un palat
		 * si la distance ne diminue pas : recalcul
		 * entre distance départ et 18cm, vérifier que distance diminue 
		 * si distance diminue plus avant 18cm minirecherhce() */
	}
	private float[] recherche() {
		/* Le robot fait une rotation de 180 degrés.
		 * Il récupère les distances des palets autour de lui.
		 * L’état est mis à jour en “RECUPERE”. */
		actionneur.rotateSC(180,200,false);
		//Move m = new Move(true, 180f, 180f);
		float[] t=new float[180];
		/*while(m.isMoving()) {
			t=a.getTabDistance();
		}
		System.out.print(t);*/
		return t;
	}
	private float[] miniRecherche() {
		/* Le robot fait une recherche de 10 degré est 10 degré ouest.
		 * Il retrouve le palet.
		 * L’état est mis à jour en “RECUPERE”. */
		return isPaletProche(null);
	}
	
	
//	/*private static void depart() {
//		/* Le robot recherche un palet et l’atteint en partant de son camp.
//		 * Cette méthode est enclenchée dès le début de la compétition. */
//		Controller controller = new Controller(SensorPort.S3, MotorPort.B, MotorPort.C);
//		controller.run();
//		actionneur.rotateSC(SMALL_ROT, 200, false);
//		actionneur.avanceDistance(10, false);
//		goToCamp();
//	}*/


	private static void goToCamp() {
		/* Le robot dépose le palet dans le camp adverse. 
		 * Met à jour l'état en "VIDE". */
			
			actionneur.returnAxe(actionneur,V2);
			actionneur.avance();
			while(capteur.isWhite(capteur.getColor())){ 
				actionneur.avance();
			}
			actionneur.stop();
			actionneur.ouverturePince(actionneur.getOuverturePince());
			actionneur.avanceDistance(- 200, true); 
			actionneur.fermeturePince(actionneur.getOuverturePince());
		
	} 

	public static void main(String[] args) {

	}

}


