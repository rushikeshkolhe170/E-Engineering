package listeners;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import BaseTest.BaseTest;
import io.qameta.allure.Allure;

public class Listener extends BaseTest implements ITestListener {
	
	public static File getScreenShotAllure(WebDriver driver, String screenshotname) throws Exception {
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		String dest = "C:\\Users\\rkolhe\\eclipse-workspace\\E-Enginnering\\screenshot\\"+screenshotname+".png";
		File finalDest = new File(dest);
		FileUtils.copyFile(file, finalDest);
		return file;
		}
	
	public static String getScreenShotExtent(WebDriver driver, String screenshotname) throws Exception {
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		String dest = "C:\\Users\\rkolhe\\eclipse-workspace\\E-Enginnering\\screenshot\\"+screenshotname+".png";
		File finalDest = new File(dest);
		FileUtils.copyFile(file, finalDest);
		return dest;
		}
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result.getName()+ " is Started...");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result.getName()+ " is successfully Pass...");
	}

	@Override
	public void onTestFailure(ITestResult result){
		String screenshotname = result.getName();
		// TODO Auto-generated method stub
		System.out.println(result.getName()+ " is Failed...");
		test.log(Status.FAIL, "Test case FAILED "+ result.getName());
		test.log(Status.FAIL, "Test case FAILED "+ result.getThrowable());
		try {
			String screenshot = Listener.getScreenShotExtent(driver, screenshotname);
			test.addScreenCaptureFromPath(screenshot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File ss;
		try {
			ss = getScreenShotAllure(driver, result.getName());
			Allure.addAttachment("Page Screenshot", FileUtils.openInputStream(ss));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result.getName()+ " is Skipped...");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Test is Started...");
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Test is Finish...");
	}

	
}
