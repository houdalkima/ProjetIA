
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Cedric_Strategie {
	private String etat;
	
	
	public Cedric_Strategie() {
		etat="DEPART";
	}
	public Cedric_Strategie(String e) {
		etat=e;
	}
	
	
	public String getEtat(){
        return this.etat;
    }
	public void setEtat(String e){
        this.etat=e;
    }
	
    public void strategie() {
		// TODO Auto-generated method stub
		if(etat=="DEPART") {
			depart();
			etat="PLEIN";
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
    private float isPaletProche(float[] tableDistances) {
    	/* repere un palet dans le tableau :
    	 * la distance est r�duite sur une largeur de qlq cm
    	 * et conserve le plus proche
    	 * renvoi un angle (potentiellement la boussole)
    	 */
    }
	private void goToPalet() {
		// TODO Auto-generated method stub
		/* 
		 * aller � la distance la plus petite jusqu'� touch
		 * si la distance ne diminue pas : recalcul
		 * entre distance depart et 18cm verifier que distance diminue 
		 * si distance diminue plus avant 18cm minirecherhce()
		 */
	}
	private float[] recherche() {
		// TODO Auto-generated method stub
		/* faire 180
		 * recuperer les distances des palet autour
		 * RECUPERE
		 */
		 return isPaletProche();
	}
	private float[] miniRecherche() {
		// TODO Auto-generated method stub
		/* faire 10est 10ouest
		 * retrouver le palet
		 * RECUPERE
		 */
		 return isPaletProche();
	}
	private static void depart() {
		// TODO Auto-generated method stub
		/* foloow line 
		 * se d�caler 
		 * goToCamp();
		 */
	}

	private static void goToCamp() {
		// TODO Auto-generated method stub
		/* aller au nord 
		 * tant que pas de ligne blanche franchit avancer
		 * quand franchi deposer 
		 */
	}
	public static void main(String[] args) {
	}
}