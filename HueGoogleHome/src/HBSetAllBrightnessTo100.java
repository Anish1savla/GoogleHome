import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HBSetAllBrightnessTo100
{
  public static int counter = 0;
  public String results;
  public String Status;
  public String sendtoHTMLsetBrightness100;
  public String Remarks;
  
  public String HBSetBrightnessTo100Percent(PHBridge bridge)
    throws InterruptedException, InvalidFormatException, IOException
  {
    System.out.println("Inside HBCheck change red color");
    
    TimeUnit.SECONDS.sleep(27);
    PHBridgeResourcesCache cache = bridge.getResourceCache();
    
    HashMap<String, Integer> TrueLightListHash = new HashMap<String, Integer>();
    HashMap<String, Integer> FalseLightListHash = new HashMap<String, Integer>();
    List<PHLight> allLights = cache.getAllLights();
    List<String> FalselightList = new ArrayList<String>();
    List<String> TruelightList = new ArrayList<String>();
    List<String> nonReachablelightList = new ArrayList<String>();
    List<String> nonDimmingLights = new ArrayList<String>();
    for (PHLight lights : allLights)
    {
      PHLight.PHLightType lightType = lights.getLightType();
      String br = lightType.toString();
      
      //System.out.println(br);
      //System.out.println(lights.getName());
      
      PHLightState lightState = lights.getLastKnownLightState();
      int BrightnessLevel = lightState.getBrightness().intValue();
      
      Boolean x = lightState.isReachable();
      if ((!br.toString().equals("ON_OFF_LIGHT")) && (BrightnessLevel == 254) && (x.booleanValue()))
      {
        TruelightList.add(lights.getName());
        TrueLightListHash.put(lights.getName(), lightState.getBrightness());
      }
      else if ((!br.toString().equals("ON_OFF_LIGHT")) && (BrightnessLevel != 254) && (x.booleanValue()))
      {
        FalselightList.add(lights.getName());
        FalseLightListHash.put(lights.getName(), lightState.getBrightness());
      }
      else if ((br.toString().equals("ON_OFF_LIGHT")) && (x.booleanValue()))
      {
        nonDimmingLights.add(lights.getName());
      }
      else if (!x.booleanValue())
      {
        nonReachablelightList.add(lights.getName());
      }
    }
    if ((FalselightList.isEmpty()) && (nonDimmingLights.isEmpty()) && (nonReachablelightList.isEmpty()))
    {
      results = "PASS";
      Status = "Brightness for All Lights is 100%.";
      Remarks = "";
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((FalselightList.isEmpty()) && (!nonDimmingLights.isEmpty()) && (nonReachablelightList.isEmpty()))
    {
      results = "PASS";
      Status = "Brightness for All Lights is 100%.";
      Remarks = (nonDimmingLights.toString() + ": Are Non Dimming Lights.");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((FalselightList.isEmpty()) && (nonDimmingLights.isEmpty()) && (!nonReachablelightList.isEmpty()))
    {
      results = "PASS";
      Status = "Brightness for All Lights is 100%.";
      Remarks = (nonReachablelightList.toString() + ": Lights are Not Reachable");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((FalselightList.isEmpty()) && (!nonDimmingLights.isEmpty()) && (!nonReachablelightList.isEmpty()))
    {
      results = "PASS";
      Status = "Brightness for All Lights is 100%. However few lights are not reachable.";
      Remarks = (nonDimmingLights.toString() + ": Are Non Dimming Lights." + nonReachablelightList.toString() + " : Are Non Reachable Lights.");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((!FalselightList.isEmpty()) && (nonDimmingLights.isEmpty()) && (nonReachablelightList.isEmpty()))
    {
      results = "FAIL";
      Status = ("Brightness for lights: " + FalselightList.toString() + " are not 100%." + FalseLightListHash.toString());
      Remarks = ("Please check Hue Lights and Bridge Connection. Manually Test Command \"Set Brightness to 100%\" " + FalseLightListHash.toString());
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((!FalselightList.isEmpty()) && (!nonDimmingLights.isEmpty()) && (nonReachablelightList.isEmpty()))
    {
      results = "FAIL";
      Status = ("Brightness for lights: " + FalselightList.toString() + " are not 100%. ");
      Remarks = 
        ("Brightness Level :" + FalseLightListHash.toString() + nonDimmingLights.toString() + ": Lights are Not Dimming Lights.");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((!FalselightList.isEmpty()) && (nonDimmingLights.isEmpty()) && (!nonReachablelightList.isEmpty()))
    {
      results = "FAIL";
      Status = ("Brightness for lights: " + FalselightList.toString() + " are not 100%. ");
      Remarks = ("Brightness Level :" + FalseLightListHash.toString() + nonReachablelightList.toString() + ": Lights are Not Reachable.");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    else if ((!FalselightList.isEmpty()) && (!nonDimmingLights.isEmpty()) && (!nonReachablelightList.isEmpty()))
    {
      results = "FAIL";
      Status = ("Brightness for lights: " + FalselightList.toString() + " are not 100%. ");
      Remarks = 
        ("Brightness Level :" + FalseLightListHash.toString() + nonReachablelightList.toString() + ": Lights are Not Reachable." + nonDimmingLights.toString() + ": Lights are Non Dimmable Lights.");
      sendtoHTMLsetBrightness100 = createHTMLReport(Status, results, Remarks);
    }
    //System.out.println("HTML Data from Brightness 100%" + this.sendtoHTMLsetBrightness100);
   // CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
    
    try{
   	 String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
   	TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String utcdate = sdf.format(calendar.getTime());
   	
		Connection myConn = DriverManager.getConnection("jdbc:mysql://yy019992.code1.emi.philips.com:3306/iv_us", 
				"iv_us_user","PaloAltoTeam");
		System.out.println("Connection with MYSQL Complete");
		
		Statement myStmt = myConn.createStatement();
		
		if(results=="PASS")
	    {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
					"Values('"+utcdate+"','5','1','Brightness for All Lights Set to 100%','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
					"Values('"+utcdate+"','5','0','Brightness for All Lights Set to 100%','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);
	    }

   }catch (Exception e){
   	e.printStackTrace();
   }
    
/*    if(results=="PASS")
    {
    	System.out.println("Putting data into excel-Inside IF");
    	cdsr.ReportSetAllLightsTo100P("PASS");
    }else if(results=="FAIL"){
    	System.out.println("Putting data into excel-Inside ELSe");
    	cdsr.ReportSetAllLightsTo100P("FAIL");
    }*/
    
    return this.sendtoHTMLsetBrightness100;
  }
  
  public String createHTMLReport(String results, String status, String remarks)
  {
    String htmlSetBrightness100 = 
      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n5</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nSet Brightness For All Lights To 100%</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nBrightness for All Lights should be set to 100%</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + results  + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + status + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + remarks + "</td>\n" + "</tr>\n";
    
    return htmlSetBrightness100;
  }
}