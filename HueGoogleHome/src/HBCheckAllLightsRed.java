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

public class HBCheckAllLightsRed
{
  public static int counter = 0;
  public String results;
  public String Status;
  public String sendtoHTMLturnOFFAll;
  public String Remarks;
  public int intX;
  public int intY;
  public double xValue;
  public double y;
  
  public String HBCheckAllLightsTurnedRed(PHBridge bridge, String utcdate)
    throws InterruptedException, InvalidFormatException, IOException
  {
    System.out.println("Inside HBCheck change red color");
    
    TimeUnit.SECONDS.sleep(27);
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
      //System.out.println(br);
      //System.out.println(lights.getName());
      
      PHLightState lightState = lights.getLastKnownLightState();
      Float colorX = lightState.getX();
      Float colorY = lightState.getY();
      
      //System.out.println(colorY);
      //System.out.println(colorX);
      if ((colorX != null) && (colorY != null))
      {
        xValue = colorX.floatValue();
        y = colorY.floatValue();
      }
      else
      {
        xValue = -1.0D;
        y = -1.0D;
      }
      //System.out.println("Double X : " + this.xValue);
      //System.out.println("Double Y : " + this.y);
      
      //String lightversion = lights.getVersionNumber();
      //System.out.println(lightversion);
      
      Boolean x = lightState.isReachable();
      //System.out.println(x);
      if ((colorX != null) && (colorY != null))
      {
        if ((this.xValue <= 0.70061) && (this.xValue >= 0.675) && (this.y >= 0.2992) && (this.y <= 0.3221))
        {
          intX = 1;
          intY = 1;
        }
        else
        {
          intX = 0;
          intY = 0;
        }
      }
      else if ((this.xValue == -1.0) && (this.y == -1.0)) {
        nonColorLights.add(lights.getName());
      }
      //System.out.println(this.intX);
      //System.out.println(this.intY);
      if ((!lightType.toString().equals("CT_LIGHT")) && (!lightType.toString().equals("DIM_LIGHT")) && 
        (intX == 1) && (intY == 1) && (x.booleanValue()))
      {
        //System.out.println("Inside IF to check RED x Y values");
        reachablelightList.add(lights.getName());
      }
      else if ((x.booleanValue()) && (this.intX != 1) && (this.intY != 1) && (!lightType.toString().equals("CT_LIGHT")) && (!lightType.toString().equals("DIM_LIGHT")))
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
    if (lightList.isEmpty())
    {
      //System.out.println("Inside IF to PASS");
      Status = "PASS";
      if (nonReachablelightList.isEmpty())
      {
        results = "All lights turned RED";
        Remarks = (nonColorLights.toString() + ": Are Not Color Lights");
      }
      else
      {
        //System.out.println("Inside IF to FAIL");
        results = "All lights turned RED. However few lights are Not Reachable.";
        Remarks = (nonReachablelightList.toString() + " : Lights are not reachable, please check Hue Bridge and Lights Settings." + nonColorLights.toString() + ": Are Not Color Lights");
      }
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    else
    {
      results = (lightList.toString() + ": Lights didn't Turn RED");
      
      Status = "FAIL";
      Remarks = ("Please check Network Connection, Hue Bridge connection in Google Home and Light Color Status Manually" + nonReachablelightList.toString() + ":Lights are not reachable");
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    
    //CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
    
    try{
   	 String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
   	 String SoftwareVersion = bridge.getResourceCache().getBridgeConfiguration().getSoftwareVersion(); 

 /*  	TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String utcdate = sdf.format(calendar.getTime());*/
   	
		Connection myConn = DriverManager.getConnection("jdbc:mysql://yy019992.code1.emi.philips.com:3306/iv_us", 
				"iv_us_user","PaloAltoTeam");
		System.out.println("Connection with MYSQL Complete");
		
		Statement myStmt = myConn.createStatement();
		
		if(Status=="PASS")
	    {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason, APIVersion,SWVersion)"+
					"Values('"+utcdate+"','3','1','All Lights Turned RED','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
		
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason, APIVersion,SWVersion)"+
					"Values('"+utcdate+"','3','0','All Lights Didnt Turned RED','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
	
	    }

   }catch (Exception e){
   	e.printStackTrace();
   }
    
    
/*    if(Status=="PASS")
    {
    	System.out.println("Putting data into excel-Inside IF");
    	cdsr.ReportTurnAllLightsRED("PASS");
    }else if(Status=="FAIL"){
    	System.out.println("Putting data into excel-Inside ELSe");
    	cdsr.ReportTurnAllLightsRED("FAIL");
    }*/
    
    return this.sendtoHTMLturnOFFAll;
  }
  
  public String createHTMLReport(String results, String status, String remarks)
  {
    String htmlChangeColorRedString = 
      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n3</td>"
      + "\n<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn All Lights RED</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nAll lights Present on Bridge should Turn Red</td>\n"
      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + 
      
      results + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + status + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + remarks + "</td>\n" + "</tr>\n";
    
    return htmlChangeColorRedString;
  }
}