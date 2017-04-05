import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selTurnONHueColorLampOne
{
  public String hbturnONHueColorLamp1;
  
  public String selTurnONHueColorLamp1(PHBridge bridge, WebDriver driver)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
	  TimeUnit.SECONDS.sleep(2);
    Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen turnONHueColorLamp1 = new Screen();
    turnONHueColorLamp1.mouseMove(commandLineImage);
    turnONHueColorLamp1.click();
    
    turnONHueColorLamp1.type("turn ON Hue Color Lamp 1");
    turnONHueColorLamp1.type("\n");
    
    HBTurnONHueColorLamp1 hbturnoncolorlamp1 = new HBTurnONHueColorLamp1();
    this.hbturnONHueColorLamp1 = hbturnoncolorlamp1.HBTurnONhueColorLampOne(bridge);
    
    return this.hbturnONHueColorLamp1;
  }
}