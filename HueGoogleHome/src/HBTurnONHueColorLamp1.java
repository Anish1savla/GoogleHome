import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HBTurnONHueColorLamp1
{
  public String noHueColorLamp1;
  public int noHueColorLamp1Counter = 0;
  public String results;
  public String Status;
  public String Remarks;
  public String sendTohtml;
  public int HueColorLampOneCounter;
  public String HueColorLampName = "Hue Color lamp 20";
  public String HBTurnONhueColorLampOne(PHBridge bridge)
    throws InterruptedException, InvalidFormatException, IOException
  {
    //System.out.println("/***************************Inside Hue Bridge Turn ON Hue Color Lamp 1 class*********************************/");
    TimeUnit.SECONDS.sleep(27);
    PHBridgeResourcesCache cache = bridge.getResourceCache();
    List<PHLight> allLights = cache.getAllLights();
    List<String> TrueLights = new ArrayList<String>();
    List<String> FalseLights = new ArrayList<String>();
    List<String> nonReachableHuecolorLamp1 = new ArrayList<String>();
    List<String> nonReachableLights = new ArrayList<String>();
    
    for (PHLight lights : allLights)
    {
      PHLightState lightState = lights.getLastKnownLightState();
      
      String lightName = lights.getName();
      //System.out.println(lightName);
      
      Boolean lightStatus = lightState.isOn();
      //Boolean HCL1 = lightName.contains(HueColorLampName);
      //System.out.println("HCL1:"+HCL1);
      Boolean lightReachable = lightState.isReachable();
      if ((lightName.equals("Hue Color Lamp 1")) && (lightReachable.booleanValue()) && (lightStatus.booleanValue()))
      {
        TrueLights.add(lights.getName());
        this.noHueColorLamp1Counter++;
        //System.out.println(noHueColorLamp1Counter);
      }
      else if ((lightName.equals("Hue Color Lamp 1")) && (lightReachable.booleanValue()) && (!lightStatus.booleanValue()))
      {
        FalseLights.add(lights.getName());
      }
      else if ((lightName.equals("Hue Color Lamp 1")) && (!lightReachable.booleanValue()))
      {
        nonReachableHuecolorLamp1.add(lights.getName());
      }
      else if ((!lightName.equals("Hue Color Lamp 1")) && (!lightReachable.booleanValue()))
      {
        nonReachableLights.add(lights.getName());
      }
    }
    //System.out.println(noHueColorLamp1Counter);
    
    if ((FalseLights.isEmpty()) && (nonReachableHuecolorLamp1.isEmpty()))
    {
      results = "PASS";
      Status = "Hue Color Lamp 1 Turned ON";
      if(nonReachableLights.isEmpty()==false){
      Remarks = (nonReachableLights.toString() + ": Are Non Reachable Lights.");
      }else{
    	  Remarks=" ";
      }
      sendTohtml = createHTMLReport(Status, results, Remarks);
    }
    else if (!FalseLights.isEmpty())
    {
      results = "FAIL";
      Status = "Hue Color Lamp 1 is OFF";
      Remarks = (nonReachableLights.toString() + ": Are Non Reachable Lights.");
      sendTohtml = createHTMLReport(Status, results, Remarks);
    }
    else if (!nonReachableHuecolorLamp1.isEmpty())
    {
      results = "FAIL";
      Status = "Hue Color Lamp 1 is Not Reachable";
      Remarks = (nonReachableLights.toString() + ": Are Non Reachable Lights.");
      sendTohtml = createHTMLReport(Status, results, Remarks);
    }else if(noHueColorLamp1Counter==0){
        results = "FAIL";
        Status = "Hue Bridge Doesn't have Hue Color Lamp 1";
        Remarks = ("Please make sure Hue Color Lamp 1 is present on Hue Bridge."+nonReachableLights.toString() + ": Are Non Reachable Lights.");
        sendTohtml = createHTMLReport(Status, results, Remarks);
    }
    
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
					"Values('"+utcdate+"','6','1','Hue Color Lamp 1 Turned ON','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
					"Values('"+utcdate+"','6','0','Hue Color Lamp 1 Didnt Turned ON','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);

	    }

   }catch (Exception e){
   	e.printStackTrace();
   }
    
    /*if(results=="PASS")
    {
    	System.out.println("Putting data into excel-Inside IF");
    	cdsr.ReportTurnONHueColorLamp1("PASS");
    }else if(results=="FAIL"){
    	System.out.println("Putting data into excel-Inside ELSe");
    	cdsr.ReportTurnONHueColorLamp1("FAIL");
    }*/
    
    //System.out.println("SendToHTML is:" + this.sendTohtml + "\n");
    return sendTohtml;
  }
  
  public String createHTMLReport(String htmlResults, String htmlStatus, String htmlRemarks)
  {
    //System.out.println("htmlResults: " + htmlResults + " htmlStatus: " + htmlStatus + " htmlRemarks :" + htmlRemarks);
    String htmlString1 = 
      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n6</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn ON Hue color Lamp 1</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nHue Color Lamp 1 Should Turn ON</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
    
    return htmlString1;
  }
}