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

public class HBDimHueColorLamp6By30P {
	
	public String LightName = "Hue color Lamp 6";
	public String Status;
	public String Results;
	public String Remarks;
	public String sendToHTML;
	public String FinalHTMLString;
	public int NewBrightnessOfHueColorLamp6;
	public int OldBrightnessOfHueColorLamp6;
	
	public String DimHueColorLamp6By30P(PHBridge bridge, WebDriver driver, String utcdate) throws FindFailed, InterruptedException, InvalidFormatException, IOException{
		
		//System.out.println("Inside Class DimHueColorLamp6By30P");
		PHBridgeResourcesCache cache = bridge.getResourceCache();
	    
	    List<PHLight> allLights = cache.getAllLights();
	    
	    HashMap<String,Integer> OldBrightnessValue = new HashMap<String,Integer>();
	    HashMap<String,Integer> NewBrightnessValue = new HashMap<String,Integer>();
	    List<String> nonReachableLights = new ArrayList<String>();
	    
	    
	    
	    for(PHLight lights:allLights){
	    	
	    	
	    	PHLightState lightState= lights.getLastKnownLightState();
	    	
	    	if(lightState.isReachable()==false){
	    		nonReachableLights.add(lights.getName());
	    	}
	    	
	    	OldBrightnessValue.put(lights.getName(), lightState.getBrightness());
	    	
	    }
	    
	    //System.out.println("For Loop for Old Values complete");
	    
	    selDimHueColorLamp6By30P sdcl630p = new selDimHueColorLamp6By30P();
	    sdcl630p.selDimHueColorLamp6By30Percent();
	    
	    TimeUnit.SECONDS.sleep(27);
	    
	    PHBridgeResourcesCache Newcache = bridge.getResourceCache();
	    
	    List<PHLight> NewAllLights = Newcache.getAllLights();
	    
	    for(PHLight Newlights:NewAllLights){
	    	PHLightState NewlightState= Newlights.getLastKnownLightState();
	    	
	    	NewBrightnessValue.put(Newlights.getName(), NewlightState.getBrightness());
	     }
	    
	    //System.out.println("For Loop for New Values complete");
	    
	    HashMap<String,Integer> TrueLights = new HashMap<String,Integer>();
	    HashMap<String,Integer> FalseLights = new HashMap<String,Integer>();
	    HashMap<String,Integer> NonLamp6TrueLights = new HashMap<String,Integer>();
	    HashMap<String,Integer> NonLamp6FalseLights = new HashMap<String,Integer>();
	    
	    //System.out.println("HashMaps Defined");
	    
	    for (Entry<String, Integer> OldKey : OldBrightnessValue.entrySet()){
	    	
	    	//System.out.println("Inside First Validation For Loop");
	    	
	    	for(Entry<String, Integer> NewKey : NewBrightnessValue.entrySet()){
	    		
	    		//System.out.println("Inside Second Validation For Loop");
	    		if(NewKey.getKey().equalsIgnoreCase(OldKey.getKey())==true){
	    			//System.out.println("Inside First If");
	    			
	    			if(NewKey.getKey().equalsIgnoreCase(LightName)==true){
	    				//System.out.println("Inside Second If");
	    				NewBrightnessOfHueColorLamp6=NewKey.getValue();
	    				OldBrightnessOfHueColorLamp6=OldKey.getValue();
	    				int NewlyCalculatedBrightness=OldKey.getValue()-((OldKey.getValue()*30)/100);
	    				
	    				System.out.println("New Calculated Brightness:"+NewlyCalculatedBrightness);
	    				System.out.println("New Brightness:"+NewKey.getValue());
	    				
	    				if(NewKey.getValue()==NewlyCalculatedBrightness){
	    					TrueLights.put(NewKey.getKey(), NewKey.getValue());
	    				}else{
	    					FalseLights.put(NewKey.getKey(), NewKey.getValue());
	    				}
	    				
	    			}else{
	    				if((NewKey.getValue().equals(OldKey.getValue())==true) && (NewKey.getKey().equalsIgnoreCase(LightName)==false)){
	    					NonLamp6TrueLights.put(NewKey.getKey(), NewKey.getValue());
	    				}else{
	    					NonLamp6FalseLights.put(NewKey.getKey(),NewKey.getValue());
	    				}
	    			}
	    		}
	       	}
	     }
	    
	    
	    if(FalseLights.isEmpty()==true && NonLamp6FalseLights.isEmpty()==true){
	    	Status="PASS";
	    	Results="Hue Color Lamp 6 is Dimmed By 30%.";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks=" ";
	      	}else{
	      		Remarks="Non Reachable Lights:"+nonReachableLights.toString();
	      	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
	    }else if(FalseLights.isEmpty()==true && NonLamp6FalseLights.isEmpty()==false){
	    	Status="FAIL";
	    	Results="Hue Color Lamp 6 is Dimmed By 30%. However some other lights also changed Brightness Level.";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks="Brightness changed for:"+NonLamp6FalseLights.toString()+" Lights.";
	      	}else{
	      		Remarks="Brightness changed for:"+NonLamp6FalseLights.toString()+" Lights."
	      		+"Non Reachable Lights:"+nonReachableLights.toString();
	      	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
	    }else if(FalseLights.isEmpty()==false && NonLamp6FalseLights.isEmpty()==true){
	    	Status="FAIL";
	    	Results="Hue color Lamp 6 Brightness is not Dimmed by 30%";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks="Old Brightness Value of Hue color Lamp 6:"+OldBrightnessOfHueColorLamp6
	    				+". New Brightness Value of Hue color Lamp 6:"+NewBrightnessOfHueColorLamp6;
	      	}else{
	      		Remarks="Old Brightness Value of Hue color Lamp 6:"+OldBrightnessOfHueColorLamp6
	    				+". New Brightness Value of Hue color Lamp 6:"+NewBrightnessOfHueColorLamp6
	      		+"Non Reachable Lights:"+nonReachableLights.toString();
	      	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
	    }else if(FalseLights.isEmpty()==false && NonLamp6FalseLights.isEmpty()==false){
	    	Status="FAIL";
	    	Results="Hue color Lamp 6 Brightness is not Dimmed by 30%. Also Brightness level of other lights changed.";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks="Old Brightness Value:"+OldBrightnessValue.toString()+". New Brightness Value:"+NewBrightnessValue.toString();
	      	}else{
	      		Remarks="Old Brightness Value:"+OldBrightnessValue.toString()+". New Brightness Value:"+NewBrightnessValue.toString()
	      		+"Non Reachable Lights:"+nonReachableLights.toString();
	      	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
	    }
	    
	  //  CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
	    
	    try{
	    	 String BridgeAPIVersion = bridge.getResourceCache().getBridgeConfiguration().getAPIVersion();
	    	 String SoftwareVersion = bridge.getResourceCache().getBridgeConfiguration().getSoftwareVersion(); 

/*	    	TimeZone timeZone = TimeZone.getTimeZone("UTC");
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
						"Values('"+utcdate+"','14','1','Hue Color Lamp 6 Dimmed by 30%','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
				myStmt.executeUpdate(sql);
		    }else {
				String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
						"Values('"+utcdate+"','14','0','Hue Color Lamp 6 Dimmed by 30%','"+Remarks+"','"+BridgeAPIVersion+"' ,'"+SoftwareVersion+"')";
				myStmt.executeUpdate(sql);
		    }

	    }catch (Exception e){
	    	e.printStackTrace();
	    }
	    
/*	    if(Status=="PASS")
	    {
	    	System.out.println("Putting data into excel-Inside IF");
	    	cdsr.ReportDimHueColorLamp6By30P("PASS");
	    }else if(Status=="FAIL"){
	    	System.out.println("Putting data into excel-Inside ELSe");
	    	cdsr.ReportDimHueColorLamp6By30P("FAIL");
	    }*/
	    
		return sendToHTML;
		
	}

	
	public String createHTMLReport(String htmlStatus, String htmlResults, String htmlRemarks){
		
		System.out.println("Status:"+htmlStatus+"Results:"+htmlResults+"Remarks:"+htmlRemarks);
		
		FinalHTMLString="<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n14</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n Dim Hue Color Lamp 6 By 30%</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n Hue color Lamp 6 should be Dimmed By 30%.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		
		return FinalHTMLString;
	}
}
