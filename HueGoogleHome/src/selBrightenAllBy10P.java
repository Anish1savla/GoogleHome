import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selBrightenAllBy10P {
	
	public String BrightenAllLightsBy10P;
	
	public String BrightenAllBy10Percent(PHBridge bridge, WebDriver driver) throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		 Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
		    
		    Screen BrightenBy10Percent = new Screen();
		    BrightenBy10Percent.mouseMove(commandLineImage);
		    BrightenBy10Percent.click();
		    
		    BrightenBy10Percent.type("Brighten Lights By 10%");
		    BrightenBy10Percent.type("\n");
			return BrightenAllLightsBy10P;
		    
		
	}

}