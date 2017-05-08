import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.philips.lighting.model.PHBridge;

public class HueGoogleHome
{
  public static String userNameStored;
  public static String ipAddressStored;
  public static int TestCaseIdCounter=0;
  public static void main(String[] args)
    throws FindFailed, InterruptedException
  {
    initializeHue(connectCallback);
    TimeUnit.SECONDS.sleep(5L);
  }
  
  private static ConnectCallback connectCallback = new ConnectCallback()
  {
    public void onConnected(PHBridge bridge) throws EncryptedDocumentException, InvalidFormatException, ParseException, MessagingException
    {
      System.out.println("Inside onConnected and about to start test");
      try
      {
        
        HueGoogleHome.startTests(bridge);
      }
      catch (FindFailed e)
      {
        e.printStackTrace();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  };
  
  private static void initializeHue(ConnectCallback connectCallback)
    throws InterruptedException
  {
	  
	  
    try
    {
    	
      HueBridgeConnection bh = new HueBridgeConnection();
      File BridgeConnectionFile = new File("C:\\Users\\310235474\\git\\GoogleHomeBL\\HueGoogleHome\\BridgeProperty.txt");
       
      
       if (BridgeConnectionFile.exists()==true){
       

		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\310235474\\git\\GoogleHomeBL\\HueGoogleHome\\BridgeProperty.txt"));
    	  String userNamefromText;
    	  int CounterExecution=0;
    	  while ((userNamefromText = br.readLine()) != null)
          {
    		  System.out.println("Inside While Loop");
            
            if (userNamefromText.length() >= 15)
            {
              userNameStored = userNamefromText.toString();
              System.out.println("Stored Username is:" + userNameStored);
            }
            else
            {
              ipAddressStored = userNamefromText.toString();
              System.out.println("Stored IP is:" + ipAddressStored);
            }
            CounterExecution++;
            System.out.println(CounterExecution+": Counter Execution");
          }
    	  bh.connectToBridgeWithIp(ipAddressStored,userNameStored,connectCallback);
    	  //bh.connectToBridgeWithIp(ipAddressStored,connectCallback);
      }else {
    	  //bh.connectToBridgeWithIp("192.168.86.20",connectCallback);
    	  bh.connectToBridgeWithIp("192.168.86.20"," ",connectCallback);
    	    TimeUnit.SECONDS.sleep(5);
    	   // System.out.println("Hue Bridge connection is done");
      }
      
    }
      
        catch (IOException localIOException) 
    	{
        	System.out.println("Inside Catch");
        }
  }
  
  
public static void InitiateSimulator(WebDriver driver) throws InterruptedException, FindFailed{
	  
	System.out.println("Inside start Test");
	
      driver.manage().deleteAllCookies();
      driver.get("https://developers.google.com/actions/tools/web-simulator");
      System.out.println("Maximising window");
      driver.manage().window().maximize();
      System.out.println("Chrome Window Maximized");
      Screen screen = new Screen();
      TimeUnit.SECONDS.sleep(2);
      
      
      ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,250)");
      
      TimeUnit.SECONDS.sleep(1);
      
      Pattern image1 = new Pattern("Start.PNG");
      
      screen.click(image1);
      
      String winHandleBefore = driver.getWindowHandle();
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
      for (String winHandle : driver.getWindowHandles()) {
        driver.switchTo().window(winHandle);
      }
      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      driver.findElement(By.xpath("//*[@id='identifierId']")).click();
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      driver.findElement(By.xpath("//*[@id='identifierId']")).sendKeys(new CharSequence[] { "HueGHAutomation@gmail.com" });
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      driver.findElement(By.id("identifierNext")).click();
      
      TimeUnit.SECONDS.sleep(3);
      Pattern image2 = new Pattern("EnterPassword.PNG");
      Screen screen1 = new Screen();
      screen1.mouseMove(image2);
      screen1.click(image2);
      System.out.println("Click Done");
      screen1.type("HueAutomation");
      screen1.type("\n");
      driver.switchTo().window(winHandleBefore);
  
  }
  
  private static void startTests(PHBridge bridge)
    throws FindFailed, InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException, ParseException, MessagingException
  {
	 System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
     WebDriver driver = new ChromeDriver();
     InitiateSimulator(driver); 
      
     Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      //String time = (sdf.format(date));
      
   /*   if((sdf.parse(sdf.format(date)).after(sdf.parse("11:40:00"))) 
    		  && (sdf.parse(sdf.format(date)).before(sdf.parse("11:45:00"))))
      {	
    	  System.out.println("Inside IF to create Daily Report");
    	  DailyReport spreadsheet = new DailyReport();
          spreadsheet.createSpreadsheetForDailyReport();  
      }else{
    	  System.out.println("Inside ELSE to create Daily Report");
      }*/
      
	System.out.println("Starting Test Case Execution");
	
	TimeZone timeZone = TimeZone.getTimeZone("UTC");
	Calendar calendar = Calendar.getInstance(timeZone);
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String utcdate = sdf1.format(calendar.getTime());
    
    TestCases tc = new TestCases();
    
    selTurnOFFDummy std = new selTurnOFFDummy();
    
    selBrightness100PDummy b100pd = new selBrightness100PDummy();
    //String APIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
    //System.out.println("API Version of Bridge:"+APIVersion);
    
    
    
    std.SelTurnOFFALLDummy();
    TimeUnit.SECONDS.sleep(15);
    
    tc.turnonalllights(bridge, driver,utcdate);

    tc.turnoffalllights(bridge, driver,utcdate);
   
    tc.changeColorToRed(bridge, driver,utcdate);
    
    tc.changeColorGreen(bridge, driver,utcdate);
    
    tc.SetBrightnessTo100(bridge, driver,utcdate);

    std.SelTurnOFFALLDummy();
    
    TimeUnit.SECONDS.sleep(5);

    tc.turnONHueColorLamp1(bridge, driver,utcdate);

	tc.turnOFFHueColorLamp1(bridge, driver,utcdate);
    
    b100pd.selBrightnessTo100PDummy();
	
    tc.DimAllLights(bridge,driver,utcdate);
    
    tc.DimHueGo2(bridge,driver,utcdate);
    
    tc.SetBrightnessTo10Percent(bridge,driver,utcdate);

    tc.BrightenAllLightsBy10P(bridge,driver,utcdate);

    tc.TurnLightStripBlue(bridge,driver,utcdate);

    b100pd.selBrightnessTo100PDummy();

    tc.DimAllLightsBy20P(bridge,driver,utcdate);
    
    tc.DimHueColorLamp6By30P(bridge,driver,utcdate);

    tc.BrightenWhiteLampBy20P(bridge,driver,utcdate);

    std.SelTurnOFFALLDummy();
    
    tc.TurnONAllLivingRoomLights(bridge,driver,utcdate);
    
    tc.TurnOFFAllLivingRoomLights(bridge,driver,utcdate);

    tc.TurnONAmbLivingRoomLight(bridge,driver,utcdate);
    
    tc.TurnOFFAmbLivingRoomLight(bridge, driver,utcdate);
    
    tc.TurnLivingRoomOrange(bridge,driver,utcdate);
    
    std.SelTurnOFFALLDummy();
   
    tc.createHTMLReport();
   
    SendEmailForReport sendEmail = new SendEmailForReport();
    sendEmail.sendEmail();

    driver.close();
  
    System.exit(0);
    
  }
   
}