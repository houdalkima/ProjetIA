import java.lang.Math;

public class OutilsMath {
	
	public static double Norme(float x1,float y1,float x2,float y2) {
		double p1 = Math.pow((x2-x1),2);
		double p2 = Math.pow((y2-y1),2);
		
		return Math.sqrt(p1+p2);
	}
	
	public static int plusProche(String[] lc, float[] cr) {
		float xr = cr[0];
		float yr = cr[1];
		String[] coord = lc[0].split(";");
	 	int x = Integer.parseInt(coord[1]);
    	int y = Integer.parseInt(coord[2]);
    	int index = Integer.parseInt(coord[0]);
    	double normeMin = Norme(xr,yr,x,y);
    	
        for (int i = 1; i < lc.length; i++) 
        {
        	coord = lc[i].split(";");
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
	
	public static int angleOptimal(String[] lc, float[] cr, int compass, boolean camp) {
		// camp = true fenetre camp = false mur
		// appeler lc comme suit : lc = coordsSansCed();
		// String[] coordsCamera = Camera.coordsCamera();
		// int indexCed = OutilsMath.plusProche(coordsCamera, coordCed); 
		// enlever robot adverse ?? 
		
		float robotX = cr[0];
		float robotY = cr[1];
		double angle = 0;
		double distance = 0;
		
		double angleOptimal = 0;
		
		double coutOptimal = Double.MAX_VALUE;
		
		for (int j =0; j<lc.length;j++) {

        	String[] coord = lc[j].split(";");
        	int x = Integer.parseInt(coord[1]);
        	int y = Integer.parseInt(coord[2]);
        	if (robotX > x && robotY >y) {
        		angle+= compass;
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle-= Math.acos(adj/hyp);
        	}
        	if (robotX > x && robotY <y) {
        		angle+= compass;
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(x,robotY,robotX,robotY);
        		
        		angle+= Math.acos(adj/hyp);
        	}
        	if (robotX < x && robotY >y) {
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(robotX,y,robotX,robotY);
        		
        		angle+= Math.acos(adj/hyp);
        	}
        	if (robotX < x && robotY <y) {
        		double hyp = Norme(x,y,robotX,robotY);
        		double adj = Norme(robotX,y,robotX,robotY);
        		
        		angle+= Math.acos(adj/hyp);
        	}
        	distance = Norme(x,y,robotX,robotY);
        	
        	if (camp) {
        		distance += Norme(x,y,30,y);
        	}else {
        		distance += Norme(x,y,270,y);
        	}
			double cout = angle/300 + distance/400;
			
			if (coutOptimal > cout) {
				coutOptimal = cout;
				angleOptimal = angle;
			}
		}
		return (int) (angleOptimal*(180/Math.PI));		
	}
	public static String[] coordsSansCed(String[] coordsCamera, int indexCed) {
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

}
