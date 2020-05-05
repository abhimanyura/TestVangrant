package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author abhimanyu
 */
public class PropertyFileUtils {
	

    //Constants
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PROPERTY_FOLDER_LOCATION = USER_DIR + "/src/main/resources/";


    /**
     * This method fetches the value from Property file
     * @param fileName-Name         of the propertyFile
     * @param propertyName-property name
     * @return-Property value in String Format
     */
    public String getPropertyValue(String fileName, String propertyName) {
        String value = "";
        if (!fileName.contains(".properties")) {
            fileName = fileName + ".properties";
        }
        try (InputStream input = new FileInputStream(PROPERTY_FOLDER_LOCATION + fileName))
        {
            Properties prop = new Properties();
            prop.load(input);
            value = prop.getProperty(propertyName);
        } catch (IOException ex) {
          System.out.println("The" + fileName + "is not present in the " + PROPERTY_FOLDER_LOCATION);
        }
        return value;
    }
    
    public  Map<String, String> getPropertyKeyValuePair(String fileName)
    {
        Properties prop = null;
        Map<String, String> connectionProperty = new HashMap<>();
        try (InputStream input = new FileInputStream(PROPERTY_FOLDER_LOCATION + fileName + ".properties")) {
            prop = new Properties();
            prop.load(input);
            for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements(); ) 
            {
                String name = (String) e.nextElement();         
                connectionProperty.put(name, prop.getProperty(name));
            }
        } catch (Exception e) {
        }
        return connectionProperty;
    }

   public static void main(String []args)
   {
	   PropertyFileUtils propertyFileUtils=new PropertyFileUtils();
	   System.out.println(propertyFileUtils.getPropertyKeyValuePair("config"));

   }

}