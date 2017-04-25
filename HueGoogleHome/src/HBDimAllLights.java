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

public class HBDimAllLights {
	
	public String currentOldLightName;
	public String currentNewLightName;
	public float NewCalculatedBrightness;
	public float SixtyPercentBrightnessValue;
	public float floatvalueofbrightness;
	public int finalint;
	public int MaxRange;
	public int MinRange;
	public String Results;
	public String Status;
	public String Remarks;
	public String sendTohtml;
	
	public String HBDimLights(PHBridge bridge, WebDriver driver, String utcdate) 
			throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		
		System.out.println("/***************************Inside Hue Bridge DIM ALL LIGHTS class*********************************/");
	    
		List<String> TrueLights = new ArrayList<String>();
	    List<String> FalseLights = new ArrayList<String>();
	    List<String> nonReachableLights = new ArrayList<String>();

	    PHBridgeResourcesCache cache = bridge.getResourceCache();
	    
	    List<PHLight> allLights = cache.getAllLights();
	    
	    HashMap<String,Integer> OldBrightness = new HashMap<String,Integer>();
	    HashMap<String,Integer> NewBrightness = new HashMap<String,Integer>();
	    
	    
	    for (PHLight lights : allLights)
	    {
	    	
	    	PHLightState lightState = lights.getLastKnownLightState();
	    	if(lightState.isReachable()==false){
	    		nonReachableLights.add(lights.getName());
	    	}else{
	    	
	    	String lightName = lights.getName();
	    	//System.out.println("Light Name is :"+lightName);
	    	
	    	//PHLightState lightState = lights.getLastKnownLightState();
	    	int lightBrightness = lightState.getBrightness();
	    	//System.out.println("Brightness is:"+lightBrightness);
	    	
	    	OldBrightness.put(lightName,lightBrightness);
	    	//System.out.println("Hashmap light:"+OldBrightness.get(lightName));
	    	
	    	//OldBrightness.keySet().stream().forEach(key->System.out.println(key));
	    	//OldBrightness.entrySet().stream().forEach(entry -> System.out.println(entry.getKey()+"-"+entry.getValue()));
	    	}
	    }
	  selDimAllLights sda = new selDimAllLights();
	  sda.selDimLights(bridge, driver);
	  
	  TimeUnit.SECONDS.sleep(27);
	  PHBridgeResourcesCache cache1 = bridge.getResourceCache();
	  List<PHLight> allLights1 = cache1.getAllLights();
	    for(PHLight lights : allLights1)
	    {
	    	PHLightState lightState = lights.getLastKnownLightState();
	    	if(lightState.isReachable()==false){
	    		nonReachableLights.add(lights.getName());
	    	}else{
	    	
	    	String lightName = lights.getName();
	    	//System.out.println("Light Name is :"+lightName);
	    	
	    	
	    	int lightBrightness = lightState.getBrightness();
	    	//System.out.println("Brightness is:"+lightBrightness);
	    	
	    	NewBrightness.put(lightName,lightBrightness);
	    	//System.out.println("Hashmap light:"+NewBrightness.get(lightName));
	    	
	    	//NewBrightness.keySet().stream().forEach(key->System.out.println(key));
	    	//NewBrightness.entrySet().stream().forEach(entry -> System.out.println(entry.getKey()+"-"+entry.getValue()));
	    	}
	    }
	    
	    for (Entry<String, Integer> key : OldBrightness.entrySet()){
	    	
	    	for(Entry<String, Integer> NewKey : NewBrightness.entrySet()){
	    	
	    		currentOldLightName = key.getKey();
	    		currentNewLightName = NewKey.getKey();
	    		Boolean x=currentNewLightName.contains(currentOldLightName);
	    		
	    		if(x==true && nonReachableLights.contains(currentNewLightName)==false){
	    			
	    			floatvalueofbrightness=key.getValue();
	    	
	    			SixtyPercentBrightnessValue = (floatvalueofbrightness*60)/100;
	    	
	    			
	    			NewCalculatedBrightness = key.getValue()-SixtyPercentBrightnessValue;
	    	
	    			
	    			MaxRange = (int) (NewCalculatedBrightness+1);
	    			MinRange = (int) (NewCalculatedBrightness-1);
	    			
	    	
	    			
	    			
	    			if(NewCalculatedBrightness>=MinRange && NewCalculatedBrightness<=MaxRange){
	    				//System.out.println("New Calculated Value is matching");
	    				TrueLights.add(currentNewLightName);
	    			}else{
	    				FalseLights.add(currentNewLightName);
	    			}
	    		}
	    	}
	    	
	    }
	    
	    if(FalseLights.isEmpty()==true){
	    	Results = "PASS";
	    	Status = "All lights are Dimmed by 60% of Current Brightness Level";
	    	if(nonReachableLights.isEmpty()==true){
	    		//Remarks = " ";
	    		Remarks= "Old Brightness level of lights : "+OldBrightness.toString()+"\n"+"New Brightness level of lights: "+NewBrightness.toString();
	    		//System.out.println("Remarks:"+Remarks);
	    	}else {
	    	Remarks = nonReachableLights.toString()+": Are non Reachable Light.";
	    	}
	    	sendTohtml=createHTMLReport(Results,Status,Remarks);
	    }
	    else if(FalseLights.isEmpty()==false){
	    		Results="FAIL";
	    		Status= "Dimming Level of lights : "+FalseLights.toString()+" is incorrect";
	    		Remarks= "Old Brightness level of lights : "+OldBrightness.toString()+"\n"+"New Brightness level of lights: "+NewBrightness.toString();
	    		sendTohtml=createHTMLReport(Results,Status,Remarks);
	    	}
	    
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
			
			if(Results=="PASS")
		    {
				String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason, APIVersion,SWVersion)"+
						"Values('"+utcdate+"','8','1','All Lights are Dimmed','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
				myStmt.executeUpdate(sql);
		    }else {
				String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason, APIVersion,SWVersion)"+
						"Values('"+utcdate+"','8','0','All Lights are not Dimmed','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
				myStmt.executeUpdate(sql);
		    }

	    }catch (Exception e){
	    	e.printStackTrace();
	    }
	    
/*	    if(Results=="PASS")
	    {
	    	System.out.println("Putting data into excel-Inside IF");
	    	cdsr.ReportDimAllLights("PASS");
	    }else if(Results=="FAIL"){
	    	System.out.println("Putting data into excel-Inside ELSe");
	    	cdsr.ReportDimAllLights("FAIL");
	    }*/
	     
    	return sendTohtml;
	}
	
	public String createHTMLReport(String htmlResults, String htmlStatus, String htmlRemarks){
	    System.out.println("htmlResults: " + htmlResults + " htmlStatus: " + htmlStatus + " htmlRemarks :" + htmlRemarks);
	    String htmlString1 = 
	      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n8</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nDim All Lights</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nAll Lights Should be Dimmed by 60% of current Brightness Level.</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlStatus + "</td>\n" 
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlResults + "</td>\n" 
	      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		return htmlString1;
		
	}
	
}