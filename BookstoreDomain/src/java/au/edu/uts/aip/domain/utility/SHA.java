package au.edu.uts.aip.domain.utility;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author https://gist.github.com/avilches/750151
 */
public class SHA {
  public static String hash256(String data) {
      try {
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          md.update(data.getBytes());
          return bytesToHex(md.digest());
      } catch (NoSuchAlgorithmException ex) {
          Logger.getLogger(SHA.class.getName()).log(Level.SEVERE, null, ex);
          return data;
      }
  }
  public static String bytesToHex(byte[] bytes) {
    StringBuffer result = new StringBuffer();
    for (byte byt : bytes) {
      result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
    }
    return result.toString();
  }
}