import java.net.SocketException;
import java.net.UnknownHostException;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class Cedric_Strategie {

	/** Declaration de l'etat : 
	 * DEPART signifie que le robot est au point de départ. 
	 * VIDE signifie que le capteur de touche du robot ne touche rien. 
	 * PLEIN signifie que le capteur de touche du robot touche un palet. 
	 * RECUPERE signifie que les pinces du robot sont fermees et contiennent un palet. */
	private String etat;
	private static final int V2 = 400;
	private static boolean fenetre;

	/* Declaration des actionneurs et des capteurs. */
	private static Actionneur actionneur;
	private static Capteur capt;
	private static Camera cam;

	// Declaration du nombre de but marques
	int nbBut;

	public Cedric_Strategie() throws SocketException, UnknownHostException {
		/** Constructeur sans @param de la classe Cedric_Strategie
		 *  Initialisation de l'etat a "DEPART", des actionneurs et des capteurs du robot. */
		actionneur = new Actionneur();
		capt = new Capteur();
		cam = new Camera();
		System.out.println("Si le robot commence cote fenetre bouton droit,bouton gauche sinon");
		Button.waitForAnyPress();
		if (Button.getButtons() == 8) {
			fenetre = true;
		}
		if (Button.getButtons() == 16) {
			fenetre = false;
		}
		etat = "DEPART";
	}



	public String getEtat(){
		/** @return l'etat actuel dans lequel se trouve le robot. */
		return this.etat;
	}

	public void setEtat(String e){
		/**Met a jour l'etat dans lequel se trouve le robot. */
		this.etat=e;
	}

	public void strategie() {
		/**Methode contenant la strategie utilisee par notre robot pour recuperer un maximum de palets.
		 */  
		if(etat=="DEPART") {
			premierPalet();
			etat="RECUPERE";
		}
		else if (etat=="RECUPERE") {
			double[] coordsCedric = actionneur.getCoords(capt, V2, fenetre);
			String[] coordsCamera = cam.coordsCamera();
			int indexCedricCamera = Outils_Math.plusProche(coordsCamera, coordsCedric);
			coordsCedric = Outils_Math.coordsPrecice(coordsCamera, indexCedricCamera);
			String[] coordsCameraSansCedric = Outils_Math.coordsSansCed(coordsCamera, indexCedricCamera);
			int angle = recherche(coordsCameraSansCedric,coordsCedric);
			goToPalet(angle);
			etat="PLEIN";
		}
		else if (etat=="PLEIN") {
			goToCamp();
		}

	}

	public void homologation1() {
		// Methode qui permet de passer l'homologation : le robot se deplace jusqu'au camp adverse puis s'arrete a la ligne blanche
		goToCamp();
	}


	private void goToPalet(int angle) {
		/** Methode qui permet au robot de se deplacer jusqu'a un palet selon le @param angle et de marquer le but
		 */
		actionneur.ouverturePince();
		while(!capt.getTouche()) {
			actionneur.avance();
		}
		actionneur.fermeturePince(true);
		actionneur.returnAxe(actionneur.getBoussole());
	}
	private int recherche(String[] coordCamera, double[] coordRobot) {
		/** @return un int angle du palet alcul� comme etant l'optimal (le moins couteux )selon 
		 * @param coordCamera : tableau de coordonnees renvoyees par la camaera
		 * @param coordRobot : tableau des coordonnees du robot
		 * 
		 */
		int angle = Outils_Math.angleOptimal(coordCamera, coordRobot, actionneur.getBoussole(), fenetre);
		return angle;
	}





	private static void goToCamp() {
		/* Le robot depose le palet dans le camp adverse. 
		 * Met a� jour l'etat en "VIDE". */

		actionneur.returnAxe(V2);
		actionneur.avance();
		while(capt.isBlanc(capt.getCouleur())) {
		}
		actionneur.stop();
		actionneur.ouverturePince();
		actionneur.avanceDistance(- 200, true); 
		actionneur.fermeturePince(true);

	} 
	public boolean champLibre() {
		/** @return true si le deplacement entre le palet et le robot est possible sans percuter l'autre robot
		 * 
		 */
		float mesure1=capt.getDistance();
		Delay.msDelay(5);
		float mesure2=capt.getDistance();
		if (mesure1-mesure2<0.18) {
			actionneur.tourneSC(90, V2, true);
			actionneur.avanceDistance(100, true);
			actionneur.tourneSC(-90, V2, true);
			return false;
		}
		return true;
	}

	public void premierPalet(){
		/** Le robot se deplace jusqu'au premier palet ( 100 % en dur)
		 */
		actionneur.ouverturePince();
		actionneur.avanceDistance(500, false);
		actionneur.fermeturePince(true);
		actionneur.tourneSC(150,400,false);
		actionneur.avanceDistance(300,false);
		actionneur.tourneSC(-150,400,false);
		goToCamp();

	}

	public void deuxiemePalet(){
		/** Le robot se deplace jusqu'au deuxieme palet ( 100 % en dur)
		 */
		actionneur.tourneSC(180, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.tourneSC(90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.tourneSC(45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.tourneSC(-45,400,false);
        goToCamp();

    }

    public void troisiemePalet(){
		/** Le robot se deplace jusqu'au troisieme palet ( 100 % en dur)
		 */
    	actionneur.tourneSC(180, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.tourneSC(90,V2,false);
        actionneur.avanceDistance(600, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.tourneSC(45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.tourneSC(-45,400,false);
        goToCamp();
    }
    
    public void quatriemePalet(){
		/** Le robot se deplace jusqu'au quatrieme palet ( 100 % en dur)
		 */
    	actionneur.tourneSC(90, V2, false);
        actionneur.avanceDistance(600, false);
        actionneur.tourneSC(90, V2, false);
        actionneur.avanceDistance(1200, false);
        actionneur.tourneSC(90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.tourneSC(-45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.tourneSC(45,400,false);
        goToCamp();
    }
    
    public void cinquiemePalet() {
		/** Le robot se deplace jusqu'au cinquieme palet ( 100 % en dur)
		 */
    	actionneur.tourneSC(180, V2, false);
        actionneur.avanceDistance(1800, false);
        actionneur.tourneSC(-90,V2,false);
        actionneur.avanceDistance(300, false);
        actionneur.fermeturePince(true);
        actionneur.returnAxe(V2);
        actionneur.tourneSC(-45,400,false);
        actionneur.avanceDistance(500,false);
        actionneur.tourneSC(45,400,false);
        goToCamp();
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException {
		Cedric_Strategie ced = new Cedric_Strategie();
		ced.strategie();
		ced.premierPalet();
		ced.deuxiemePalet();
		ced.quatriemePalet();
		ced.cinquiemePalet();
		for(int i = 0; i<4;i++) {
			ced.setEtat("RECUPERE");
			ced.strategie();
			ced.strategie();
		}
		
	}

}


