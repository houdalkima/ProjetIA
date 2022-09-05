import lejos.hardware.Brickfinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

import java.awt.*;

public class HelloWordCedric {
    public static void main(String[] args){
        GraphicsLCD g = Brickfinder.getDefault().getGraphicsLCD();

        g.drawString("Hello Word !",0,0, GraphicsLCD.VCENTER| GraphicsLCD.LEFT);

        Delay.msDelay(5000);
    }
}
