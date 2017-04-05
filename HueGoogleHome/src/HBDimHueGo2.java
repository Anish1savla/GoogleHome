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

public class HBDimHueGo2 {
	
	public String currentOldLightName;
	public String currentNewLightName;
	public String HueGo2Name = "Hue Go 2";
	public int NewCalculatedBrightness;
	public int sixtyPercentOfOldBrightness;
	public int OldlightBrightness;
	public int NewlightBrightness;
	public int ValueofbrightnessForHueGo2;
	public int ValueofOldbrightnessForNonHueGo2;
	public int ValueofNewbrightnessForNonHueGo2;
	public int MaxRange;
	public int MinRange;
	public String Results;
	public String Status;
	public String Remarks;
	public int OldHueGo2BrightnessLevel;
	public int NewHueGo2BrightnessLevel;
	public String sendTohtml;
	
	//List<String> TrueLights = new ArrayList<String>();
    //List<String> FalseLights = new ArrayList<String>();
    List<String> nonReachableLights = new ArrayList<String>();
	
	public String DimHueGo2(PHBridge bridge, WebDriver driver) throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		System.out.println("/***************************Inside Hue Bridge Dim Hue Go 2 class*********************************/");
		
		HashMap<String,Integer> TrueLights = new HashMap<String,Integer>();
		HashMap<String,Integer> FalseLights = new HashMap<String,Integer>();
		HashMap<String,Integer> TrueNonHueGo2Lights = new HashMap<String,Integer>();
		HashMap<String,Integer> FalseNonHueGo2Lights = new HashMap<String,Integer>();
		HashMap<String,Integer> OldBrightness = new HashMap<String,Integer>();
	    HashMap<String,Integer> NewBrightness = new HashMap<String,Integer>();
		
		  PHBridgeResourcesCache cache = bridge.getResourceCache();
		  List<PHLight> allLights = cache.getAllLights();
		  for(PHLight lights : allLights)
		    {
		    	PHLightState lightState = lights.getLastKnownLightState();
		    	
		    	if(lightState.isReachable()==false){
		    		nonReachableLights.add(lights.getName());
		    	}
		    	
		    	String lightName = lights.getName();
		    	OldlightBrightness = lightState.getBrightness();
		    	
		    	OldBrightness.put(lightName,OldlightBrightness);
		    	
		    	if(lightName.equalsIgnoreCase(HueGo2Name)==true){
		    		OldHueGo2BrightnessLevel=lightState.getBrightness();
		    	}	    	
		    }	
		  
		  selDimHueGo2 sdhg2 = new selDimHueGo2();
		  sdhg2.selDimHueGo2();
		  
		  TimeUnit.SECONDS.sleep(27);
		  PHBridgeResourcesCache cache1 = bridge.getResourceCache();
		  List<PHLight> allLight1s = cache1.getAllLights();
		  for(PHLight lights : allLight1s)
		    {
		    	PHLightState lightState = lights.getLastKnownLightState();
		    	
		    	String lightName = lights.getName();
		    	NewlightBrightness = lightState.getBrightness();
		    	NewBrightness.put(lightName, NewlightBrightness);
		    	
		    	if(lightName.equalsIgnoreCase(HueGo2Name)==true){
		    		NewHueGo2BrightnessLevel=lightState.getBrightness();
		    	}
		    }	
		  for (Entry<String, Integer> key : OldBrightness.entrySet()){
		    	
		    	for(Entry<String, Integer> NewKey : NewBrightness.entrySet()){
		    	
		    		currentOldLightName = key.getKey();
		    		currentNewLightName = NewKey.getKey();
		    		Boolean x=currentNewLightName.contains(currentOldLightName);
		    		
		    		if(x==true && nonReachableLights.contains(currentNewLightName)==false && currentNewLightName.equalsIgnoreCase(HueGo2Name)==true){
		    			
		    			ValueofbrightnessForHueGo2=key.getValue();
		    			sixtyPercentOfOldBrightness = (ValueofbrightnessForHueGo2*60)/100;
		    			NewCalculatedBrightness = key.getValue()-sixtyPercentOfOldBrightness;
		    			
		    			MaxRange = (int) (NewCalculatedBrightness+1);
		    			MinRange = (int) (NewCalculatedBrightness-1);
		    			
		    			if(NewCalculatedBrightness <= MaxRange && NewCalculatedBrightness >= MinRange){
		    				TrueLights.put(currentNewLightName, NewCalculatedBrightness);
		    			}else {
		    				FalseLights.put(currentNewLightName, NewCalculatedBrightness);
		    			}
		    			
		    		}else if(x==true && nonReachableLights.contains(currentNewLightName)==false && currentNewLightName.equalsIgnoreCase(HueGo2Name)==false){
		    			ValueofOldbrightnessForNonHueGo2=key.getValue();
		    			ValueofNewbrightnessForNonHueGo2=key.getValue();
		    			
		    			if(ValueofOldbrightnessForNonHueGo2==ValueofNewbrightnessForNonHueGo2){
		    				TrueNonHueGo2Lights.put(currentNewLightName, ValueofNewbrightnessForNonHueGo2);
		    			}else{
		    				FalseNonHueGo2Lights.put(currentNewLightName, ValueofNewbrightnessForNonHueGo2);
		    			}
		    			
		    		}
		    	}
		    	
		    }
		  	  
		  if(FalseLights.isEmpty()==true && FalseNonHueGo2Lights.isEmpty()==true){
			  Status="PASS";
			  Results="Hue Go 2 Dimmed by 60% of original Brightness level";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks="Old Brightness level of Hue Go 2 :"+OldHueGo2BrightnessLevel
						  +". New Brightness level of Hue Go 2: "+NewHueGo2BrightnessLevel;
						  
			}else {
				Remarks=nonReachableLights.toString()+" : Lights are Non Reachable Lights";
			}
			  sendTohtml=createHTMLReport(Status,Results,Remarks);
		  }else if(FalseLights.isEmpty()==true && FalseNonHueGo2Lights.isEmpty()==false){
			  Status="FAIL";
			  Results="Hue Go 2 Dimmed by 60% of original Brightness level, However other lights also got dimmed";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks="Old Brightness level of other lights:"+OldBrightness.toString()+"\n"+"New Brightness Level of other lights: "+FalseNonHueGo2Lights.toString();
				  
			  }else{
				  Remarks="Old Brightness level of other lights:"+OldBrightness.toString()+"\n"
			  +"New Brightness Level of other lights: "+FalseNonHueGo2Lights.toString()
			  +"Non Reachable Lights: "+nonReachableLights.toString();
			  }
			  sendTohtml=createHTMLReport(Status,Results,Remarks);
		  }else if(FalseLights.isEmpty()==false && FalseNonHueGo2Lights.isEmpty()==false){
			  Status="FAIL";
			  Results="Brightness level of Hue Go 2 & Other lights is incorrect";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks= "Old Brightness level of Hue Go 2 :"+OldHueGo2BrightnessLevel
						  +". New Brightness level of Hue Go 2: "+NewHueGo2BrightnessLevel
						  +". Old Brightness level of other lights:"+OldBrightness.toString()+"\n"
						  +"New Brightness Level of other lights: "+FalseNonHueGo2Lights.toString();
				  
			  }else{
				  Remarks= "Old Brightness level of Hue Go 2 :"+OldHueGo2BrightnessLevel
						  +". New Brightness level of Hue Go 2: "+NewHueGo2BrightnessLevel
						  +". Old Brightness level of other lights:"+OldBrightness.toString()+"\n"
						  +"New Brightness Level of other lights: "+FalseNonHueGo2Lights.toString()
						  +"Non Reachable Lights: "+nonReachableLights.toString();
			  }
			  sendTohtml=createHTMLReport(Status,Results,Remarks);
		  }else if(FalseLights.isEmpty()==false && FalseNonHueGo2Lights.isEmpty()==true){
			  Status="FAIL";
			  Results="Brightness level of Hue Go 2 is Incorrect";
			  if(nonReachableLights.isEmpty()==true){
				  Remarks ="Old Brightness level of Hue Go 2 :"+OldHueGo2BrightnessLevel
						  +". New Brightness level of Hue Go 2: "+NewHueGo2BrightnessLevel;
			  }else{
				  Remarks="Old Brightness level of Hue Go 2 :"+OldHueGo2BrightnessLevel
				  +". New Brightness level of Hue Go 2: "+NewHueGo2BrightnessLevel
				  +"Non Reachable Lights: "+nonReachableLights.toString();
			  }
			  sendTohtml=createHTMLReport(Status,Results,Remarks);
			  
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
							"Values('"+utcdate+"','9','1','Hue Go 2 Dimmed by 40% of original Brightness level','"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
					/*System.out.println("Putting data into excel-Inside IF");
			    	
			    	cdsr.ReportTurnONAllLights("PASS");*/
			    }else {
					String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
							"Values('"+utcdate+"','9','0','Hue Go 2 Dimmed by 40% of original Brightness level','"+Remarks+"','"+BridgeAPIVersion+"')";
					myStmt.executeUpdate(sql);
			   /* 	System.out.println("Putting data into excel-Inside ELSE");
			    	cdsr.ReportTurnONAllLights("FAIL");*/
			    }

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		  
		 /*   if(Status=="PASS")
		    {
		    	System.out.println("Putting data into excel-Inside IF");
		    	cdsr.ReportDimHueGo2("PASS");
		    }else if(Status=="FAIL"){
		    	System.out.println("Putting data into excel-Inside ELSe");
		    	cdsr.ReportDimHueGo2("FAIL");
		    }*/
		  
		return sendTohtml;
		
	}
	
	public String createHTMLReport(String htmlStatus,String htmlResults,String htmlRemarks){
	    System.out.println("htmlResults: " + htmlResults + " htmlStatus: " + htmlStatus + " htmlRemarks :" + htmlRemarks);
	    String htmlString1 = 
	      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n9</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nDim Hue Go 2</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nHue Go 2 Should be Dimmed by 60% of current Brightness Level.</td>\n"
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
	      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		return htmlString1;
		
	}
}