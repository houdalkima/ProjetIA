
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class Capteur {
	//Declaration capteurs
	 protected EV3TouchSensor captTouche;
	 protected EV3UltrasonicSensor captDistance;
	 protected EV3ColorSensor captCouleur;
	 
	 
	 //Valeur par default pour enlever les Infinity de getDistance
	 private int maxDistance = 500;
	 
	 
	 
	 public Capteur(){
		/** Constructeur de la classe Capteur
		 * Sans @param qui permet d'initialiser les capteurs
		 */
		 super();
		 
		//Initialisation des capteurs
		 captTouche  = new EV3TouchSensor(SensorPort.S1);
		 captDistance  = new EV3UltrasonicSensor(SensorPort.S2);
		 captCouleur = new EV3ColorSensor(SensorPort.S3);
		 
		 //Allume la LED du capteur de couleurs en blanc
	     captCouleur.setFloodlight(Color.WHITE);
	 }
	 
	 public float getDistance() {
		 //Methode retournant un tableau contenant la distance en metre entre Cedric et le palet le plus proche 
		 SampleProvider d= captDistance.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 Float dist = sample[0];
		 if (dist.isInfinite()) {
			 return maxDistance;
		 }
		 return dist;
	 }

	 
	public boolean getTouche() {
		/**@return true si le capteur de touche est touche
		 *  
		 */
		float[] sample = new float[1];
        	captTouche.fetchSample(sample, 0);
		return sample[0] != 0;
	}
	
	public float[] getCouleur() {
		/** @return un tableau de float des proportions RGB captees par le capteur de couleur
		 * 
		 */
		SampleProvider moyenne = new MeanFilter(captCouleur.getRGBMode(), 1);
        float[] Couleur = new float[moyenne.sampleSize()];
        moyenne.fetchSample(Couleur, 0);        
		return new float[] {Couleur[0],Couleur[1],Couleur[2]};
	}
	
	public boolean isBlanc(float[] color) {
        /** Méthode @return true si la couleur captée par le capteur de couleur du robot est blanche. **/
        if (captCouleur.getColorID()!=6) {
            return false;
        }
        return true;
    }
	
		
}