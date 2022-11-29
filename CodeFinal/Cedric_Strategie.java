import java.net.SocketException;
import java.net.UnknownHostException;

import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;

public class Cedric_Strategie {

	/* Déclaration de l'état : 
	 * DEPART signifie que le robot est au point de départ. 
	 * VIDE signifie que le capteur de touché du robot ne touche rien. 
	 * PLEIN signifie que le capteur de touché du robot touche un palet. 
	 * RECUPERE signifie que les pinces du robot sont fermées et contiennent un palet. */
	private String etat;
	private static final int V2 = 400;
	private static boolean fenetre;

	/* Initalisation de la longueur de la table sur laquelle a lieu la compétition.
	 * Initialisation d’une valeur d'angle pour une petite rotation */
	private final static int LONGUEURTABLE = 300;
	private final static int SMALL_ROT = 5;
	private static float[] White;

	/* Déclaration des actionneurs et des capteurs. */
	private static Actionneur actionneur;
	private static Capteur capt;
	private static Camera cam;

	public Cedric_Strategie() throws SocketException, UnknownHostException {
		/* Initialisation de l'état à "DEPART", des actionneurs et des capteurs du robot. */
		this.actionneur = new Actionneur();
		this.capt = new Capteur();
		this.cam = new Camera();
		System.out.println("Si le robot commence cote fenetre bouton droit,bouton gauche sinon");
		Button.waitForAnyPress();
		if (Button.getButtons() == 8) {
			this.fenetre = true;
		}
		if (Button.getButtons() == 16) {
			this.fenetre = false;
		}
		System.out.println("Placer Cedric sur du blanc");
		Button.waitForAnyPress();
		White = capt.getColor();
		etat = "DEPART";
	}

	public Cedric_Strategie(String e) throws SocketException, UnknownHostException {
		/* Initialisation de l'état à "e", des actionneurs et des capteurs du robot. */
		this.etat = e;
		this.actionneur = new Actionneur();
		this.capt = new Capteur();
		System.out.println("Si le robot commence cote fenetre bouton droit,bouton gauche sinon");
		Button.waitForAnyPress();
		if (Button.getButtons() == 8) {
			this.fenetre = true;
		}
		if (Button.getButtons() == 16) {
			this.fenetre = false;
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
			premierPalet();
			etat="RECUPERE";
		}
		else if (etat=="RECUPERE") {
			double[] coordsCedric = actionneur.getCoords(capt, V2, fenetre);
			String[] coordsCamera = cam.coordsCamera();
			int indexCedricCamera = OutilsMath.plusProche(coordsCamera, coordsCedric);
			//coordsCedric = OutilsMath.coordsPrecice(coordsCamera, indexCedricCamera);
			String[] coordsCameraSansCedric = OutilsMath.coordsSansCed(coordsCamera, indexCedricCamera);
			int angle = recherche(coordsCameraSansCedric,coordsCedric);
			goToPalet(angle);
			//when touch();
			etat="PLEIN";
		}
		else if (etat=="PLEIN") {
			goToCamp();
			etat="VIDE";
		}
		else if (etat=="VIDE") {
			etat="RECUPERE";
		}

	}

	public void homologation1() {
		goToCamp();
	}
	public void homologation2() {
		
	}

	
	private void goToPalet(int angle) {
		actionneur.ouverturePince();
		avance();
		while(!capt.getTouch()) {
		}
		actionneur.fermeturePince(true);
	}
	private int recherche(String[] coordCamera, double[] coordRobot) {
		int angle = OutilsMath.angleOptimal(coordCamera, coordRobot, actionneur.getCompass(), fenetre);
		return angle;
	}

	
	


	private static void goToCamp() {
		/* Le robot dépose le palet dans le camp adverse. 
		 * Met à jour l'état en "VIDE". */
			
			actionneur.returnAxe(V2);
			actionneur.avance();
			while(!capt.isWhite(capt.getColor())) {
		}
			actionneur.ouverturePince();
			actionneur.avanceDistance(-150,false);
			
	} 
	public boolean champLibre() {
		/*
		 *on récupere deux messures de distances
		 *si la distance est négative ou trop proche pour etre un palet on recalcul ailleurs
		 */
		float mesure1=capt.getDistance();
		Delay.msDelay(5);
		float mesure2=capt.getDistance();
		if (mesure1-mesure2<0.18) {
			actionneur.rotateSC(90, V2, true);
			actionneur.avanceDistance(100, true);
			actionneur.rotateSC(-90, V2, true);
			return false;
		}
		return true;
	}

	public void premierPalet(){
        actionneur.ouverturePince();
        actionneur.avanceDistance(600, false);
        actionneur.fermeturePince(true);
        actionneur.rotateSC(45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.rotateSC(-45,400,false);
        goToCamp();

    }
	public void deuxiemePalet(){
		actionneur.rotateSC(180, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.rotateSC(90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.rotateSC(45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.rotateSC(-45,400,false);
        goToCamp();

    }

    public void troisiemePalet(){
    	actionneur.rotateSC(180, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.rotateSC(90,V2,false);
        actionneur.avanceDistance(600, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.rotateSC(45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.rotateSC(-45,400,false);
        goToCamp();
    }
    
    public void quatriemePalet(){
    	actionneur.rotateSC(90, V2, false);
        actionneur.avanceDistance(600, false);
        actionneur.rotateSC(90, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.rotateSC(90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.rotateSC(-45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.rotateSC(45,400,false);
        goToCamp();
    }
    
    public void cinquiemePalet() {
    	actionneur.rotateSC(180, V2, false);
        actionneur.avanceDistance(1800, false);
        actionneur.rotateSC(-90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.rotateSC(-45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.rotateSC(45,400,false);
        goToCamp();
    }


	public static void avance() {
        actionneur.avance();
        while( capt.getDistance()>0.08f) {
        }
        actionneur.stop();
        actionneur.rotateSC(90, V2, false);
    }
	public static void avancePP() {
        actionneur.avance();
        while( capt.getDistance()>0.08f) {
        }
        actionneur.stop();
        actionneur.rotateSC(90, V2, false);
    }
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		Cedric_Strategie ced = new Cedric_Strategie();
		ced.strategie();
		ced.deuxiemePalet();
		ced.quatriemePalet();
		//ced.cinquiemePalet();
		for(int i = 0; i<4;i++) {
			ced.setEtat("RECUPERE");
			ced.strategie();
			ced.strategie();
		}
		
	}

}


