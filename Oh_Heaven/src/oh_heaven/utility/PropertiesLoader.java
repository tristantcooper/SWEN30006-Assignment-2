package oh_heaven.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class PropertiesLoader {
	public static final String DEFAULT_DIRECTORY_PATH = "properties/";
    public static Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream( DEFAULT_DIRECTORY_PATH + "runmode.properties")) {

                Properties prop = new Properties();

                // load a properties file
                prop.load(input);

                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void loadProperties(Properties properties) {
     

        int nbStartCards = Integer.parseInt(properties.getProperty("nbStartCards"));
        int round = Integer.parseInt(properties.getProperty("rounds"));

        System.out.print(nbStartCards);
        System.out.print(round);
     
    }


}
