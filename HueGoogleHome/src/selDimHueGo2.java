import java.util.concurrent.TimeUnit;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class selDimHueGo2 {
String DimHueGo2;
	
	public void selDimHueGo2() throws FindFailed, InterruptedException{
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen DimHueGo2 = new Screen();
	    DimHueGo2.mouseMove(commandLineImage);
	    DimHueGo2.click();
	    
	    DimHueGo2.type("Dim Hue Go 2");
	    DimHueGo2.type("\n");
	}


}