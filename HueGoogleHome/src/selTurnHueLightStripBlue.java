import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnHueLightStripBlue {

	public String TurnHueLightStripBlue;
	
	public String TurnHueLightStripBlue(PHBridge bridge, WebDriver driver)throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		TimeUnit.SECONDS.sleep(2);
		 Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
		    
		    Screen setlightstripblue = new Screen();
		    setlightstripblue.mouseMove(commandLineImage);
		    setlightstripblue.click();
		    
		    setlightstripblue.type("Turn Hue LightStrip plus 1 Blue");
		    setlightstripblue.type("\n");
		    
		    HBTurnLightStripBlue hblsb = new HBTurnLightStripBlue();
		
		 TurnHueLightStripBlue=hblsb.TurnLightStripToBlue(bridge, driver);
		 return TurnHueLightStripBlue;
	}
	
}