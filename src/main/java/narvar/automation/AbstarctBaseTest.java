package narvar.automation;

import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import narvar.utilities.Configurator;
import narvar.utilities.Logtool;
import narvar.utilities.RestClient;
import org.hamcrest.Matchers.*;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


// Commenting the following line as we may not need this right now.
//import io.restassured.module.jsv.JsonSchemaValidator.*;

public class AbstarctBaseTest {

    // Initialize the extent report objects
    ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest extentTest;
    public Configurator configurator;
    public Map<String, RestClient> restClients;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public AbstarctBaseTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String htmlFileName = System.getProperty("user.dir") +"/test-output/automation"+timestamp+".html";
        htmlReporter = new ExtentHtmlReporter(htmlFileName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Configure HTML Report
        htmlReporter.config().setAutoCreateRelativePathMedia(true);
        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        // Read Config File and read the end point and port for a given service
        String currentDirectory = System.getProperty("user.dir");
        String propertiesFile = currentDirectory+"/config/serviceconfiguration.properties";
        configurator = new Configurator(propertiesFile);

        extent.setSystemInfo("Properties File", propertiesFile);
        extent.setSystemInfo("Environemnt", "QA");
        extent.setSystemInfo("user", "Automation-Team");

        restClients = populateRestClients(configurator);
    }

    private Map<String, RestClient> populateRestClients(Configurator configurator) {
        Map<String, RestClient> restclients = new HashMap<String, RestClient>();
        Map<String, String> serviceUrls = configurator.getAllPropertiesMapThatStartsWithAndContains("service","baseurl");
        for (String key: serviceUrls.keySet()) {
            RestClient client = new RestClient(serviceUrls.get(key));
            restclients.put(key, client);
        }

        return restclients;
    }

    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            extentTest.fail(result.getThrowable());
        } else if(result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        } else {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            extentTest.skip(result.getThrowable());
        }
    }

    @BeforeClass
    public void beforeClass() {
        Logtool.log(Level.INFO, "In - BeforeClass");
    }

    @BeforeTest
    public void beforeTest() {
        Logtool.log(Level.INFO, "In - BeforeTest");
    }

    @BeforeMethod
    public void beforeMethod() {
        Logtool.log(Level.INFO, "In - BeforeMethod");
    }

    @AfterClass
    public void afterClass() {
        Logtool.log(Level.INFO, "In - AfterClass");
    }

    @AfterTest
    public void afterTest() {
        Logtool.log(Level.INFO, "In - AfterTest");
        extent.flush();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        Logtool.log(Level.INFO, "In - AfterMethod");
        getResult(result);
    }
}
