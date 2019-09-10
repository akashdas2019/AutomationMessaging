package narvar.utilities;

import narvar.automation.AbstarctBaseTest;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Configurator {

    private File propertiesFile;
    public Properties properties;

    public Configurator(String propertiesFileName) {
        this.propertiesFile = new File(propertiesFileName);
        properties = new Properties();
        try {
            properties.load(new FileInputStream(this.propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getPropertyFromPropertiesFile(String parameter) {
        return properties.getProperty(parameter);
    }

    public List<String> getAllPropertyNames() {
        List<String> propertyNames = new LinkedList<String>();
        for (Enumeration<?> keys = properties.propertyNames(); keys.hasMoreElements(); ) {
            propertyNames.add((String) keys.nextElement());
        }
        return propertyNames;
    }

    public List<String> getAllPropertyNamesThatStartsWith(String startName) {
        List<String> propertyNames = new LinkedList<String>();
        for (Enumeration<?> keys = properties.propertyNames(); keys.hasMoreElements(); ) {

            if (((String) keys.nextElement()).contains(startName)) {
                propertyNames.add((String) keys.nextElement());
            }
        }
        return propertyNames;
    }

    public Map<String, String> getAllPropertiesMapThatStartsWithAndContains(String startName, String contains) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        for (Enumeration<?> keys = properties.propertyNames(); keys.hasMoreElements(); ) {
            String keyName = (String) keys.nextElement();
            if (keyName.contains(startName) && keyName.contains(contains)) {
                String[] elements = keyName.split("\\.");
                if (elements.length == 2) {
                    String serviceName = keyName.split("\\.")[1];
                    String baseUrl = keyName.split(".")[2];
                    propertyMap.put(serviceName,baseUrl);
                }
            }
        }
        return propertyMap;
    }

}
