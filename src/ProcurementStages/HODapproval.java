package ProcurementStages;

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

public class HODapproval extends BaseTest{
	
	@Test(description = "Login as an HOD", priority = 1)
	@Feature("HOD: HOD Login")
	@Severity(SeverityLevel.NORMAL)
	public void login() throws Exception {
		
		test = extent.createTest("HOD: HOD Login");
		//WebSite redirecting...
		driver.get("https://technosofteng.in/staging_eengineering/login");
		
		// Internal selection...
		driver.findElement(By.xpath("//a[text()='Internal']")).click();

		// Login as the HOD (Dharmesh Goyal's id).
		driver.findElement(By.xpath("(//input[@placeholder='Email Address'])[2]")).sendKeys("dgohil@technosofteng.com");
		driver.findElement(By.xpath("(//input[@placeholder='Password'])[2]")).sendKeys("welcome");
		driver.findElement(By.xpath("(//button[text()='Login'])[2]")).click();
	}
	
	@Test(description = "Opening newly submitted request from requester", priority = 2)
	@Feature("HOD: Notification selection")
	@Severity(SeverityLevel.NORMAL)
	public void requestNotification() throws Exception {
		
		test = extent.createTest("HOD: Notification selection");
		//Opening notification panel...
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//a[@data-toggle='dropdown'])[3]")).click();  //Notification icon click
		
		//Selecting the new procurement order...
		Thread.sleep(2000);
		WebElement notification = driver.findElement(By.xpath("(//span[@class='notification-title'])[1]"));  //Latest Notification
		String note = notification.getText();
		if(note.equalsIgnoreCase("Chetan Bhootra - New Procurement Order Submitted")) {
			notification.click();
		}
		else
		{
			System.out.println("No procurement is submitted...");
		}
	}
	
	@Test(description = "Approving the request by HOD", priority = 3)
	@Feature("HOD: Procurement Approval by HOD")
	@Severity(SeverityLevel.CRITICAL)
	public void requestApprovalHOD() throws Exception {
		
		test = extent.createTest("HOD: Procurement Approval by HOD");
		//Updating Status in status box...
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div[text()='Nothing selected'])[1]")).click();  //Status Box
		driver.findElement(By.xpath("//span[text()='Approved']")).click();  //Updated status
		driver.findElement(By.xpath("//textarea[@name='comments']")).sendKeys("Comment from automation HOD");  //Comment
		driver.findElement(By.xpath("(//input[@name='file_title[]'])[1]")).sendKeys("Title for document automation");  //Document Title
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='upload_file[]']")).sendKeys("C://Users//rkolhe//Downloads//SampleFile.pdf");  //Upload doc
		driver.findElement(By.xpath("//button[@id='save_hod_status']")).click();  //Submitting approval
		Thread.sleep(2000);
		WebElement alert = driver.findElement(By.xpath("//span[@class='alert-title']"));  //Successful submitted alert.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(alert));
		String alertMessage = alert.getText();
		System.out.println(alertMessage);
		wait.until(ExpectedConditions.invisibilityOf(alert));
	}
	
	@Test(description = "Ending the approval process from HOD side", priority = 4)
	@Feature("HOD: HOD Logout")
	@Severity(SeverityLevel.NORMAL)
	public void flush() {
		
		test = extent.createTest("HOD: HOD Logout");
		//Waiting till the request update by HOD update in logs...
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//b[contains(text(),'(HOD)')]"))));
		
		//Log out from the WebSite...
		driver.findElement(By.xpath("//li[@class='icon header-user-profile']")).click();
		driver.findElement(By.xpath("(//a[text()='Logout'])[2]")).click();
	}
}
