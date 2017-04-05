import java.text.ParseException;

import javax.mail.MessagingException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.philips.lighting.model.PHBridge;

public abstract interface ConnectCallback
{
  public abstract void onConnected(PHBridge paramPHBridge) throws EncryptedDocumentException, InvalidFormatException, ParseException, MessagingException;
}