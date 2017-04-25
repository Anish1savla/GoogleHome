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
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HBTurnLROrange {
	
	public  String SendToHTML;
	public String FinalHTMLString;
	public String Result;
	public String Status;
	public String Remarks;
	public float xValue;
	public float yValue;
	public float minXRange;
	public float maxXRange;
	public float minYRange;
	public float maxYRange;
	public String lightType = "CT_COLOR_LIGHT";
	
	
	public String TurnLivingRoomOrange(PHBridge bridge, WebDriver driver, String utcdate) throws InterruptedException, InvalidFormatException, IOException{
		
		HashMap<String,Integer> LRLights = new HashMap<String,Integer>();
		LRLights.put("Hue ambiance lamp 1", 1);
		LRLights.put("Hue Color lamp 1", 2);
		LRLights.put("Hue Color lamp 2", 3);
		LRLights.put("Hue Color lamp 3", 4);
		LRLights.put("Hue go 2", 5);
		
		List<String> TrueLights = new ArrayList<String>();
	    List<String> FalseLights = new ArrayList<String>();
	    List<String> nonReachableLights = new ArrayList<String>();
		
		TimeUnit.SECONDS.sleep(27);
		
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		  List<PHLight> allLights = cache.getAllLights();
		  
		  for(PHLight lights:allLights){
			  
			  PHLightState lightState = lights.getLastKnownLightState();
			  System.out.println(lights.getName());
			  for(Entry<String, Integer> MapKey : LRLights.entrySet()){
				  System.out.println(MapKey.getKey());
				  System.out.println(MapKey.getValue());
				  
				  System.out.println("Light Type:"+lights.getLightType());
				  
				  
				  
				  
				  if(lights.getLightType().toString().equals(lightType)==true){
				  xValue=lightState.getX();
				  yValue=lightState.getY();
				  
				  System.out.println("Light Name:"+lights.getName());
				  
				  
				  System.out.println("xValue:"+xValue);
				  System.out.println("yValue:"+yValue);
				  
				  minXRange = xValue-1;
				  maxXRange = xValue+1;
				  
				  minYRange = yValue-1;
				  maxYRange = yValue+1;
				  
				  if((MapKey.getKey().equalsIgnoreCase(lights.getName())==true) &&
						  ((0.6094>=minXRange && 0.6094<=maxXRange) && (0.3699>=minYRange && 0.3699<=maxYRange)) 
						  && (lightState.isReachable()==true)){
					  TrueLights.add(lights.getName());
				  }else if ((MapKey.getKey().equalsIgnoreCase(lights.getName())==true) &&
						  ((0.6094<minXRange && 0.6094>maxXRange) && (0.3699<minYRange && 0.3699>maxYRange))
						  && (lightState.isReachable()==true)){
					  FalseLights.add(lights.getName());
				  }else if ((MapKey.getKey().equalsIgnoreCase(lights.getName())==true) &&
						  ((0.6094>=minXRange && 0.6094<=maxXRange) && (0.3699>=minYRange && 0.3699<=maxYRange)) 
						  && (lightState.isReachable()==false))
				  {
					  nonReachableLights.add(lights.getName());
				  }
				  }else{
					  break;
				  }
				 
			  }
			  
		  }
		  
		  System.out.println("True Lights:"+TrueLights.toString());
		  System.out.println("False Lights:"+FalseLights.toString());
		  
		  if(FalseLights.isEmpty()==true){
			  Status="PASS";
			  Result="All Lights in Living Room Turned Orange";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks = " ";
			  }else{
				  Remarks=nonReachableLights.toString()+": Non Reachable Lights.";
			  }
			  SendToHTML=createHTMLReport(Status,Result,Remarks);
		  }else if(FalseLights.isEmpty()==false){
			  Status="FAIL";
			  Result="All Lights in Living Room Didn't Turned Orange.";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks = " ";
			  }else{
				  Remarks=FalseLights.toString()+":Lights Didn't Turn Orange in Living Room."
						  +nonReachableLights.toString()+": Non Reachable Lights.";
			  }
			  SendToHTML=createHTMLReport(Status,Result,Remarks);
		  }
		  
		  //CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
		  
		  try{
		    	 String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
		    	 String SoftwareVersion = bridge.getResourceCache().getBridgeConfiguration().getSoftwareVersion(); 
		    /*	TimeZone timeZone = TimeZone.getTimeZone("UTC");
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
							"Values('"+utcdate+"','20','1','All Lights Turned Orange in Living Room','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
					myStmt.executeUpdate(sql);
			    }else {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason, APIVersion,SWVersion)"+
							"Values('"+utcdate+"','20','0','All Lights Didnt Turned Orange in Living Room','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
					myStmt.executeUpdate(sql);

			    }

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		  
/*		    if(Status=="PASS")
		    {
		    	System.out.println("Putting data into excel-Inside IF");
		    	cdsr.TurnLivingRoomOrange("PASS");
		    }else if(Status=="FAIL"){
		    	System.out.println("Putting data into excel-Inside ELSe");
		    	cdsr.TurnLivingRoomOrange("FAIL");
		    }*/
		  
		return SendToHTML;
	}
	
	public String createHTMLReport(String htmlStatus, String htmlResult, String htmlRemarks){
		
		FinalHTMLString="<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n20</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn Living Room Lighs Orange</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nAll Lights in Living Room should turn Orange.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResult + "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		
		return FinalHTMLString;
	}
	
}