package AmazonTest.Tests;

import org.testng.annotations.Test;

import AmazonTest.PageObjects.BookSearch;
import AmazonTest.Utils.Common;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

public class BookSearchTest {

	private WebDriver driver;

	@BeforeTest
	@Parameters({ "url", "browser" })
	public void beforeTest(String url, String browser, ITestContext context) {
		
		Common cmn = new Common();
		
		driver = cmn.setupDriver(driver, browser, context);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		context.setAttribute("webdriver", driver);

		driver.get(url);

	}

	@Test
	@Parameters({ "category", "search", "ignoreCategory", "prodNo" })
	public void search(String category, String searchValue, String ignoreCategory, int prodNo, ITestContext context) throws IOException, InterruptedException {

		AmazonTest.PageObjects.BookSearch books = new BookSearch(driver);
		
		books.selectCategory(category, searchValue, context); //To select Books category
		books.selectProductFromList(prodNo); // To select nth result
		
		List<Map<String, String>> pDetails = books.productDetails(ignoreCategory);
		
		//books.printDetails(pDetails);
		books.writeDetails(pDetails);

	}

	@AfterTest
	public void afterTest() {

		driver.quit();

	}

}
