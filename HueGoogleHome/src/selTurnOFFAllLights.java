import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selTurnOFFAllLights
{
  public String HBTurnOFFReturn;
  
  public String selTurnOFFAllLight1(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
	  TimeUnit.SECONDS.sleep(2);
    Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen turnOFFAllLightsTestScreen = new Screen();
    turnOFFAllLightsTestScreen.mouseMove(commandLineImage);
    turnOFFAllLightsTestScreen.click();
    
    turnOFFAllLightsTestScreen.type("turn off the lights");
    turnOFFAllLightsTestScreen.type("\n");
    
    HBcheckAllLightsOFF hboff = new HBcheckAllLightsOFF();
    
    this.HBTurnOFFReturn = hboff.HBTurnOFFAlllight(bridge,utcdate);
    
    return this.HBTurnOFFReturn;
  }


}