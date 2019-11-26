package com.qa.testQA;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LandingPageTest {

	 public static WebDriver driver;
	
	Logger log = Logger.getLogger(LandingPageTest.class);
	
	public static ExtentReports extent;
	 public static ExtentTest extentTest;
	
	
	@BeforeTest
      public void setExtent()
      {
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/Extent.html", true);
		
		extent.addSystemInfo("Host Name","srijaytechLAP-1");
		extent.addSystemInfo("User Name","subham");
		extent.addSystemInfo("OS","Windows 7");
		extent.addSystemInfo("Java version","1.8.0_181");

      }
	
	
	@AfterTest
			   public void endReport()
			 {
				extent.flush();
				extent.close();
			 }
	
	
	    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException
	    {
	    	String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	    	
	    	TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			// after execution, you could see a folder "FailedTestsScreenshots"
			// under src folder
			String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
					+ ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
			
			return destination;
	    }
	    
	    
	@BeforeMethod
	public void setup(){
		
		log.info("****************************** Starting test cases execution  *****************************************");

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\subham\\Downloads\\chromedriver_win32\\chromedriver.exe");	
		driver = new ChromeDriver(); 
		log.info("launching chrome broswer");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.kiposcollective.com/#/");
		log.info("entering application URL");
		//log.warn("Hey this just a warning message");
		//log.fatal("hey this is just fatal error message");
		//log.debug("this is debug message");
	}
	
	
	@Test(priority=1)
	public void kiposSignInTest() throws InterruptedException{
		log.info("****************************** starting test case *****************************************");
		log.info("****************************** Kipos Sign up Test *****************************************");
		
		   extentTest= extent.startTest("kiposSignIn");
				   
		boolean b= driver.findElement(By.xpath("//div[@class='col-lg-4 col-xl-4 col-md-5 col-sm-4 col-8 sign-right order-aline-2 ng-scope']//span[@class='btn btn-login'][contains(text(),'Signup123')]")).isDisplayed();
		
		   System.out.println(b);
		   driver.findElement(By.xpath("//div[@class='col-lg-4 col-xl-4 col-md-5 col-sm-4 col-8 sign-right order-aline-2 ng-scope']//span[@class='btn btn-login'][contains(text(),'Signup123')]")).click();
		     Thread.sleep(2000);
		
			log.info("****************************** ending test case *****************************************");
			log.info("****************************** Kipos Sign up Test *****************************************");

	}
	
	@Test(priority=2)
	public void kiposLogoTest() throws InterruptedException{
			log.info("****************************** starting test case *****************************************");
			log.info("****************************** Kipos login test *****************************************");
	         
			 extentTest= extent.startTest("kiposLoginTest");
			 
			boolean b = driver.findElement(By.xpath("//div[@class='col-lg-4 col-xl-4 col-md-5 col-sm-4 col-8 sign-right order-aline-2 ng-scope']//span[@class='btn btn-sign'][contains(text(),'Login')]")).isDisplayed();
			Assert.assertTrue(b);
			driver.findElement(By.xpath("//div[@class='col-lg-4 col-xl-4 col-md-5 col-sm-4 col-8 sign-right order-aline-2 ng-scope']//span[@class='btn btn-sign'][contains(text(),'Login')]")).click();
			   Thread.sleep(2000);
			   
			   
			log.info("****************************** ending test case *****************************************");
			log.info("****************************** Kipos login test *****************************************");

	}
	
	

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException{
		
		
		if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.log(LogStatus.FAIL, "Test case is failed"+result.getName());
			
			extentTest.log(LogStatus.FAIL, "Test case is failed"+result.getThrowable());
			
			String screenshotPath= LandingPageTest.getScreenshot(driver, result.getName());
			
			 extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));

		}
		
		else if(result.getStatus()==ITestResult.SKIP)
		{
			extentTest.log(LogStatus.SKIP, "Test case skipped is"+result.getName());
		}
		
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(LogStatus.PASS,"Test case passed is"+result.getName());
		}
		
		extent.endTest(extentTest);
		
		driver.quit();
		log.info("****************************** Browser is closed *****************************************");

		
	}
	

}