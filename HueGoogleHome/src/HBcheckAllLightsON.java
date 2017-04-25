import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.util.Properties;
import java.util.TimeZone;
import java.sql.*;
import java.text.SimpleDateFormat;
public class HBcheckAllLightsON
{
  public static int counter = 0;
  public String results;
  public String Status;
  public String sendtoHTMLturnOFFAll;
  public String Remarks;
  private PHHueSDK phHueSDK;
  public String HBTurnONAllLight(PHBridge bridge, String utcdate)
    throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException
  {
	  
	  System.out.println("/**************************** INSIDE TURN ON ALL LIGHTS **********************************/");
    //TimeUnit.SECONDS.sleep(27);
	
	  
	long start_time = System.currentTimeMillis();
	
			
    PHBridgeResourcesCache cache1 = bridge.getResourceCache();
    
    List<PHLight> allLights1 = cache1.getAllLights();
    int numberoflights=allLights1.size();
    long wait_time = (numberoflights*2000);
    long end_time = start_time+wait_time;
    System.out.println("Wait Time:"+wait_time);
    System.out.println("End_Time:"+end_time);
    System.out.println("Start time:"+start_time);
    
    List<String> lightList = new ArrayList<String>();
    List<String> nonReachablelightList = new ArrayList<String>();
    
/*    do{
    	System.out.println("Inside DO while");
    }while((System.currentTimeMillis()-start_time)<wait_time);*/
    
    Thread.sleep(wait_time);
    
    do{
    	PHBridgeResourcesCache cache2 = bridge.getResourceCache();
        
        List<PHLight> allLights = cache2.getAllLights();
        
        
        counter++;
        for (PHLight lights : allLights)
        {
    	
        	PHLightState lightState = lights.getLastKnownLightState();
        	Boolean x = lightState.isOn();
	      System.out.println(lights.getName());
	      System.out.println(x);
	      
	      Boolean y = lightState.isReachable();
	      
	      if (x==false && y==true && lightList.contains(lights.getName())==false) {
	        
	          lightList.add(lights.getName());
	          
	          
	          System.out.println("light list size in if:"+lightList.size());
	        }
	        else if (y==false)
	        {
	          nonReachablelightList.add(lights.getName());
	        }else if (x==true) {
	        	lightList.remove(lights.getName());
	        }
	      
	    }
        System.out.println("Light list size:"+lightList.size());
	    System.out.println("System time in mili seconds:"+System.currentTimeMillis());
	    System.out.println("Counter Value:"+counter);
   }while(((System.currentTimeMillis()-start_time)<30000) && lightList.size()!=0);
    
    
    if (lightList.isEmpty())
    {
      this.Status = "PASS";
      if (nonReachablelightList.isEmpty())
      {
        results = "All lights turned ON";
        Remarks = "";
      }
      else
      {
        results = "All lights are ON. However few lights are Not Reachable.";
        Remarks = (nonReachablelightList.toString() + " : Lights are not reachable, please check Hue Bridge and Lights Settings.");
      }
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    else
    {
      results = (lightList.toString() + ": Lights are Still OFF");
      
      Status = "FAIL";
      Remarks = ("Please check Network Connection, Hue Bridge connection in Google Home and Light Status Manually" + nonReachablelightList.toString() + ":Lights are not reachable");
      sendtoHTMLturnOFFAll = createHTMLReport(results, Status, Remarks);
    }
    System.out.println("Putting data into excel");
    //CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
    
    try{
    	 String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
    	 
    	 String SoftwareVersion = bridge.getResourceCache().getBridgeConfiguration().getSoftwareVersion();
    	 
    	 
    	/*TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String utcdate = sdf.format(calendar.getTime());*/
    	
		Connection myConn = DriverManager.getConnection("jdbc:mysql://yy019992.code1.emi.philips.com:3306/iv_us", 
				"iv_us_user","PaloAltoTeam");
		System.out.println("Connection with MYSQL Complete");
		
		Statement myStmt = myConn.createStatement();
		
		if(Status=="PASS")
	    {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
					"Values('"+utcdate+"','1','1','All Lights Turned ON','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
			/*System.out.println("Putting data into excel-Inside IF");
	    	
	    	cdsr.ReportTurnONAllLights("PASS");*/
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
					"Values('"+utcdate+"','1','0','All Lights Didnt Turned ON','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
	    	/*System.out.println("Putting data into excel-Inside ELSE");
	    	cdsr.ReportTurnONAllLights("FAIL");*/
	    }

    }catch (Exception e){
    	e.printStackTrace();
    }
   System.out.println("Result:"+results+"Status:"+Status+"Remarks:"+Remarks);
    return this.sendtoHTMLturnOFFAll;
  }
  
  public String createHTMLReport(String resultString, String statusString, String resultRemarks)
  {
    /*String htmlString = "<center>\n<table width=\"80%\" style=\"border:1px solid black;border-collapse:collapse\">\n"
    		+ "<tr>\n<th style=\"border:1px solid black;border-collapse:collapse\">\nTest ID</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nTest Command Name</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nExpected Results</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nActual Results</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nStatus</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nRemarks</th>\n</tr>\n";*/
    
    String htmlString1 = //htmlString + 
      "<tr>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\"><font face=\"Verdana\">\n" + "1" + "</td>\n"+"</font>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\"><font face=\"Verdana\">\n" + "Turn ON All Lights" + "</td>\n" +"</font>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + "All lights Present on Bridge should Turn ON" + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + resultString + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + statusString + "</td>\n" + 
      "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + resultRemarks + "</td>\n" + "</tr>\n";
    return htmlString1;
  }
}