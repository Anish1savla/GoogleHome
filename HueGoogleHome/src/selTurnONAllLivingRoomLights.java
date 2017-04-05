import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnONAllLivingRoomLights {

	
	public void TurnONAllLivingRoomLights(PHBridge bridge, WebDriver driver) throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen turnONLivingRoomLights = new Screen();
	    //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    TimeUnit.SECONDS.sleep(2);
	    turnONLivingRoomLights.mouseMove(commandLineImage);
	    turnONLivingRoomLights.click();
	    turnONLivingRoomLights.type("Turn on Lights in Living Room");
	    turnONLivingRoomLights.type("\n");
	    
	    
	    return;
	}

	
}