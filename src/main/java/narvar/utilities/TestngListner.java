package narvar.utilities;

import narvar.automation.AbstarctBaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.logging.Level;

public class TestngListner extends AbstarctBaseTest implements ITestListener {

    public void onTestStart(ITestResult iTestResult) {
        Logtool.log(Level.INFO, "I am in onTestStart method " +  getTestMethodName(iTestResult) + " start");
    }

    public void onTestSuccess(ITestResult iTestResult) {
        Logtool.log(Level.INFO, "I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
    }

    public void onTestFailure(ITestResult iTestResult) {
        Logtool.log(Level.INFO, "I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed");
    }

    public void onTestSkipped(ITestResult iTestResult) {
        Logtool.log(Level.INFO, "I am in onTestSkipped method " +  getTestMethodName(iTestResult) + " skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Logtool.log(Level.INFO, "I am in onTestFailedButWithinSuccessPercentage method " +
                getTestMethodName(iTestResult) + " start");
    }

    public void onStart(ITestContext iTestContext) {
        Logtool.log(Level.INFO, "I am in onStart method " +  iTestContext.getName());
    }

    public void onFinish(ITestContext iTestContext) {
        Logtool.log(Level.INFO, "I am in onFinish method " +  iTestContext.getName());
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
}
