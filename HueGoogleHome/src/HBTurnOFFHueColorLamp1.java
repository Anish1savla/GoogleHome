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

public class HBTurnOFFHueColorLamp1 {
	
	  public String noHueColorLamp1;
	  public int noHueColorLamp1Counter = 0;
	  public String results;
	  public String Status;
	  public String Remarks;
	  public String sendTohtml;
	  public int HueColorLampOneCounter=0;
	  public String HueColorLampName = "Hue Color lamp 1";

	public String HBTurnOFFHueColorLampOne(PHBridge bridge, String utcdate)
			throws InterruptedException, InvalidFormatException, IOException {
		   System.out.println("/***************************Inside Hue Bridge Turn OFF Hue Color Lamp 1 class*********************************/");
		    TimeUnit.SECONDS.sleep(27);
		    PHBridgeResourcesCache cache = bridge.getResourceCache();
		    
		    //List<String> nonReachableLights = new ArrayList<String>();
		    List<PHLight> allLights = cache.getAllLights();
		    for (PHLight lights : allLights)
		    {
		      PHLightState lightState = lights.getLastKnownLightState();
		      Boolean x = lightState.isOn();
		      System.out.println(lights.getName());
		      System.out.println(x);
		      
		      String lightName = lights.getName();
		      
		      if(lightName.equalsIgnoreCase("Hue Color lamp 1")==true && x==false){
		    	  System.out.println("Inside IF");
		    	  results = "PASS";
		    	  Status = "Hue color Lamp 1 Turned OFF";
		    	  Remarks = " ";
		    	  sendTohtml = createHTMLReport(results,Status,Remarks);
		      }
		      else if(lightName.equalsIgnoreCase("Hue Color lamp 1")==true && x==true){
		    	  System.out.println("Inside ELSE");
		    	  results = "FAIL";
		    	  Status = "Hue color Lamp 1 is Still ON";
		    	  Remarks = "Please check Hue Bridge network and Lights Configuration and Manually Verify the Test.";
		    	   sendTohtml = createHTMLReport(results,Status,Remarks);
		      }
		      
		    }
		    
		   // CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
		    
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
							"Values('"+utcdate+"','7','1','Hue Color Lamp 1 Is OFF','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
					myStmt.executeUpdate(sql);
				/*	System.out.println("Putting data into excel-Inside IF");
			    	
			    	cdsr.ReportTurnOFFHueColorLamp1("PASS");*/
			    }else {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
							"Values('"+utcdate+"','7','0','Hue Color Lamp 1 Is Still ON','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
					myStmt.executeUpdate(sql);
			    	/*System.out.println("Putting data into excel-Inside ELSE");
			    	cdsr.ReportTurnOFFHueColorLamp1("FAIL");*/
			    }

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		return sendTohtml;
		
}
	
	public String createHTMLReport(String results, String Status, String Remarks){
		System.out.println("htmlResults: " + results + " htmlStatus: " + Status + " htmlRemarks :" + Remarks);
		 String htmlString1 = 
			      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n7</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn OFF Hue color Lamp 1</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nHue Color Lamp 1 Should Turn OFF</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + Status+ "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + results + "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + Remarks + "</td>\n" + "</tr>\n";
			    
		return htmlString1;
		
		
	}
}
	