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

public class HBCheckAllLightsGreen
{
  public static int counter = 0;
  public String results;
  public String Status;
  public String sendtoHTMLturnOFFAll;
  public String Remarks;
  public int OldintX;
  public int OldintY;
  public int NewintX;
  public int NewintY;
  public double xColor;
  public double yColor;
  
  public String HBCheckAllLightsToGreen(PHBridge bridge)
    throws InterruptedException, InvalidFormatException, IOException
  {
    //System.out.println("/*************************Inside HBCheck change Green color****************************/");
    
    TimeUnit.SECONDS.sleep(50);
    PHBridgeResourcesCache cache = bridge.getResourceCache();
    
    List<PHLight> allLights = cache.getAllLights();
    List<String> lightList = new ArrayList<String>();
    List<String> reachablelightList = new ArrayList<String>();
    List<String> nonReachablelightList = new ArrayList<String>();
    List<String> nonColorLights = new ArrayList<String>();
    for (PHLight lights : allLights)
    {
      PHLight.PHLightType lightType = lights.getLightType();
      String br = lightType.toString();
      //System.out.println("Light Type:" + br);
      //System.out.println("Light Name:" + lights.getName());
      
      PHLightState lightState = lights.getLastKnownLightState();
      Float colorX = lightState.getX();
      Float colorY = lightState.getY();
      Boolean x = lightState.isReachable();
      //System.out.println("Turn all lights Green X value:" + colorX);
      //System.out.println("Turn all lights Green Y value:" + colorY);
      //System.out.println("Light is Reachable?:" + x);
      if ((colorX != null) && (colorY != null))
      {
        xColor = colorX.floatValue();
        yColor = colorY.floatValue();
      }
      else
      {
        xColor = -1.0D;
        yColor = -1.0D;
      }
      //System.out.println(this.xColor);
      //System.out.println(this.yColor);
      
      String lightversion = lights.getVersionNumber();
      
      System.out.println("Lighstrip or not:" + lights.getName().contains("lightstrip"));
      if (((lightversion.equals("1.15.2_r19181")) || ((lights.getName().contains("lightstrip")) && (lightversion == "5.50.1.19085"))) && 
        (colorX != null) && (colorY != null))
      {
        if ((xColor >= 0.17) && (xColor <= 0.173) && (yColor >= 0.69998) && (yColor <= 0.75))
        {
          OldintX = 1;
          OldintY = 1;
        }
        else
        {
          OldintX = 0;
          OldintY = 0;
        }
      }
      else if ((lightversion.equals("5.50.1.19085")) && (colorX != null) && (colorY != null) && (!lights.getName().contains("lightstrip")))
      {
        if ((xColor >= 0.409) && (xColor <= 0.41) && (yColor >= 0.517) && (yColor <= 0.519))
        {
          NewintX = 1;
          NewintY = 1;
        }
        else
        {
          NewintX = 0;
          NewintY = 0;
        }
      }
      else if (lights.getName().contains("lightstrip"))
      {
    	 // System.out.println("Inside lightstrip IF check");
        if ((xColor >= 0.17) && (xColor <= 0.173) && (yColor >= 0.69555) && (yColor <= 0.75005))
        {
          NewintX = 1;
          NewintY = 1;
        }
        else
        {
          NewintX = 0;
          NewintY = 0;
        }
      }
      else {
        nonColorLights.add(lights.getName());
      }
      if ((!lightType.toString().equals("CT_LIGHT")) && (!lightType.toString().equals("DIM_LIGHT")) && 
        ((OldintX == 1) || (NewintX == 1)) && ((OldintY == 1) || (NewintY == 1)) && (x.booleanValue()))
      {
        //System.out.println("Inside IF to check RED x Y values");
        reachablelightList.add(lights.getName());
      }
      else if ((x.booleanValue()) && (OldintX != 1) && (NewintX != 1) && (OldintY != 1) && (NewintY != 1) && 
        (!lightType.toString().equals("CT_LIGHT")) && (!lightType.toString().equals("DIM_LIGHT")))
      {
        //System.out.println("Inside 1st ELSE IF to check RED x Y values");
        lightList.add(lights.getName());
      }
      else if (!x.booleanValue())
      {
        //System.out.println("Inside 2nd ELSE IF to check RED not reachable values");
        nonReachablelightList.add(lights.getName());
      }
    }
    
    //String reachableLights="Reachable light List:"+reachablelightList.toString();
    //System.out.println(reachableLights);
    //String lightList1 = "lightList:"+lightList.toString();
    //System.out.println(lightList1);
    //String nonReachablelightList1="Non Reachable lights:"+nonReachablelightList.toString();
    //System.out.println(nonReachablelightList1);
    
    if (lightList.isEmpty())
    {
      //System.out.println("Inside IF to PASS");
      Status = "PASS";
      if (nonReachablelightList.isEmpty())
      {
        results = "All lights turned GREEN";
        Remarks = (nonColorLights.toString() + ": Are Not Color Lights");
      }
      else
      {
        //System.out.println("Inside IF to FAIL");
        results = "All lights turned GREEN. However few lights are Not Reachable.";
        Remarks = (nonReachablelightList.toString() + " : Lights are not reachable, please check Hue Bridge and Lights Settings." + nonColorLights.toString() + ": Are Not Color Lights");
      }
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    else
    {
      results = (lightList.toString() + ": Lights didn't Turn GREEN");
      
      Status = "FAIL";
      Remarks = ("Please check Network Connection, Hue Bridge connection in Google Home and Light Color Status Manually. " + nonReachablelightList.toString() + ":Lights are not reachable");
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    
    //CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
    
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
   		
   		if(Status=="PASS")
   	    {
   			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
   					"Values('"+utcdate+"','4','1','All Lights Turned GREEN','"+Remarks+"','"+BridgeAPIVersion+"')";
   			myStmt.executeUpdate(sql);
   	
   	    }else {
   			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
   					"Values('"+utcdate+"','4','0','All Lights Didnt Turned GREEN','"+Remarks+"','"+BridgeAPIVersion+"')";
   			myStmt.executeUpdate(sql);
   	    	
   	    }

      }catch (Exception e){
      	e.printStackTrace();
      }
    
    
/*    if(Status=="PASS")
    {
    	System.out.println("Putting data into excel-Inside IF");
    	cdsr.ReportTurnGreenAllLights("PASS");
    }else if(Status=="FAIL"){
    	System.out.println("Putting data into excel-Inside ELSe");
    	cdsr.ReportTurnGreenAllLights("FAIL");
    }*/
    
    return this.sendtoHTMLturnOFFAll;
  }
  
  public String createHTMLReport(String results, String status, String remarks)
  {
    String htmlChangeColorGreenString = 
    
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "4" + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "Turn Lights Green" + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "All lights Present on Bridge should Turn Green" + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + results + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + status + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + remarks + "</td>\n" + "</tr>\n";
    
    
    return htmlChangeColorGreenString;
  }
}