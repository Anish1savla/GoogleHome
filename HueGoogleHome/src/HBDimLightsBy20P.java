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

public class HBDimLightsBy20P {

	public int NewCalculatedBrightness;
	public String Status;
	public String Results;
	public String Remarks;
	public String sendToHTML;
	public String htmlFinalResults;
	
	public String DimAllLightsBy20Percent(PHBridge bridge, WebDriver driver) throws FindFailed, InterruptedException, InvalidFormatException, IOException{
		System.out.println("/*************************** INSIDE DIM ALL LIGHTS By 20% class *********************************/");
	    
		 PHBridgeResourcesCache cache = bridge.getResourceCache();
		    
		 HashMap<String,Integer> OldBrightnessLevel = new HashMap<String,Integer>();
		 HashMap<String,Integer> NewBrightnessLevel = new HashMap<String,Integer>();
		 
		 HashMap<String,Integer> TrueLights = new HashMap<String,Integer>();
		 HashMap<String,Integer> FalseLights = new HashMap<String,Integer>();
		 List<String> nonReachableLights = new ArrayList<String>();
		 
		    List<PHLight> allLights = cache.getAllLights();
		    
		    for (PHLight lights : allLights)
		    {
		    	PHLightState lightState = lights.getLastKnownLightState();
		    	
		    	if(lightState.isReachable()==false){
		    	nonReachableLights.add(lights.getName());
		    	}
		    	OldBrightnessLevel.put(lights.getName(), lightState.getBrightness());
		    	    	
		    }
		    
		    selDimLightsBy20P sdl20p = new selDimLightsBy20P();
		    sdl20p.selDimAllLightsBy20P();
		    
		    TimeUnit.SECONDS.sleep(27);
		    
		    List<PHLight> allLights1 = cache.getAllLights();
		    
		    for(PHLight lights:allLights1){
		    	
		    	PHLightState lightState=lights.getLastKnownLightState();
		    	
		    	NewBrightnessLevel.put(lights.getName(), lightState.getBrightness());
		    	
		    }
		    
		    for (Entry<String, Integer> Oldkey : OldBrightnessLevel.entrySet()){
		    	
		    	for(Entry<String, Integer> NewKey : NewBrightnessLevel.entrySet()){
		    		
		    		if(NewKey.getKey()==Oldkey.getKey()){
		    			
		    			NewCalculatedBrightness= Oldkey.getValue()-(Oldkey.getValue()*20)/100;
		    			
		    			if(NewKey.getValue()==NewCalculatedBrightness){
		    				TrueLights.put(NewKey.getKey(), NewKey.getValue());
		    			}else{
		    				FalseLights.put(NewKey.getKey(), NewKey.getValue());
		    			}
		    		}
		    		
		    	}
		    }
		    
		    if(FalseLights.isEmpty()==true){
		    	Status = "PASS";
		    	Results="Lights are Dimmed by 20%.";
		    	if(nonReachableLights.isEmpty()==true){
		    		Remarks="Old Brightness Level of Lights:"+OldBrightnessLevel.toString()
		    		+". New Brightness Level of Lights: "+NewBrightnessLevel.toString();
		    	}else{
		    		Remarks="Old Brightness Level of Lights:"+OldBrightnessLevel.toString()
		    		+". New Brightness Level of Lights: "+NewBrightnessLevel.toString()+". Uncreachable Lights:"+nonReachableLights.toString();
		    	}
		    	sendToHTML=createHTMLReport(Status,Results,Remarks);
		    }
		    else if(FalseLights.isEmpty()==false){
		    	Status="FAIL";
		    	Results="All Lights are not Dimmed by 20%.";
		    	if(nonReachableLights.isEmpty()==true){
		    		Remarks="Old Brightness Level of Lights:"+OldBrightnessLevel.toString()
		    		+". New Brightness Level of Lights: "+NewBrightnessLevel.toString();
		    	}else{
		    		Remarks="Old Brightness Level of Lights:"+OldBrightnessLevel.toString()
		    		+". New Brightness Level of Lights: "+NewBrightnessLevel.toString()+". Uncreachable Lights:"+nonReachableLights.toString();
		    	}
		    	sendToHTML=createHTMLReport(Status,Results,Remarks);
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
				
				if(Status=="PASS")
			    {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
							"Values('"+utcdate+"','13','1','All Lights are Dimmed by 20%','"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
					
			    }else {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
							"Values('"+utcdate+"','13','0','All Lights are Not Dimmed by 20%,'"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
			
			    }

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		    
		/*    if(Status=="PASS")
		    {
		    	System.out.println("Putting data into excel-Inside IF");
		    	cdsr.ReportDimLightsBy20P("PASS");
		    }else if(Status=="FAIL"){
		    	System.out.println("Putting data into excel-Inside ELSe");
		    	cdsr.ReportDimLightsBy20P("FAIL");
		    }*/
		    
			return sendToHTML;
		    
	}
	
	public String createHTMLReport(String htmlStatus,String htmlResults,String htmlRemarks){
		System.out.println("Status:"+htmlStatus+"Results:"+htmlResults+"Remarks:"+htmlRemarks);
		htmlFinalResults = 
			      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n13</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n Dim the Lights By 20%</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n All Lights Should be Dimmed by 20% of current Brightness Level.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		return htmlFinalResults;
		
	}

}