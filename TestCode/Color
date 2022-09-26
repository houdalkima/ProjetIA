import lejos.robotics.Color; 
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SColor extends Agent{
    private static final int ecart = 50;
    /*private static final float[] RED = new float[]{}; 
    private static final float[] GREEN = new float[]{}; 
    private static final float[] BLUE = new float[]{};
    private static final float[] DARK = new float[]{};
    private static final float[] YELLOW = new float[]{};*/
    private static final int[] WHITE = new int[]{276,279,245};
    private int[] couleur;
    
    public SColor(){
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
        int r = (int)((colorSensor.getRed())*1000);
        int g = (int)((colorSensor.getGreen())*1000);
        int b = (int)((colorSensor.getBlue())*1000);
        couleur = new int []{r,g,b};
    }
    
    public boolean equalsWhite(int[] RVB){
        for (int i=0; i<RVB.length;i++){
            if(RVB[i]<=WHITE[i]+ecart && RVB[i]>=WHITE[i]-ecart){
                cpt++;
            }
        }
        return cpt == RVB.length;
    }
    
    public boolean recognizeColor(){
		return equalsWhite(couleur);
    }
    
	public static void main(String[] args) {
	    Agent cedric = new Agent();
	    cedric.avance();
	    SColor c = new SColor();
		if (c.recognizeColor()){
		    cedric.stop();
		}
		
	}
}
