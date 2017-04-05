import java.util.concurrent.TimeUnit;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selTurnOFFDummy {
	
	public void SelTurnOFFALLDummy() throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		 Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
		    
		    Screen turnOFFAllLightsTestScreenDummy = new Screen();
		    turnOFFAllLightsTestScreenDummy.mouseMove(commandLineImage);
		    turnOFFAllLightsTestScreenDummy.click();
		    
		    turnOFFAllLightsTestScreenDummy.type("turn off lights");
		    turnOFFAllLightsTestScreenDummy.type("\n");
	}

}