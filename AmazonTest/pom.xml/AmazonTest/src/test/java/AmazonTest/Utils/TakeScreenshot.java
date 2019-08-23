package AmazonTest.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TakeScreenshot {
	
	private WebDriver driver;
	
	public void takeScreenshot(ITestResult result) {
		ITestContext context = result.getTestContext();
		driver = (WebDriver) context.getAttribute("webdriver");
		
		String dir = System.getProperty("user.dir");
		
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile //method
			Path scrnPath = Paths.get(dir, "datas", result.getName()+".png");
			FileUtils.copyFile(src, new File(scrnPath.toString()));
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
