package ProcurementStages;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import BaseTest.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Listeners(listeners.Listener.class)

public class RequesterLogin extends BaseTest{

	
	@Test(description = "Login as a requester", priority = 1)
	@Feature("Requester: Requester Login")
	@Severity(SeverityLevel.NORMAL)
	public void login() {
		
		test = extent.createTest("Requester: Requester Login");
		//WebSite redirecting...
		driver.get("https://technosofteng.in/staging_eengineering/login");
		
		// Internal selection...
		driver.findElement(By.xpath("//a[text()='Internal']")).click();

		// Login as the requester (Chetan_Bhootra's id).
		driver.findElement(By.xpath("(//input[@placeholder='Email Address'])[2]")).sendKeys("cbhootra@technosofteng.com");
		driver.findElement(By.xpath("(//input[@placeholder='Password'])[2]")).sendKeys("welcome");
		driver.findElement(By.xpath("(//button[text()='Login'])[2]")).click();
	}
	
	
	@Test(description = "New procurement submitting as an requester", priority = 2)
	@Feature("Requester: New procurement submit")
	@Severity(SeverityLevel.CRITICAL)
	public void newProcurement() {
		
		test = extent.createTest("Requester: New procurement submit");
		// Procurement tab selection...
		driver.findElement(By.xpath("//a/span[contains(text(), 'Procurement')]")).click();

		// New procurement Tab selection...
		driver.findElement(By.xpath("//a[contains(text(), 'New Procurement')]")).click();

		// New procurement form filling...
		driver.findElement(By.xpath("//div[text()='Select Category']")).click(); // Category
		driver.findElement(By.xpath("//span[text()='IT Assets']")).click();
		driver.findElement(By.id("pur_order_name")).sendKeys("Automation 1"); // Title
		driver.findElement(By.xpath("//div[text()='Select Department']")).click(); // Department
		driver.findElement(By.xpath("//a/span[contains(text(),'IT Enabled Services')]")).click();
		driver.findElement(By.xpath("//textarea[@id='vendornote']")).sendKeys("Automation note from requester"); // Note
		driver.findElement(By.xpath("//textarea[@id='description_1']")).sendKeys("Automation description from requester"); // Description
		driver.findElement(By.id("price_1")).clear(); // Price
		driver.findElement(By.id("price_1")).sendKeys("100");
		String price = driver.findElement(By.id("price_1")).getAttribute("value");
		driver.findElement(By.id("quantity_1")).clear(); // Quantity
		driver.findElement(By.id("quantity_1")).sendKeys("20");
		String quantity = driver.findElement(By.id("quantity_1")).getAttribute("value");
		
		// Converting String to Integer for calculation.
		int Total = Integer.parseInt(price) * Integer.parseInt(quantity); 
		System.out.println("Total amount of the product after calculation is: " +Total);
		WebElement readonly = driver.findElement(By.id("total_1")); // Amount verification
		int Amount = (int) Double.parseDouble(readonly.getAttribute("value"));
		System.out.println("Total amount of the product display in amount field is: " +Amount);
		
		// verifying the Amount is correctly calculated in Amount field.
		assertEquals(Total, Amount);
		WebElement readonly1 = driver.findElement(By.id("total_mn")); // SubTotal
		int subTotalAmount = (int) Double.parseDouble(readonly1.getAttribute("value"));
		
		// verifying the Amount is equal to SubTotal field.
		System.out.println("SubTotal amount of the product display in SubTotal field is: " +subTotalAmount);
		assertEquals(Amount, subTotalAmount);
		driver.findElement(By.id("file_title_1")).sendKeys("Automate Doc Title"); // Document Title
		driver.findElement(By.xpath("//input[@id='upload_file_1']")).sendKeys("C://Users//rkolhe//Downloads//SampleFile.pdf");  // File Upload
		driver.findElement(By.xpath("(//button[text()='Save'])[1]")).click();
	}

	
	@Test(description = "Ending the request process from requester side", priority = 3)
	@Feature("Requester: Requester Logout")
	@Severity(SeverityLevel.NORMAL)
	public void flush() {
		
		test = extent.createTest("Requester: Requester Logout");
		//Element to be found on the screen before next step...
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Procurement Details')]"))));
		
		//Log out from the WebSite...
		driver.findElement(By.xpath("//li[@class='icon header-user-profile']")).click();
		driver.findElement(By.xpath("(//a[text()='Logout'])[2]")).click();
	}
}
