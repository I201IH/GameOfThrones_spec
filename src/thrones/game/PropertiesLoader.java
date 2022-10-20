package thrones.game;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
