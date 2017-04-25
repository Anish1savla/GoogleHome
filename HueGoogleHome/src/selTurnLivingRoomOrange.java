import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnLivingRoomOrange {

	public String LivingRoomOrange;
	
	public String TurnLROrange(PHBridge bridge, WebDriver driver, String utcdate) throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		
		TimeUnit.SECONDS.sleep(2);
		 Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
		    
		    Screen LROrange = new Screen();
		    LROrange.mouseMove(commandLineImage);
		    LROrange.click();
		    
		    LROrange.type("Turn Living Room Lights Orange");
		    LROrange.type("\n");
		    
		    HBTurnLROrange hbtlror = new HBTurnLROrange();
		    LivingRoomOrange = hbtlror.TurnLivingRoomOrange(bridge,driver,utcdate);
		    
		    return LivingRoomOrange;
		
		
	}
	
}