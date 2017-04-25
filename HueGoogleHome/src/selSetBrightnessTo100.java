import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selSetBrightnessTo100
{
  public String HBsetBright100;
  
  public <HBSetAllBrightessTo100> String SetBrightnessTo100(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
	  TimeUnit.SECONDS.sleep(2);
    Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen setBrightness100 = new Screen();
    setBrightness100.mouseMove(commandLineImage);
    setBrightness100.click();
    
    setBrightness100.type("Set the lights to 100%");
    setBrightness100.type("\n");
    
    HBSetAllBrightnessTo100 hbtlsb = new HBSetAllBrightnessTo100();
    return this.HBsetBright100 = hbtlsb.HBSetBrightnessTo100Percent(bridge,utcdate);
  }


}