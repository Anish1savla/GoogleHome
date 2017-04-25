import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnOFFAmbianceLampInLivingRoom {
	public String TurnONAmbInLR;
	
	public String TurnOFFAmbianceLampInLivingRoom(PHBridge bridge, WebDriver driver, String utcdate) throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen turnONAmbLivingRoom = new Screen();
	    //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    TimeUnit.SECONDS.sleep(1);
	    turnONAmbLivingRoom.mouseMove(commandLineImage);
	    turnONAmbLivingRoom.click();
	    turnONAmbLivingRoom.type("Turn OFF Hue ambiance lamp 1 in Living Room");
	    turnONAmbLivingRoom.type("\n");
		
	    HBTurnOFFAmbLivingRoom hbtoffamb = new HBTurnOFFAmbLivingRoom(); 
	    TurnONAmbInLR = hbtoffamb.TurnOFFAmbianceLivingRoom(bridge,driver,utcdate);
	    
		return TurnONAmbInLR;
	}

	
}