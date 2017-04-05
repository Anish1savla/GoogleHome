import java.util.concurrent.TimeUnit;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selDimHueColorLamp6By30P {
	
	public void selDimHueColorLamp6By30Percent() throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
	Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen turnONHueColorLamp1 = new Screen();
    turnONHueColorLamp1.mouseMove(commandLineImage);
    turnONHueColorLamp1.click();
    
    turnONHueColorLamp1.type("Dim Hue Color Lamp 6 by 30%");
    turnONHueColorLamp1.type("\n");
	
	}
}