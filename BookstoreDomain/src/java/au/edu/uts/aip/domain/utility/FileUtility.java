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
    
    /**
     * Get the extension of a file, based on its path.
     * @author Sylvain Leroux (http://stackoverflow.com/questions/3571223/)
     * @param path the path to the file, it can be either relative path or absolute path
     * @return the file extension, or an empty string if the path is a directory, or the file does not have extension part
     */
    public static String getExtension(String path){
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        }
        
        return extension;
    }
}
