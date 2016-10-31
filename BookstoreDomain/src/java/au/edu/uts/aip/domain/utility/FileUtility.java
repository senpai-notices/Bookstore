package au.edu.uts.aip.domain.utility;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xml.security.exceptions.Base64DecodingException;

/**
 * Helper class for file-related actions
 */
public class FileUtility {

    /**
     * Copy a file input stream to a location.
     *
     * @author Laprise (http://stackoverflow.com/questions/43157)
     * @param input the file input stream
     * @param path the location where the file will be copied to
     * @throws IOException
     */
    public static void copy(InputStream input, String path) throws IOException {
        try (OutputStream output = new FileOutputStream(path)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static void decode64AndCopy(InputStream input, String path) throws IOException, Base64DecodingException {
        try (OutputStream os = new FileOutputStream(path)){
            Base64.decode(input, os);
        }
    }

    /**
     * @author Adamski (http://stackoverflow.com/questions/1264709/)
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] copyToBuffer(InputStream input) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            return buffer.toByteArray();

        } catch (IOException ex) {
            Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
