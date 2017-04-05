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

public class HBcheckAllLightsOFF
{
  public static int counter = 0;
  public String results;
  public String Status;
  public String Remarks;
  public String sendTohtml;
  
  public String HBTurnOFFAlllight(PHBridge bridge)
    throws InterruptedException, InvalidFormatException, IOException
  {
    //System.out.println("/***************************Inside Hue Bridge Turn OFF All Lights class*********************************/");
    TimeUnit.SECONDS.sleep(30);
    PHBridgeResourcesCache cache = bridge.getResourceCache();
    List<PHLight> allLights = cache.getAllLights();
    List<String> lightList = new ArrayList<String>();
    List<String> nonReachablelightList = new ArrayList<String>();
    for (PHLight lights : allLights)
    {
      PHLightState lightState = lights.getLastKnownLightState();
      Boolean x = lightState.isOn();
      
      Boolean y = lightState.isReachable();
      if ((x.booleanValue()) || (!y.booleanValue())) {
        if ((x.booleanValue()) && (y.booleanValue()))
        {
          lightList.add(lights.getName());
          
          counter += 1;
        }
        else if (!y.booleanValue())
        {
          nonReachablelightList.add(lights.getName());
        }
      }
    }
    if (lightList.isEmpty())
    {
      Status = "PASS";
      if (nonReachablelightList.isEmpty())
      {
        results = "All lights are OFF";
        Remarks = "";
      }
      else
      {
        results = "All lights are OFF.However few lights are Not Reachable.";
        Remarks = (nonReachablelightList.toString() + ": Lights are not reachable. Please check your Hue Bridge and Lights Settings");
      }
      sendTohtml = createHTMLReport(this.results, this.Status, this.Remarks);
    }
    else
    {
      results = (lightList.toString() + ": Lights are Still ON.");
      
      Status = "FAIL";
      Remarks = ("Please check Network Connection, Hue Bridge connection in Google Home and Light Status Manually. " + nonReachablelightList.toString() + ":Lights are not reachable");
      sendTohtml = createHTMLReport(results, Status, Remarks);
    }
    
  try{
	  String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
	 // CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
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
					"Values('"+utcdate+"','2','1','All Lights Turned OFF','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);
		/*	System.out.println("Putting data into excel-Inside IF");
	    	
	    	cdsr.ReportTurnOFFAllLights("PASS");*/
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
					"Values('"+utcdate+"','2','0','All Lights Didnt Turned OFF','"+Remarks+"','"+BridgeAPIVersion+"')";
			myStmt.executeUpdate(sql);
	    /*	System.out.println("Putting data into excel-Inside ELSE");
	    	cdsr.ReportTurnOFFAllLights("FAIL");*/
	    }
	    
		
		
    }catch (Exception e){
    	e.printStackTrace();
    }
    
    return this.sendTohtml;
  }
  
  public String createHTMLReport(String htmlResults, String htmlStatus, String htmlRemarks)
  {
    String htmlString1 = "<tr>\n" + 
    	      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "2" + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "TurnOFFAllLights" + "</td>\n" + 
    "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "All lights Present on Bridge should Turn OFF" + "</td>\n"  
     + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
    return htmlString1;
  }
}