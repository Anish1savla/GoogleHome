import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class htmlReport
{
  String reportHeader = null;
  String finalHTMLReportString = "";
  String reportNotes = "</table>\n</center>\n<h3>\nTest Cases Not Executed-\n";
  
  public void createHTMLReport(String turnONAll, String turnOFFAll, String changeColorRed, String changeColorGreen, 
		  String setBrightness100, String turnONhueColorLamp1, String turnOFFhueColorLamp1,String DimAll, String DimHueGo2, 
		  String SetBrighteness10Percent,String BrightenAllLightsBy10P, String TurnLightStripBlue,String DimAllLightsBy20P,
		  String DimHueColorLamp6By30P,String BrightenWhiteLampBy20P, String TurnONAllLivingRoomLights, String TurnOFFAllLivingRoomLights,
		  String TurnONAmbLivingRoom, String TurnOFFAmbLivingRoom, String TurnLivingRoomOrange)
    throws IOException
  {
	  
	/*Turn ON All Lights*/  
    if (turnONAll == null)
    {
      turnONAll = "Turn ON All Lights";
      reportNotes = (reportNotes + "<h5>" + turnONAll + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += turnONAll;
    }
    
    /* Turn OFF All Lights */
    if (turnOFFAll == null)
    {
      turnOFFAll = "Turn OFF All Lights";
      reportNotes = (reportNotes + "<h5>" + turnOFFAll + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += turnOFFAll;
    }
    
    /* Turn Color Red for All Lights */
    if (changeColorRed == null)
    {
      changeColorRed = "Turn All Lights Red";
      reportNotes = (reportNotes + "<h5>" + changeColorRed + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += changeColorRed;
    }
    
    /* Turn Color Green for All Lights */
    if (changeColorGreen == null)
    {
      changeColorGreen = "Turn All Lights Green";
      reportNotes = (reportNotes + "<h5>" + changeColorGreen + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += changeColorGreen;
    }
    
    /* Set all Lights to 100% */
    if (setBrightness100 == null)
    {
      setBrightness100 = "Set Brightness for All Lights to 100%";
      reportNotes = (reportNotes + "<h5>" + setBrightness100 + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += setBrightness100;
    }
    /* Turn ON Hue Color Lamp 1 */
    if (turnONhueColorLamp1 == null)
    {
      turnONhueColorLamp1 = "Turn ON Hue Color Lamp 1";
      reportNotes = (reportNotes + "<h5>" + turnONhueColorLamp1 + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += turnONhueColorLamp1;
    }
    
    /* Turn OFF Hue Color Lamp 1 */
    if (turnOFFhueColorLamp1 == null)
    {
    	
      turnOFFhueColorLamp1 = "Turn OFF Hue Color Lamp 1";
      reportNotes = (reportNotes + "<h5>" + turnOFFhueColorLamp1 + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += turnOFFhueColorLamp1;
    }
    
    /* Dim All Lights */
    if (DimAll == null)
    {
    	
    	DimAll = "Dim All Lights";
      reportNotes = (reportNotes + "<h5>" + DimAll + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += DimAll;
    }
    
    /* Dim Hue Go 2 */
    if (DimHueGo2 == null)
    {
    	
    	DimHueGo2 = "Dim Hue Go 2";
      reportNotes = (reportNotes + "<h5>" + DimHueGo2 + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += DimHueGo2;
    }
    
    /* Set All Lights to 10% */
    if (SetBrighteness10Percent == null)
    {
    	
    	DimHueGo2 = "Set Brightness for All lights to 10%";
      reportNotes = (reportNotes + "<h5>" + SetBrighteness10Percent + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += SetBrighteness10Percent;
    }
    
    /* Brighten All Lights By 10% */
    if (BrightenAllLightsBy10P == null)
    {
    	
    	DimHueGo2 = "Brighten All Lights by 10%";
      reportNotes = (reportNotes + "<h5>" + BrightenAllLightsBy10P + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += BrightenAllLightsBy10P;
    }
    
    /* Turn Hue LightStrip Plus 1 Blue*/
    if (TurnLightStripBlue == null)
    {
    	
    	DimHueGo2 = "Turn Hue LightStrip Plus 1 Blue";
      reportNotes = (reportNotes + "<h5>" + TurnLightStripBlue + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += TurnLightStripBlue;
    }
    
    /* Dim The Lights By 20% */
    if (DimAllLightsBy20P == null)
    {
    	
    	DimHueGo2 = "Dim the Lights By 20%";
      reportNotes = (reportNotes + "<h5>" + DimAllLightsBy20P + "</h5>\n");
    }
    else
    {
    	
      finalHTMLReportString += DimAllLightsBy20P;
    }
    
    /* Dim Hue Coloe Lamp 6 By 30% */
    
    
    if (DimHueColorLamp6By30P == null)
    {
    	
    	DimHueGo2 = "Dim Hue Color Lamp 6 By 30%";
      reportNotes = (reportNotes + "<h5>" + DimHueColorLamp6By30P + "</h5>\n");
    }
    else
    {
       finalHTMLReportString += DimHueColorLamp6By30P;
    }
    
    /* Brighten White Lamp By 20% */
      
    if (BrightenWhiteLampBy20P == null)
    {
      DimHueGo2 = "Brighten White Lamp By 20%";
      reportNotes = (reportNotes + "<h5>" + BrightenWhiteLampBy20P + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += BrightenWhiteLampBy20P;
    }

    
    /* Turn ON All lights in Living Room*/
    if (TurnONAllLivingRoomLights == null)
    {
      DimHueGo2 = "Turn ON All Lights in Living Room";
      reportNotes = (reportNotes + "<h5>" + TurnONAllLivingRoomLights + "</h5>\n");
    }
    else
    {
       finalHTMLReportString += TurnONAllLivingRoomLights;
    }
    
    if (TurnOFFAllLivingRoomLights == null)
    {
    	DimHueGo2 = "Turn OFF All Lights in Living Room";
      reportNotes = (reportNotes + "<h5>" + TurnOFFAllLivingRoomLights + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += TurnOFFAllLivingRoomLights;
    }
    
    /* Turn ON Hue Ambiance Lamp 1 in Living Room*/ 
    if (TurnONAmbLivingRoom == null)
    {
    	DimHueGo2 = "Turn ON Ambiance Lamp in Living Room";
      reportNotes = (reportNotes + "<h5>" + TurnONAmbLivingRoom + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += TurnONAmbLivingRoom;
    }
    
    /*Turn OFF Hue ambiance Lamp 1 in Living Room */
    if (TurnOFFAmbLivingRoom == null)
    {
    	DimHueGo2 = "Turn OFF Ambiance Lamp in Living Room";
      reportNotes = (reportNotes + "<h5>" + TurnOFFAmbLivingRoom + "</h5>\n");
    }
    else
    {
      finalHTMLReportString += TurnOFFAmbLivingRoom;
    }
    
    /*Turn Living Room Lights Orange */
    if (TurnLivingRoomOrange == null)
    {
    	DimHueGo2 = "Turn Living Room lights Orange";
      reportNotes = (reportNotes + "<h5>" + TurnLivingRoomOrange + "</h5>\n");
    }
    else
    {
       finalHTMLReportString += TurnLivingRoomOrange;
    }

    reportHeader = "<!DOCTYPE html>\n<html>\n<head>\n<title>GoogleHome-Hue Daily Report</title>\n</head>\n"
    		+ "<body bgcolor=\"#E6E6FA\">\n<h1>\n<center>\nGoogle Home - Philips Hue Daily Test Report</center>\n</h1>\n"
    		+ "<h2>\n<center>\nLocal Integration</center>\n</h2>\n\n"
    		+"<center>\n<table width=\"80%\" style=\"border:1px solid black;border-collapse:collapse\">\n"
    		+ "<tr>\n<th style=\"border:1px solid black;border-collapse:collapse\">\nTest ID</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nTest Command Name</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nExpected Results</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nActual Results</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nStatus</th>\n"
    		+ "<th style=\"border:1px solid black;border-collapse:collapse\">\nRemarks</th>\n</tr>\n";
    
    System.out.println("Creating Report HTML file");
    
    File ArchiveFolder = new File("C:\\Users\\310235474\\git\\GoogleHome1.0\\Archive\\All Report");
    
    File OldFile = new File("C:\\Users\\310235474\\git\\GoogleHome1.0\\");
    
    File[] oldFiles = OldFile.listFiles();
    
    for (int i = 0; i < oldFiles.length; i++)
    {
    	
    	if(oldFiles[i].getName().contains("html")){
    		System.out.println("File:"+oldFiles[i].getName());
    		oldFiles[i].renameTo(new File(ArchiveFolder + "\\" + oldFiles[i].getName()));
    	}
    }
    
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMDDYY HH-mm-ss");
    String curDate = dateFormat.format(date);
    System.out.println("Date is Set for Report");
    File f = new File("C:\\Users\\310235474\\git\\GoogleHome1.0\\Report " + curDate + ".html");
    f.createNewFile();
    try
    {
      BufferedWriter bf = new BufferedWriter(new FileWriter(f));
      bf.write(reportHeader);
      bf.write(finalHTMLReportString);
      bf.write(reportNotes);
      bf.close();
      
      System.out.println(f.exists());
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
}