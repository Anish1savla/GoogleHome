import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

public class HueBridgeConnection
{
  private PHHueSDK phHueSDK;
  private ConnectCallback connectCallback;
  public String lastknownUserName = "lastusername";
  public String lastKnownIPAddress = "lastIPAddress";
  
  public void connectToBridgeWithIp(String ipAddress,String userNameStored, ConnectCallback connectCallback)
  {
    phHueSDK = PHHueSDK.create();
    phHueSDK.getNotificationManager().registerSDKListener(listener);
    
    this.connectCallback = connectCallback;
    
    //System.out.println("Inside Hue bridge connection and IP address is:"+ipAddress);
    //System.out.println("Inside Hue Bridge connection and username is :"+userNameStored);
    PHAccessPoint accessPoint = new PHAccessPoint();
    
    accessPoint.setIpAddress(ipAddress);
   accessPoint.setUsername(userNameStored);
    //System.out.println("Access Point is :"+accessPoint.getUsername());
    phHueSDK.connect(accessPoint);
	//System.out.println("Before push link");
  }
  
  private PHSDKListener listener = new PHSDKListener()
  {
    public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {}
    
    public void onAuthenticationRequired(PHAccessPoint accessPoint)
    {
      phHueSDK.startPushlinkAuthentication(accessPoint);
      System.out.println("Press pushlink Button");
    }
    
    public void onBridgeConnected(PHBridge bridge, String username)
    {
      System.out.println("Bridge Connected");
      //HueBridgeConnection.this.
      phHueSDK.setSelectedBridge(bridge);
      phHueSDK.enableHeartbeat(bridge, 10000L);
      
      String lastConnectedUsername = bridge.getResourceCache().getBridgeConfiguration().getUsername();
      String lastConnectedipAddress = bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
      
      File PropertyFile = new File("C:\\Users\\310235474\\git\\GoogleHome1.0\\BridgeProperty.txt");
      try
      {
        PropertyFile.createNewFile();
        BufferedWriter bf = new BufferedWriter(new FileWriter(PropertyFile));
        bf.write(lastConnectedUsername);
        bf.append("\r\n");
        bf.write(lastConnectedipAddress);
        
        bf.close();
        System.out.println(PropertyFile.exists());
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
      }
      System.out.println("calling connect call back");
      
      try {
		connectCallback.onConnected(bridge);
	} catch (EncryptedDocumentException | InvalidFormatException | ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {}
    
    public void onConnectionLost(PHAccessPoint arg0) {}
    
    public void onConnectionResumed(PHBridge arg0) {}
    
    public void onError(int code, String message) {}
    
    public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {}
  };

}