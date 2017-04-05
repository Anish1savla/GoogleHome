import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selTurnOFFHueColorLampOne
{
  public String hbturnONHueColorLamp1;
  
  public String selTurnOFFHueColorLamp1(PHBridge bridge, WebDriver driver)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
	  TimeUnit.SECONDS.sleep(2);
    Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen turnONHueColorLamp1 = new Screen();
    turnONHueColorLamp1.mouseMove(commandLineImage);
    turnONHueColorLamp1.click();
    
    turnONHueColorLamp1.type("Turn OFF Hue Color Lamp 1");
    turnONHueColorLamp1.type("\n");
    
    HBTurnOFFHueColorLamp1 hbturnoffcolorlamp1 = new HBTurnOFFHueColorLamp1();
    this.hbturnONHueColorLamp1 = hbturnoffcolorlamp1.HBTurnOFFHueColorLampOne(bridge);
    
   // System.out.println("In Turn off hue color lamp one in Selenium:"+hbturnONHueColorLamp1);
    
    return this.hbturnONHueColorLamp1;
  }
}