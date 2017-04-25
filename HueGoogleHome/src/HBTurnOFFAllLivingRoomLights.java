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

public class HBTurnOFFAllLivingRoomLights {

	public String sendToHTML;
	public String FinalHTMLString;
	public Boolean TurnONIndicator;
	public Boolean OldTurnONIndicator;
	public String Status;
	public String Result;
	public String Remarks;
	public int OldCounter;
	public int OldCounter1;
	public int NewCounter;
	public int NewCounter1;
	
	public String TurnOFFAllLivingRoomLights(PHBridge bridge, WebDriver driver, String utcdate) throws InterruptedException, FindFailed, InvalidFormatException, IOException{
		
	HashMap<String,Integer> LivingRoomLights = new HashMap<String,Integer>();
	HashMap<String,Boolean> OldAllLightState = new HashMap<String,Boolean>();
	HashMap<String,Boolean> NewAllLightState = new HashMap<String,Boolean>();
	HashMap<String,Boolean> NewNonLivingRoomLightState = new HashMap<String,Boolean>();
	HashMap<String,Boolean> OldNonLivingRoomLightState = new HashMap<String,Boolean>();
	HashMap<String,Boolean> TrueLights = new HashMap<String,Boolean>();
	HashMap<String,Boolean> FalseLights = new HashMap<String,Boolean>();
	HashMap<String,Boolean> FalseNonLivingRoomLights = new HashMap<String,Boolean>();
	HashMap<String,Boolean> TrueNonLivingRoomLights = new HashMap<String,Boolean>();
	

	
	
		
	LivingRoomLights.put("Hue ambiance lamp 1",1);
	LivingRoomLights.put("Hue color lamp 1",2);
	LivingRoomLights.put("Hue Color lamp 2",3);
	LivingRoomLights.put("Hue Color lamp 3",4);
	LivingRoomLights.put("Hue Go 2",5);
	
    List<String> nonReachableLights = new ArrayList<String>();
    
    
    //TimeUnit.SECONDS.sleep(20);
    PHBridgeResourcesCache cache = bridge.getResourceCache();
    
    
    List<PHLight> allOldLights = cache.getAllLights();
    
    for(PHLight OldLights:allOldLights){
    	   	
    	PHLightState lightState = OldLights.getLastKnownLightState();
    	
    	if(lightState.isReachable()==false){
    		nonReachableLights.add(OldLights.getName());
    	}
    	
    	OldTurnONIndicator=lightState.isOn();
    	
    	OldAllLightState.put(OldLights.getName(), OldTurnONIndicator);
    	
    }
    
    for(Entry<String, Boolean> OldKey : OldAllLightState.entrySet()){
		for (Entry<String, Integer> MapKey : LivingRoomLights.entrySet()){
			
			
			if(!MapKey.getKey().equalsIgnoreCase(OldKey.getKey())){
				OldCounter++;
			}else{
				OldCounter=0;
				break;
			}
	   	}
		if(OldCounter !=0){
			//System.out.println("Old Counter:"+OldCounter);
		OldNonLivingRoomLightState.put(OldKey.getKey(), OldKey.getValue());
		}
	}
    
    
    selTurnOFFAllLivingRoomLights stonlrl = new selTurnOFFAllLivingRoomLights();
    stonlrl.TurnOFFAllLivingRoomLights(bridge, driver);
    
    TimeUnit.SECONDS.sleep(27);
    
    PHBridgeResourcesCache Newcache = bridge.getResourceCache();
    List<PHLight> allLights = Newcache.getAllLights();
    
    
    
    for(PHLight Lights:allLights){
    	    	
    	PHLightState lightState = Lights.getLastKnownLightState();
    	
    	if(lightState.isReachable()==false){
    		nonReachableLights.add(Lights.getName());
    	}
    	
    	NewAllLightState.put(Lights.getName(), lightState.isOn());
    	
    	for (Entry<String, Integer> MapKey : LivingRoomLights.entrySet()){
    		
    		if(Lights.getName().equalsIgnoreCase(MapKey.getKey())==true){
    			TurnONIndicator = lightState.isOn();
    			
    			if(TurnONIndicator==false){
    				TrueLights.put(MapKey.getKey(),lightState.isOn());
    			}else{
    				FalseLights.put(MapKey.getKey(), lightState.isOn());
    			}
    		}
    		
    	}
    	
    }
    
    
    	for(Entry<String, Boolean> NewKey : NewAllLightState.entrySet()){
    		for (Entry<String, Integer> MapKey : LivingRoomLights.entrySet()){
    			//System.out.println("Map Key:"+MapKey.getKey());
    			//System.out.println("New Key:"+NewKey.getKey());
    			if(!MapKey.getKey().equalsIgnoreCase(NewKey.getKey())){
    				//System.out.println("Inside IF");
    				NewCounter++;
    			}else {
    				NewCounter=0;
    				break;
    			}
    	   	}
    		
    		//System.out.println("New CounteR:"+NewCounter);
    		//System.out.println("New CounteR1:"+NewCounter1);
    		if(NewCounter !=0){
    			//System.out.println("New CounteR:"+NewCounter);
    			NewNonLivingRoomLightState.put(NewKey.getKey(), NewKey.getValue());
    		}
    	}
    	
    	for(Entry<String,Boolean> NewNonKey : NewNonLivingRoomLightState.entrySet()){
    		for(Entry<String,Boolean> OldNonKey : OldNonLivingRoomLightState.entrySet()){
    			
    			if((OldNonKey.getKey().equalsIgnoreCase(NewNonKey.getKey())==true) 
    						&& (OldNonKey.getValue()==NewNonKey.getValue())){
    					//System.out.println("Inside IF");
    					TrueNonLivingRoomLights.put(NewNonKey.getKey(), NewNonKey.getValue());
    			}else if ((OldNonKey.getKey().equalsIgnoreCase(NewNonKey.getKey())==true) 
    						&& (OldNonKey.getValue()!=NewNonKey.getValue())) {
    					//System.out.println("Inside Else");
    					FalseNonLivingRoomLights.put(NewNonKey.getKey(), NewNonKey.getValue());
    			}
    		}
    	}
    
    if(FalseLights.isEmpty()==true && FalseNonLivingRoomLights.isEmpty()==true){
    	Status = "PASS";
    	Result = "All Lights in Living Room Turned OFF.";
    	if(nonReachableLights.isEmpty()==true){
    		Remarks=" ";
    	}else{
    		Remarks=nonReachableLights.toString()+" Lights are Non Reachable Lights.";
    	}
    	sendToHTML=createHTMLReport(Status,Result,Remarks);
    }else if(FalseLights.isEmpty()==false && FalseNonLivingRoomLights.isEmpty()==true){
    	Status = "FAIL";
    	Result = "All Lights in Living Room Didn't Turned OFF.";
    	if(nonReachableLights.isEmpty()==true){
    		Remarks=FalseLights.toString()+" Light still ON.";
    	}else{
    		Remarks=FalseLights.toString()+" Light still ON. "+nonReachableLights.toString()+" Lights are Non Reachable Lights.";
    	}
    	sendToHTML=createHTMLReport(Status,Result,Remarks);
    }else if(FalseLights.isEmpty()==true && FalseNonLivingRoomLights.isEmpty()==false){
    	Status = "FAIL";
    	Result = "All Lights in Living Room Turned OFF. However Status of other Lights also changed.";
    	if(nonReachableLights.isEmpty()==true){
    		Remarks="Light Status before providing Command:"+OldAllLightState.toString()+
    				". Light status after providing Command:"+NewAllLightState.toString();
    	}else{
    		Remarks="Light Status before providing Command:"+OldAllLightState.toString()+
    				". Light status after providing Command:"+NewAllLightState.toString()
    				+nonReachableLights.toString()+" Light Non Reachable Lights.";
    	}
    	sendToHTML=createHTMLReport(Status,Result,Remarks);
    }else if(FalseLights.isEmpty()==true && FalseNonLivingRoomLights.isEmpty()==false){
    	Status = "FAIL";
    	Result = "All Lights in Living Room Didn't Turned OFF. However Status of other Lights also changed.";
    	if(nonReachableLights.isEmpty()==true){
    		Remarks="Light Status before providing Command:"+OldAllLightState.toString()+
    				". Light status after providing Command:"+NewAllLightState.toString();
    	}else{
    		Remarks="Light Status before providing Command:"+OldAllLightState.toString()+
    				". Light status after providing Command:"+NewAllLightState.toString()+
    				"Non Reachable Lights:"+nonReachableLights.toString();
    	}
    	sendToHTML=createHTMLReport(Status,Result,Remarks);
    }
    
   // CreateNewDailySummaryReport cdsr = new CreateNewDailySummaryReport();
    
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
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
					"Values('"+utcdate+"','17','1','All Lights Turned OFF in Living Room','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
			
	    }else {
			String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,APIVersion,SWVersion)"+
					"Values('"+utcdate+"','17','0','All Lights Didnt Turned OFF in Living Room','"+Remarks+"','"+BridgeAPIVersion+"','"+SoftwareVersion+"')";
			myStmt.executeUpdate(sql);
	  
	    }

   }catch (Exception e){
   	e.printStackTrace();
   }
    
/*    if(Status=="PASS")
    {
    	System.out.println("Putting data into excel-Inside IF");
    	cdsr.ReportTurnOFFAllLR("PASS");
    }else if(Status=="FAIL"){
    	System.out.println("Putting data into excel-Inside ELSe");
    	cdsr.ReportTurnOFFAllLR("FAIL");
    }*/
		return sendToHTML;
		
	}
	
	
	public String createHTMLReport(String htmlStatus, String htmlResult, String htmlRemarks){
		
		FinalHTMLString= "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n17</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn OFF Lights in Living Room.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nAll Lights Should Turn OFF in Living Room.</td>\n"
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlResult + "</td>\n" 
			      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlStatus+ "</td>\n" 
			      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		
		return FinalHTMLString;
	}
}