import com.philips.lighting.model.PHBridge;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selDimAllLights {
	
	String DimAllLights;
	
	public void selDimLights(PHBridge bridge, WebDriver driver) throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen turnONHueColorLamp1 = new Screen();
	    turnONHueColorLamp1.mouseMove(commandLineImage);
	    turnONHueColorLamp1.click();
	    
	    turnONHueColorLamp1.type("Dim The Lights");
	    turnONHueColorLamp1.type("\n");
	    	
	   
	}
}