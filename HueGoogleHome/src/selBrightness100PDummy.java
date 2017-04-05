import java.util.concurrent.TimeUnit;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selBrightness100PDummy {

	public void selBrightnessTo100PDummy() throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		 Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
		    
		    Screen turnOFFAllLightsTestScreenDummy = new Screen();
		    turnOFFAllLightsTestScreenDummy.mouseMove(commandLineImage);
		    turnOFFAllLightsTestScreenDummy.click();
		    
		    turnOFFAllLightsTestScreenDummy.type("set the lights to 100%");
		    turnOFFAllLightsTestScreenDummy.type("\n");
		}
	}
	