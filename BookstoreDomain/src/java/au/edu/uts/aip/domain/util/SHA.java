package au.edu.uts.aip.domain.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SHA-256 encryption algorithm
 * 
 * Source: https://gist.github.com/avilches/750151
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
public class SHA {

    /**
     * Encrypt a plaintext string.
     * @param data
     * @return
     */
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

    /**
     * Convert bytes to a hexadecimal format.
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
