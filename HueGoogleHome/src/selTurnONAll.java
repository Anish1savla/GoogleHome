import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class selTurnONAll
{
  String HBTurnONAllLights;

  public String seleniumTestToTurnAllLightsON(PHBridge bridge, WebDriver driver, String utcdate)
    throws FindFailed, IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException
  {
	/*  
	  DesiredCapabilities capabilities=DesiredCapabilities.chrome();
	  capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
	  
	 
	  driver.manage().deleteAllCookies();
    driver.get("https://developers.google.com/actions/tools/web-simulator");
    
    driver.manage().window().maximize();
    System.out.println("Chrome Window Maximized");
    Screen screen = new Screen();
    TimeUnit.SECONDS.sleep(1);
    
    Pattern image1 = new Pattern("Start.PNG");
    //wait(30);
    
    //wait(30);
    screen.click(image1);
    
    String winHandleBefore = driver.getWindowHandle();
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);
    }
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//*[@id='Email']")).click();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(new CharSequence[] { "HueGHAutomation@gmail.com" });
    driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
    driver.findElement(By.id("next")).click();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(new CharSequence[] { "HueAutomation" });
    driver.findElement(By.xpath("//*[@id='PersistentCookie']")).click();
    driver.findElement(By.id("signIn")).click();
   
    driver.switchTo().window(winHandleBefore);
   */
	  TimeUnit.SECONDS.sleep(2);
    Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
    
    Screen turnOnAllLightsTestScreen = new Screen();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    turnOnAllLightsTestScreen.mouseMove(commandLineImage);
    turnOnAllLightsTestScreen.click();
    turnOnAllLightsTestScreen.type("Turn on the lights");
    turnOnAllLightsTestScreen.type("\n");
    
    HBcheckAllLightsON hbturnonalllights = new HBcheckAllLightsON();
    
    this.HBTurnONAllLights = hbturnonalllights.HBTurnONAllLight(bridge,utcdate);
    
    return this.HBTurnONAllLights;
  }
  
  /*
  public String y;
  public String response;
  public String fullLine;
  public String seleniumTestToTurnAllLightsON(PHBridge bridge, WebDriver driver)
		    throws FindFailed, IOException, InterruptedException
		  {
			  
			  DesiredCapabilities capabilities=DesiredCapabilities.chrome();
			  capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
			  Pattern commandLineImage = new Pattern("CommandLineImage.PNG");
			    
			    Screen turnOnAllLightsTestScreen = new Screen();
			    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			    turnOnAllLightsTestScreen.mouseMove(commandLineImage);
			    turnOnAllLightsTestScreen.click();
			    turnOnAllLightsTestScreen.type("Turn on the lights");
			    turnOnAllLightsTestScreen.type("\n");
			   
			    TimeUnit.SECONDS.sleep(10);
			    
			   driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
			   String source=driver.getPageSource();
			   Boolean x = source.contains("turning 10 lights on");
			   
			   //System.out.println(source);
			   System.out.println(x);
			   
			   //String html = "<span class="+"\"Key\""+">"+"\"response\""+":</span> <span class="+"\"string\""+">";
				//System.out.println(html);	   
			   File logTextFile = new File("C:\\Users\\310235474\\git\\GoogleHome1.0\\logtextfile.txt");
			   FileWriter wr = new FileWriter(logTextFile);
			   wr.write(source);
			   wr.close();
			   
			   FileReader fr = new FileReader("C:\\Users\\310235474\\git\\GoogleHome1.0\\logtextfile.txt");
			   
			   BufferedReader br = new BufferedReader(fr);
			   
			   while((y=br.readLine())!=null){
				   if(y.contains("turning 10 lights on")==true){
					   fullLine=y.toString();
					   //System.out.println(fullLine);
					   response= y.substring(63, 96);
				   }else if(y.contains("Error")==true){
					   turnOnAllLightsTestScreen.type("Turn on the lights");
					    turnOnAllLightsTestScreen.type("\n");
				   }
			   }
			   logTextFile.delete();
			   System.out.println(response);
			    return this.HBTurnONAllLights;
			  }		  
			*/
}