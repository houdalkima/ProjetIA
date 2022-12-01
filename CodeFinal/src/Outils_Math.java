
import java.lang.Math;

public class Outils_Math {
	 // La constante VA1 contient la vitesse angulaire. 
	 private static final int VA1 = 300;
	 
	 // La constante V1 contiens la vitesse lineaire.
	 protected static final int V1 = 400;
	
	public static double Norme(double x1,double y1,double x2,double y2) {
		/** @return la distance entre le point x de coordonnees @param x1, y1
		 * et le point y de coordonnees @param x2, y2
		 * En calculant la norme 2
		 */
		double p1 = Math.pow((x2-x1),2);
		double p2 = Math.pow((y2-y1),2);
		
		return Math.sqrt(p1+p2);
	}
	
	public static int plusProche(String[] tabCoords, double[] coordsCedric) {
		/** @retunr l'indice du robot parmis le tableau de coordonnees @param tabCoords
		 */
		double xr = coordsCedric[0];
		double yr = coordsCedric[1];
		String[] coord = tabCoords[0].split(";");
	 	int x = Integer.parseInt(coord[1]);
    	int y = Integer.parseInt(coord[2]);
    	int index = Integer.parseInt(coord[0]);
    	double normeMin = Norme(xr,yr,x,y);
    	
        for (int i = 1; i < tabCoords.length; i++) 
        {
        	coord = tabCoords[i].split(";");
        	x = Integer.parseInt(coord[1]);
        	y = Integer.parseInt(coord[2]);
        	double norme = Norme(xr,yr,x,y);
        	if (norme < normeMin) {
        		index = Integer.parseInt(coord[0]);
        		normeMin = norme;
        	}
        	
        }
        
		return index;
	}
	
	public static int angleOptimal(String[] tabCoords, double[] coordsCedric, int boussole, boolean camp) {
		/** @return l'angle en degre entre le palais optimal (le moins couteux) et le robot
		 * en prenant en compte la distance au palet et la distance pour amener le palet au camp adverse
		 * @param tabCoords : tableau de coordonnees sans celles de Cedric
		 * @param coordsCedric : coordonnes cu robot
		 * @param boussole : angle de la boussole en degres
		 * @param camp: true si le camp est du cote fenetre, false sinon
		 */
		
		// String[] coordsCamera = Camera.coordsCamera();
		// int indexCed = OutilsMath.plusProche(coordsCamera, coordCed); 
		// enlever robot adverse ?? 
		
		double robotX = coordsCedric[0];
		double robotY = coordsCedric[1];

		
		double angleOptimal = 0;
		
		double coutOptimal = Double.MAX_VALUE;
		
		for (int j =0; j<tabCoords.length;j++) {
			double angle = 0;
			double distance = 0;

        	String[] coord = tabCoords[j].split(";");
        	int x = Integer.parseInt(coord[1]);
        	int y = Integer.parseInt(coord[2]);
        	
        	if (robotX < x && robotY >y) {
        		angle+= boussole;
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle+= Math.acos(adj/hyp)*(180/Math.PI);
        	}
        	if (robotX < x && robotY <y) {
        		angle+= boussole;
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle-= Math.acos(adj/hyp)*(180/Math.PI);
        	}
        	if (robotX > x && robotY >y) {
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle+= Math.acos(adj/hyp)*(180/Math.PI);
        	}
        	if (robotX > x && robotY <y) {
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle-= Math.acos(adj/hyp)*(180/Math.PI);
        	}
        	distance = Norme(x,y,robotX,robotY);
        	
        	if (camp) {
        		distance += Norme(x,y,30,y);
        	}else {
        		distance += Norme(x,y,270,y);
        	}
			double cout = angle/VA1 + distance/V1;
			if (coutOptimal > cout) {
				coutOptimal = cout;
				angleOptimal = angle;
			}
		}
		return (int) (angleOptimal);		
	}
	
	public static String[] coordsSansCed(String[] coordsCamera, int indexCed) {
		/** @return le tableau de coordonnees de la camera @param coordsCamera sans les coordonnees du robot
		 * selon l'indice des coordonnees du robot : @param indexCed
		 */
		String[] res  = new String[coordsCamera.length-1];
		int j = 0;
		while (j != indexCed) {
			res[j] = coordsCamera[j];
			System.out.println(res[j]);
			j++;
		}
		int i = j;
		j++;
		while (j< coordsCamera.length) {
			res[i] = coordsCamera[j];
			System.out.println(res[i]);
			j++;
		}
		return res;
	}
	
	public static double[] coordsPrecice(String[] coordsCamera , int indexCed) {
		/** @return les coordonnees du robot grace au coordonnees de la camera @param coordsCamera
		 * selon l'indice des coordonnees du robot @param index ced
		 */
		String[] coord = coordsCamera[indexCed].split(";");
		double x = Double.parseDouble(coord[1]);
		double y = Double.parseDouble(coord[2]);
		return new double[] {x,y};
	}

}
