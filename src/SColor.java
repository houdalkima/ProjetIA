import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class SColor extends Agent{
    public static void main(String[] args) {
    	Port port = LocalEV3.get().getPort("S3");
		EV3ColorSensor colorSensor = new EV3ColorSensor(port);
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
		colorSensor.setFloodlight(Color.WHITE);
		while (true) {
			float[] color = new float[average.sampleSize()];
			average.fetchSample(color, 0);
			for (int i = 0; i<color.length;i++) {
				System.out.println(color[i]);
				
			}
			System.out.println("");
			Delay.msDelay(5000);
		}
       

    }
}

