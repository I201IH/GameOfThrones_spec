package thrones.game;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * Load property file to get parameters
 */
public class PropertiesLoader {
    //Derived from assignment 1

    public static Properties loadPropertiesFile(String file_path){

        try (InputStream file = new FileInputStream(file_path)) {
            Properties property = new Properties();
            property.load(file);

            return property;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
