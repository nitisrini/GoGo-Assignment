package AmazonTest.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;

public class Common {

	public WebDriver setupDriver(WebDriver driver, String browser, ITestContext context) {

		switch (browser) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("disable-infobars");

			driver = new ChromeDriver(options);
			
			context.setAttribute("browser", browser);
			break;
		case "firefox":
			FirefoxOptions ops = new FirefoxOptions();
			ops.addPreference("dom.webnotifications.enabled", false);
			
			driver = new FirefoxDriver(ops);
			
			context.setAttribute("browser", browser);
			break;
		default:
			break;
		}

		return driver;

	}

}
