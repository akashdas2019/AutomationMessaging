package messaging.functional;

import narvar.automation.AbstarctBaseTest;
import narvar.utilities.Logtool;
import org.testng.Assert;

import org.testng.SkipException;
import org.testng.annotations.Test;
import java.util.logging.Level;

public class SampleTest extends AbstarctBaseTest {
    @Test
    public void test1() {
        extentTest = extent.createTest("test1", "Test1 Description");
        SampleTest t = new SampleTest();
        Logtool.log(Level.INFO, "This test case will pass");
    }

    @Test(expectedExceptions=RuntimeException.class)
    public void test2() {
        extent.createTest("test2", "Test2 Description");
        Logtool.log(Level.INFO, "Failing this test method");
    }

    @Test
    public void test3() {
        extent.createTest("test2", "Test2 Description");
        Logtool.log(Level.INFO, "Skipping this test case.");
        throw new SkipException("Skipping the test3 test method!");
    }

    private int i=0;
    @Test(successPercentage=60, invocationCount=5)
    public void test4() {
        i++;
        System.out.println("test4 test method, invocation count: " + i);
        if (i == 1 || i == 2) {
            System.out.println("test4 failed!");
            Assert.assertEquals(i, 8);
        }
    }
}
