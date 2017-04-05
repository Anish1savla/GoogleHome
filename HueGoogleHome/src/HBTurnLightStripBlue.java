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
import org.openqa.selenium.WebDriver;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HBTurnLightStripBlue {

	public float xFloatValue;
	public float yFloatValue;
	public String Status;
	public String Results;
	public String Remarks;
	public String sendToHTML;
	public String HTMLFinalResults;
	public String LightName = "Hue LightStrip Plus 1";
	
	public String TurnLightStripToBlue(PHBridge bridge, WebDriver driver) throws InterruptedException, InvalidFormatException, IOException{
		
		System.out.println("******************** TURN LIGHT STRIP BLUE **********************");
		
		List<String> TrueLights = new ArrayList<String>();
	    List<String> FalseLights = new ArrayList<String>();
	    List<String> nonReachableLights = new ArrayList<String>();

	    TimeUnit.SECONDS.sleep(30);
	    
	    PHBridgeResourcesCache cache = bridge.getResourceCache();
	    List<PHLight> allLights = cache.getAllLights();
	    
	    for (PHLight lights : allLights)
	    {
	    	//System.out.println("Light Name:"+lights.getName());
	    	PHLightState lightState = lights.getLastKnownLightState();
	    	if(lightState.isReachable()==false){
	    		nonReachableLights.add(lights.getName());
	    	}else{
	    		
	    		if(lights.getName().equalsIgnoreCase(LightName)==true){
	    			xFloatValue=lightState.getX();
	    			yFloatValue=lightState.getY();
	    			
	    			System.out.println("X Value:"+xFloatValue);
	    			System.out.println("Y Value:"+yFloatValue);
	    			
	    			if(xFloatValue>=0.1532 && yFloatValue<=0.0475){
	    				TrueLights.add(lights.getName());
	    			}else{
	    				FalseLights.add(lights.getName());
	    			}
	    			
	    		}
	    		
	    	}
	    }
		
	    System.out.println("True Lights:"+TrueLights.toString());
	    System.out.println("False Lights:"+FalseLights.toString());
	    if(FalseLights.isEmpty()==true){
	    	Status="PASS";
	    	Results="Hue Light Strip Plus 1 Turned Blue";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks=" ";
	    	}else{
	    		Remarks=nonReachableLights.toString()+" Lights are Unreachable.";
	    	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
	    }else if(FalseLights.isEmpty()==false){
	    	Status="FAIL";
	    	Results="Hue Light Strip Plus 1 Didn't Turned Blue";
	    	if(nonReachableLights.isEmpty()==true){
	    		Remarks="Hue Light Stip Plus 1: X="+xFloatValue+", Y="+yFloatValue+".Expected Values:X=0.1532 and Y =0.0475";
	    	}else{
	    		Remarks="Hue Light Stip Plus 1: X="+xFloatValue+", Y="+yFloatValue+".Expected Values:X=0.1532 and Y =0.0475"
	    				+nonReachableLights.toString()+" Lights are Unreachable.";
	    	}
	    	sendToHTML=createHTMLReport(Status,Results,Remarks);
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
						"Values('"+utcdate+"','12','1','Hue Light Strip Plus 1 Turned Blue','"+Remarks+"','"+BridgeAPIVersion+"')";
				myStmt.executeUpdate(sql);

		    }else {
				String sql = "INSERT INTO IV_US.RESULTS"+"(runDateTime,testCaseId,isPassed,actualResult,failureReason,bridgeVersion)"+
						"Values('"+utcdate+"','12','0','Hue Light Strip Plus 1 Didnt Turned Blue','"+Remarks+"','"+BridgeAPIVersion+"')";
				myStmt.executeUpdate(sql);

		    }

	    }catch (Exception e){
	    	e.printStackTrace();
	    }
	    
	    
	   /* if(Status=="PASS")
	    {
	    	System.out.println("Putting data into excel-Inside IF");
	    	cdsr.ReportTurnLightStrip100P("PASS");
	    }else if(Status=="FAIL"){
	    	System.out.println("Putting data into excel-Inside ELSe");
	    	cdsr.ReportTurnLightStrip100P("FAIL");
	    }*/
	    
		return sendToHTML;
	}
	
	public String createHTMLReport(String htmlStatus,String htmlResults,String htmlRemarks){
		System.out.println("Status:"+htmlStatus+"Results:"+htmlResults+"Remarks:"+htmlRemarks);
		HTMLFinalResults=
			      "<tr>\n<td style=\"border:1px solid black;border-collapse:collapse\">\n12</td>\n"
			    	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nTurn Hue Light Strip Plus 1 Blue</td>\n"
			    	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\nHue Light Strip Plus 1 should turn Blue.</td>\n"
			    	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" +htmlResults + "</td>\n" 
			    	      + "<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlStatus + "</td>\n" 
			    	      +"<td style=\"border:1px solid black;border-collapse:collapse\">\n" + htmlRemarks + "</td>\n" + "</tr>\n";
		
		return HTMLFinalResults;
		
	}

	
}