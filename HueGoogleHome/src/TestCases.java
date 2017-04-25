import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import com.philips.lighting.model.PHBridge;

public class TestCases
  extends HueGoogleHome
{
  public String StringToCreateHTML = "";
  public String turnOFFAll;
  public String turnONAll;
  public String changeColorRed;
  public String changeColorGreen;
  public String setBrightness100;
  public String nullString = "";
  public String turnONhueColorLamp1;
  public String turnOFFhueColorLamp1;
  public String DimAll;
  public String DimHueGo2;
  public String BrightenAllLightsBy10P;
  public String SetBrighteness10Percent;
  public String TurnLightStripBlue;
  public String DimAllLightsBy20P;
  public String DimHueColorLamp6By30P;
  public String BrightenWhiteLampBy20P;
  public String TurnONAllLivingRoomLights;
  public String TurnOFFAllLivingRoomLights;
  public String TurnONAmbLivingRoom;
  public String TurnOFFAmbLivingRoom;
  public String TurnLivingRoomOrange;
  //htmlReport hr = new htmlReport();
  
  public void turnonalllights(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException
  {
    selTurnONAll stonall = new selTurnONAll();
    this.turnONAll = stonall.seleniumTestToTurnAllLightsON(bridge, driver,utcdate);
  }
  
  public void turnoffalllights(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selTurnOFFAllLights stoffall = new selTurnOFFAllLights();
    this.turnOFFAll = stoffall.selTurnOFFAllLight1(bridge, driver,utcdate);
  }
  
  public void changeColorToRed(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selChangeColorRed sccred = new selChangeColorRed();
    this.changeColorRed = sccred.selChangeColorToRed(bridge, driver,utcdate);
  }
  
  public void changeColorGreen(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selChangeColorGreen sccgreen = new selChangeColorGreen();
    this.changeColorGreen = sccgreen.selChangeColorToRed(bridge, driver,utcdate);
  }
 
  public void SetBrightnessTo100(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selSetBrightnessTo100 ssb100 = new selSetBrightnessTo100();
    this.setBrightness100 = ssb100.SetBrightnessTo100(bridge, driver,utcdate);
  }
   
  public void turnONHueColorLamp1(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selTurnONHueColorLampOne stonhcl1 = new selTurnONHueColorLampOne();
    this.turnONhueColorLamp1 = stonhcl1.selTurnONHueColorLamp1(bridge, driver,utcdate);
    //System.out.println("turnONHueColorLamp1:" + this.turnONhueColorLamp1);
  }
  
  
  public void turnOFFHueColorLamp1(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, InterruptedException, InvalidFormatException, IOException
  {
    selTurnOFFHueColorLampOne stoffhcl1 = new selTurnOFFHueColorLampOne();
    this.turnOFFhueColorLamp1 = stoffhcl1.selTurnOFFHueColorLamp1(bridge, driver,utcdate);
    //System.out.println("turnOFFHueColorLamp1 in Test Case:" + this.turnOFFhueColorLamp1);
  }
  
  
  public void DimAllLights(PHBridge bridge, WebDriver driver, String utcdate)
		    throws FindFailed, InterruptedException, InvalidFormatException, IOException
		  {
		    HBDimAllLights hbdimall = new HBDimAllLights();
		    this.DimAll = hbdimall.HBDimLights(bridge,driver,utcdate);
		    //System.out.println("turnOFFHueColorLamp1 in Test Case:" + this.DimAll);
		  }

  public void DimHueGo2(PHBridge bridge, WebDriver driver, String utcdate)
		  throws FindFailed, InterruptedException, InvalidFormatException, IOException{
	  HBDimHueGo2 hbdhg2 = new HBDimHueGo2();
	  DimHueGo2 = hbdhg2.DimHueGo2(bridge,driver,utcdate);
  }
  

  public void SetBrightnessTo10Percent(PHBridge bridge, WebDriver driver, String utcdate)
		  throws FindFailed, InterruptedException, InvalidFormatException, IOException{
	  selSetBrightness10Percent sb10p = new selSetBrightness10Percent();
	  SetBrighteness10Percent = sb10p.SetBrightness10Percent(bridge,driver,utcdate);
  }
  
  public void BrightenAllLightsBy10P(PHBridge bridge, WebDriver driver, String utcdate)
		  throws FindFailed, InterruptedException, InvalidFormatException, IOException{
	  HBBrightenAllLightsBy10P hbball = new HBBrightenAllLightsBy10P();
	  BrightenAllLightsBy10P = hbball.BrightenAllLights(bridge, driver,utcdate);
  }  

  public void TurnLightStripBlue(PHBridge bridge, WebDriver driver, String utcdate)
		  throws FindFailed, InterruptedException, InvalidFormatException, IOException{
	  selTurnHueLightStripBlue stlb = new selTurnHueLightStripBlue();
	  TurnLightStripBlue = stlb.TurnHueLightStripBlue(bridge,driver,utcdate);
  }  

  public void DimAllLightsBy20P(PHBridge bridge, WebDriver driver, String utcdate)
		  throws FindFailed, InterruptedException, InvalidFormatException, IOException{
	  HBDimLightsBy20P dlb20p = new HBDimLightsBy20P();
	  DimAllLightsBy20P=dlb20p.DimAllLightsBy20Percent(bridge,driver,utcdate);
  }  

  public void DimHueColorLamp6By30P(PHBridge bridge,WebDriver driver, String utcdate)
  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  HBDimHueColorLamp6By30P dcl630p = new HBDimHueColorLamp6By30P();
	  DimHueColorLamp6By30P=dcl630p.DimHueColorLamp6By30P(bridge,driver,utcdate);
	  
  }
    
  public void BrightenWhiteLampBy20P(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
			  HBBrightenWhiteLampBy20P bwl20p = new HBBrightenWhiteLampBy20P();
			  BrightenWhiteLampBy20P=bwl20p.BrightenWhiteLampBy20P(bridge,driver,utcdate);
			  
  }
  
  public void TurnONAllLivingRoomLights(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  		HBTurnONAllLivingRoomLights tonalllr = new HBTurnONAllLivingRoomLights();
			  TurnONAllLivingRoomLights=tonalllr.TurnONAllLivingRoomLights(bridge,driver,utcdate);
			  
}

  
  public void TurnOFFAllLivingRoomLights(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  		HBTurnOFFAllLivingRoomLights tonalllr = new HBTurnOFFAllLivingRoomLights();
	  		TurnOFFAllLivingRoomLights=tonalllr.TurnOFFAllLivingRoomLights(bridge,driver,utcdate);
	}
  
  public void TurnONAmbLivingRoomLight(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  		selTurnONAmbianceLampInLivingRoom tonamblr = new selTurnONAmbianceLampInLivingRoom();
	  		TurnONAmbLivingRoom=tonamblr.TurnONAmbianceLampInLivingRoom(bridge, driver,utcdate);
	}

  public void TurnOFFAmbLivingRoomLight(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  		selTurnOFFAmbianceLampInLivingRoom tonamblr = new selTurnOFFAmbianceLampInLivingRoom();
	  		TurnOFFAmbLivingRoom=tonamblr.TurnOFFAmbianceLampInLivingRoom(bridge, driver,utcdate);
	}
  
  
  public void TurnLivingRoomOrange(PHBridge bridge,WebDriver driver, String utcdate)
		  	throws FindFailed,InterruptedException, InvalidFormatException, IOException{
	  		selTurnLivingRoomOrange tlrorn = new selTurnLivingRoomOrange();
	  		TurnLivingRoomOrange=tlrorn.TurnLROrange(bridge, driver,utcdate);
	}
  
  
  
  public void createHTMLReport()
    throws IOException
  {
    htmlReport hr = new htmlReport();
    hr.createHTMLReport(turnONAll, turnOFFAll, changeColorRed, changeColorGreen, 
    		setBrightness100, turnONhueColorLamp1,turnOFFhueColorLamp1,DimAll,DimHueGo2,
    		SetBrighteness10Percent,BrightenAllLightsBy10P,TurnLightStripBlue,DimAllLightsBy20P,DimHueColorLamp6By30P,
    		BrightenWhiteLampBy20P,TurnONAllLivingRoomLights,TurnOFFAllLivingRoomLights,TurnONAmbLivingRoom,TurnOFFAmbLivingRoom,
    		TurnLivingRoomOrange);
  }
}