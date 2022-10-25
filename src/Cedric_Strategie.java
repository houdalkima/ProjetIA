
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
    	 * 
    	 * 
    	 * 
    	 * 
    	 * 
    	 * la distance est réduite sur une largeur de qlq cm
    	 * et conserve le plus proche
    	 * renvoi un angle (potentiellement la boussole)
    	 * 
    	 * 
    	 */
    }
	private void goToPalet() {
		// TODO Auto-generated method stub
		/* 
		 * aller à la distance la plus petite jusqu'à touch
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
		Agent a= new Agent();
		a.rotateSC(180,200);
		//Move m = new Move(true, 180f, 180f);
		float[] t=new float[180];
		/*while(m.isMoving()) {
			t=a.getTabDistance();
		}
		System.out.print(t);*/
		return t;
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
		 * se décaler 
		 * goToCamp();
		 */
	}

	private void goToCamp() {
		// TODO Auto-generated method stub
		act.rotateSC(act.getCompass(),200,false);
		act.avanceDistance(LONGUEURTABLE*10,false);
		if (capt.isWhite(capt.getColor())) {
			act.stop();
			act.ouverturePince();
			act.avanceDistance(-20,true);
			act.rotateSC(act.getCompass()+180,200,false);
		}		
	}
	
	public static void main(String[] args) {
	}
}
