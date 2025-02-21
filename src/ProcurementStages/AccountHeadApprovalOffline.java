package ProcurementStages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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

public class AccountHeadApprovalOffline extends BaseTest {

	@Test(description = "Login as an Account Head", priority = 1)
	@Feature("Account Head: Account Head Login")
	@Severity(SeverityLevel.NORMAL)
	public void login() throws Exception {
		
		test = extent.createTest("Account Head: Account Head Login");
		//WebSite redirecting...
		driver.get("https://technosofteng.in/staging_eengineering/login");
		
		// Internal selection...
		driver.findElement(By.xpath("//a[text()='Internal']")).click();

		// Login as the HOD (Account head's id).
		driver.findElement(By.xpath("(//input[@placeholder='Email Address'])[2]")).sendKeys("accounthead@technosofteng.com");
		driver.findElement(By.xpath("(//input[@placeholder='Password'])[2]")).sendKeys("welcome");
		driver.findElement(By.xpath("(//button[text()='Login'])[2]")).click();
	}
	
	@Test(description = "Opening newly approved request from HOD", priority = 2)
	@Feature("Account Head: Notification selection")
	@Severity(SeverityLevel.NORMAL)
	public void requestNotification() throws Exception {
		
		test = extent.createTest("Account Head: Notification selection");
		//Opening notification panel...
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//a[@data-toggle='dropdown'])[3]")).click();  //Notification icon click
		
		//Selecting the new procurement order approved by HOD...
		Thread.sleep(2000);
		WebElement notification = driver.findElement(By.xpath("(//span[@class='notification-title'])[1]"));  //Latest Notification
		String note = notification.getText();
		if(note.equalsIgnoreCase("Dharmesh Gohil - Procurement order status updated by Department Head")) {
			notification.click();
		}
		else
		{
			System.out.println("No procurement is approved from HOD...");
		}
	}
	
	@Test(description = "Updating the offline vendor details", priority = 3)
	@Feature("Account Head: Offline Vendor's details")
	@Severity(SeverityLevel.CRITICAL)
	public void vendorDetails() throws Exception {
		
		test = extent.createTest("Account Head: Offline Vendor's details");
		//Updating vendor's details...
		driver.findElement(By.xpath("//input[@name='vendor_name']")).click();  //Vendor name
		WebElement options = driver.findElement(By.cssSelector("datalist#vendorList option:nth-child(6)"));
		String optionValue = options.getAttribute("value");
		driver.findElement(By.xpath("//input[@name='vendor_name']")).sendKeys(optionValue);  //Inputting the value 
		Robot r = new Robot();  //Keys action
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		String vendorName = driver.findElement(By.xpath("//input[@name='vendor_name']")).getAttribute("value");  //Vendor name capture
		driver.findElement(By.xpath("//input[@id='quote_ref_no']")).sendKeys("AutoRef number 1");  //Quote reference number	
		System.out.println("Vendore Name is: "+ vendorName);
		driver.findElement(By.xpath("//input[@id='file_title_1']")).sendKeys("Automate document title");  //Document Title
		driver.findElement(By.xpath("//input[@id='upload_file_1']")).sendKeys("C://Users//rkolhe//Downloads//SampleFile.pdf");  //Document upload
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'Update')]")).click();  //Updating data
	}
	
	@Test(description = "Approving the updated request by Account Head", priority = 4)
	@Feature("Account Head: Procurement Approval by Account Head")
	@Severity(SeverityLevel.CRITICAL)
	public void requestApproving() throws Exception {
		
		test = extent.createTest("Account Head: Procurement Approval by Account Head");
		Thread.sleep(3000);
		//Approving the request from Account Head...
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div[text()='Nothing selected'])[1]")).click();  //Status box
		driver.findElement(By.xpath("//span[text()='Approved']")).click();  //Status update
		driver.findElement(By.xpath("//textarea[@name='acc_comments']")).sendKeys("Comment from automation Account Head");  //Comment box
		
		//Random number generator code...
		int n = 15;
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";  //choose a Character random from this String
			StringBuilder sb = new StringBuilder(n);  // create StringBuffer size of AlphaNumericString
			for (int i = 0; i < n; i++) {
					//generate a random number between
					//0 to AlphaNumericString variable length
					int index = (int) (AlphaNumericString.length() * Math.random());
					sb.append(AlphaNumericString.charAt(index));  //add Character one by one in end of sb
				}
			String alnum = sb.toString();
		driver.findElement(By.xpath("//input[@id='order_number']")).sendKeys(alnum);  //Random number insert
		driver.findElement(By.xpath("//button[@id='save_account_status']")).click();
		driver.findElement(By.xpath("//button[text()='Yes']")).click();
		Thread.sleep(2000);
		WebElement alert = driver.findElement(By.xpath("//span[@class='alert-title']"));  //Successful submitted alert.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(alert));
		String alertMessage = alert.getText();
		System.out.println(alertMessage);
		wait.until(ExpectedConditions.invisibilityOf(alert));
	}
	
	@Test(description = "Ending the approval process from Account Head side", priority = 5)
	@Feature("Account Head: Account Head Logout")
	@Severity(SeverityLevel.NORMAL)
	public void flush() {
		
		test = extent.createTest("Account Head: Account Head Logout");
		//Waiting till the request update by HOD update in logs...
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//b[contains(text(),'(Account Head)')]"))));
		
		//Log out from the WebSite...
		driver.findElement(By.xpath("//li[@class='icon header-user-profile']")).click();
		driver.findElement(By.xpath("(//a[text()='Logout'])[2]")).click();
	}
}
