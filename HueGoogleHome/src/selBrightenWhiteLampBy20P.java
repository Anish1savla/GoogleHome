import java.util.concurrent.TimeUnit;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selBrightenWhiteLampBy20P {

	public void BrightenWhiteLampBy20P() throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen BrightWhiteLamp20P = new Screen();
	    BrightWhiteLamp20P.mouseMove(commandLineImage);
	    BrightWhiteLamp20P.click();
	    
	    TimeUnit.SECONDS.sleep(1);
	    BrightWhiteLamp20P.type("Brighten White Lamp by 30%");
	    BrightWhiteLamp20P.type("\n");
	}
}