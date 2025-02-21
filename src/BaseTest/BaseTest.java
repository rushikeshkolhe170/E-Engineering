package BaseTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseTest {

	public static WebDriver driver;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void browser() {
		
		//Report creation requirements...
		String dateName = new SimpleDateFormat("YYYY-MM-dd_hh.mm.ss").format(new Date());
		htmlReporter = new ExtentSparkReporter(".//extentReport//"+dateName+".html");
		htmlReporter.config().setDocumentTitle("E-Enginnering Automation Report");
		htmlReporter.config().setReportName("Procurement Functional report");
		htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Operating System", "Windows");
		extent.setSystemInfo("Project", "E-Enginnnering");
		extent.setSystemInfo("Tester Name", "Rushikesh Kolhe");
		
		//Driver setting...
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	
	@AfterSuite
	public void closing() {
		
		//Quitting all the process...
		driver.quit();
		//Flushing the extent...
		extent.flush();
	}
}
