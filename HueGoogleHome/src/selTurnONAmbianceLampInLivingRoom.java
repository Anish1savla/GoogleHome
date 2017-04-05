import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnONAmbianceLampInLivingRoom {

	public String TurnONAmbInLR;
	
	public String TurnONAmbianceLampInLivingRoom(PHBridge bridge, WebDriver driver) throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		
		TimeUnit.SECONDS.sleep(2);
		Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
	    
	    Screen turnONAmbLivingRoom = new Screen();
	    //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    TimeUnit.SECONDS.sleep(1);
	    turnONAmbLivingRoom.mouseMove(commandLineImage);
	    turnONAmbLivingRoom.click();
	    turnONAmbLivingRoom.type("Turn On Hue ambiance lamp 1 in Living Room");
	    turnONAmbLivingRoom.type("\n");
		
	    HBTurnONAmbLivingRoom hbtonamb = new HBTurnONAmbLivingRoom(); 
	    TurnONAmbInLR = hbtonamb.TurnONAmbianceLivingRoom(bridge,driver);
	    
		return TurnONAmbInLR;
	}
	
}