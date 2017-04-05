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
import org.sikuli.script.FindFailed;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HBBrightenAllLightsBy10P {

	public int OldBrightnessValue;
	public int TenPofOldBrightnessValue;
	public int ExpectedNewBrightnessValue;
	public String Status;
	public String Results;
	public String Remarks;
	public String SendToHTML;
	public String htmlFinalResults;

	
	public String BrightenAllLights(PHBridge bridge, WebDriver driver) throws FindFailed, InterruptedException, InvalidFormatException, IOException{
		
		System.out.println("/*************************** INSIDE BRIGHTEN ALL LIGHTS By 10% class *********************************/");
	    
		 PHBridgeResourcesCache cache = bridge.getResourceCache();
		    
		    List<PHLight> allLights = cache.getAllLights();
		    
		    HashMap<String,Integer> OldLightState = new HashMap<String,Integer>();
		    HashMap<String,Integer> NewLightState = new HashMap<String,Integer>();
		    
		    HashMap<String,Integer> TrueLights = new HashMap<String,Integer>();
		    HashMap<String,Integer> FalseLights = new HashMap<String,Integer>();
		    List<String> nonReachableLights = new ArrayList<String>();
		    
		    
		    for (PHLight lights : allLights)
		    {
		    	
		    	System.out.println("Inside 1st For Loop");
		    	System.out.println("Light Name:"+lights.getName());
		    	
		    	PHLightState lightState = lights.getLastKnownLightState();
		    	
		    	if(lightState.isReachable()==false){
		    		nonReachableLights.add(lights.getName());
		    	}
		    	
		    	int BrightnessLevel = lightState.getBrightness().intValue();
		            	
		        OldLightState.put(lights.getName(),BrightnessLevel);
		    	
		    }
		    
  
			selBrightenAllBy10P  selbaby10p = new selBrightenAllBy10P();
		    selbaby10p.BrightenAllBy10Percent(bridge,driver);
		
		    TimeUnit.SECONDS.sleep(27);
			PHBridgeResourcesCache cache1 = bridge.getResourceCache();
			List<PHLight> allLights1 = cache1.getAllLights();
		    
		    
		    for (PHLight lights : allLights1)
		    {
		    	PHLightState lightState = lights.getLastKnownLightState();
		    	
		    	int BrightnessLevel = lightState.getBrightness().intValue();
		        	    	
		        NewLightState.put(lights.getName(),BrightnessLevel);
		    	
		    }
		    
		    for (Entry<String, Integer> Oldkey : OldLightState.entrySet()){
		    	
		    	for(Entry<String, Integer> NewKey : NewLightState.entrySet()){
		    			
		    			OldBrightnessValue = Oldkey.getValue();
		    			TenPofOldBrightnessValue = (OldBrightnessValue *10)/100; 
		    			
		    			ExpectedNewBrightnessValue = OldBrightnessValue+TenPofOldBrightnessValue;
		    			
		    			if(NewKey.getValue()==ExpectedNewBrightnessValue){
		    				TrueLights.put(NewKey.getKey(), NewKey.getValue());
		    			}else{
		    				FalseLights.put(NewKey.getKey(),NewKey.getValue());
		    			}
		    	}
		    }
		    
		    if(FalseLights.isEmpty()==true){
		    	Status="PASS";
		    	Results="All Lights are Brighten by 10%";
		    	if(nonReachableLights.isEmpty()==true){
		    		Remarks="Old Brightness Value for Lights:"+OldLightState.toString()+". New Brightness Value for Lights:"+NewLightState.toString();
		    	}
		    	else{
		    		Remarks="Old Brightness Value for Lights:"+OldLightState.toString()
		    		+". New Brightness Value for Lights:"+NewLightState.toString()
		    		+". Lights are Non Reachable:"+nonReachableLights.toString();
		    	}
		    	SendToHTML=createHTMLReport(Status,Results,Remarks);
		    	
		    }else if(FalseLights.isEmpty()==false){
		    	Status="FAIL";
		    	Results="All Lights Didn't brightned by 10%";
		    	if(nonReachableLights.isEmpty()==true){
		    		Remarks="Old Brightness Value for Lights:"+OldLightState.toString()+". New Brightness Value for Lights:"+NewLightState.toString();
		    	}
		    	else{
		    		Remarks="Old Brightness Value for Lights:"+OldLightState.toString()
		    		+". New Brightness Value for Lights:"+NewLightState.toString()
		    		+". Lights are Non Reachable:"+nonReachableLights.toString();
		    	}
		    	SendToHTML=createHTMLReport(Status,Results,Remarks);
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
							"Values('"+utcdate+"','11','1','All Lights brightned by 10%','"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
					
			    }else {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
							"Values('"+utcdate+"','11','0','All Lights Didnt brightned by 10%','"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
			    
			    }

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		    
		/*    if(Status=="PASS")
		    {
		    	System.out.println("Putting data into excel-Inside IF");
		    	cdsr.ReportBrightenBy10P("PASS");
		    }else if(Status=="FAIL"){
		    	System.out.println("Putting data into excel-Inside ELSe");
		    	cdsr.ReportBrightenBy10P("FAIL");
		    }
		    */
		return SendToHTML;
	}
	
	public String createHTMLReport(String htmlStatus,String htmlResults,String htmlRemarks){
		//System.out.println("Status:"+htmlStatus+" Results:"+htmlResults+" Remarks:"+htmlRemarks);
		htmlFinalResults = 
			      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n11</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n Brighten All Lights By 10%</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n All Lights Should be Brighten by 10% of current Brightness Level.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlStatus  + "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		return htmlFinalResults;
	}
	
}