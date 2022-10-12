import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.filter.MeanFilter;

public class Capteur {
	 private TouchSensor uTouch;
	 private EV3UltrasonicSensor ultra;
	 private EV3ColorSensor colorSensor;
	 
	 
	 public Capteur() {
		 uTouch  = new TouchSensor(SensorPort.S1);
		 ultra  = new EV3UltrasonicSensor(SensorPort.S2);
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
	 }
	 
	 public float getDistance() {
		 SampleProvider d= ultra.getMode("Distance"); 
		 float[] sample = new float[d.sampleSize()];
		 d.fetchSample(sample, 0);
		 return sample[0];
	 }

	 
	public boolean getTouche() {
		return uTouch.isPressed();
	}
	
	public float[] getColor() {
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
        colorSensor.setFloodlight(Color.WHITE);
        float[] color = new float[average.sampleSize()];
        average.fetchSample(color, 0);        
		return new float[] {color[0],color[1],color[2]};
	}
}