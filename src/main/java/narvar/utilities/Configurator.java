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

public class Configurator {

    File propertiesFile;
    File xmlFile;
    Configurations configs = new Configurations();


    public void setPropetiesFile(String propertiesFileName) {
        this.propertiesFile = new File(propertiesFileName);
    }

    public void setXMLFile(String xmlFileName) {
        this.xmlFile = new File(xmlFileName);
    }

    public Object getPropertyFromPropertiesFile(String parameter) {
        try {
            Configuration config = configs.properties(this.propertiesFile);
            return config.getProperty(parameter);
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Object getPropertyFromXMLFile(String parameter) {
        try {
            XMLConfiguration config = configs.xml(this.xmlFile);
            return config.getProperty(parameter);
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
