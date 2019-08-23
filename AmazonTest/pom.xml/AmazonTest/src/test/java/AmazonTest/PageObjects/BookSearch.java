package AmazonTest.PageObjects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;

public class BookSearch {

	private WebDriver driver;

	@FindBy(id = "searchDropdownBox")
	WebElement categoryDropdown;

	@FindBy(id = "twotabsearchtextbox")
	WebElement searchTextbox;

	@FindBy(id = "nav-search-submit-text")
	WebElement searchIcon;

	@FindAll(@FindBy(css = ".s-search-results .a-color-base.a-text-normal"))
	List<WebElement> resultHeadings;

	@FindBy(css = "#title [id*='Title']")
	WebElement bName;

	@FindBy(id = "bookEdition")
	WebElement edition;

	// @FindBy(css = ".contributorNameID")
	@FindAll(@FindBy(css = ".author .a-link-normal"))
	List<WebElement> authors;

	@FindBy(css = ".a-icon-star")
	WebElement rating;

	@FindBy(id = "acrCustomerReviewText")
	WebElement reviews;

	@FindAll(@FindBy(css = "#isbn_feature_div div.a-row"))
	List<WebElement> isbn;

	@FindAll(@FindBy(css = "#mediaTabs_tabSet>li"))
	List<WebElement> mediaTabs;

	By mediaTabs1 = By.id("mediaTabs_tabSet");

	By mediaTitle = By.cssSelector(".mediaTab_title");

	By mediaSubTitle = By.cssSelector(".mediaTab_subtitle");

	@FindAll(@FindBy(css = "#productDetailsTable .content>ul>li"))
	List<WebElement> prodDetails;

	@FindBy(css = "#productDetailsTable .content")
	WebElement prodDetailsTable;

	public BookSearch(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void selectCategory(String category, String searchValue, ITestContext context) throws InterruptedException {

		Object browser = context.getAttribute("browser");

		Actions ac = new Actions(driver);

		if(browser.toString().equalsIgnoreCase("firefox")) {
			
			Thread.sleep(15000);
			
			ac.moveToElement(driver.findElement(By.id("nav-link-shopall"))).build().perform();
			ac.click(driver.findElement(By.cssSelector("#nav-flyout-anchor #nav-flyout-shopAll"))
					.findElement(By.linkText(category))).build().perform();

			Thread.sleep(2000);
		}
		
		if(browser.toString().equalsIgnoreCase("chrome")) {

			Select setUpDropDown = new Select(categoryDropdown);

			setUpDropDown.selectByVisibleText(category);
		}

		
		ac.sendKeys(searchTextbox, searchValue).sendKeys(Keys.ENTER).build().perform();

	}

	public void selectProductFromList(int prodNo) {

		if (prodNo > 0)
			resultHeadings.get(--prodNo).click();

	}

	public List<Map<String, String>> productDetails(String ignoreCategory) {

		List<Map<String, String>> allEditions = new ArrayList<Map<String, String>>();

		Map<String, String> prodNameAndAuthors = new LinkedHashMap<String, String>(), prodDtls;

		Stream<WebElement> aFilt = authors.stream().filter(p -> p.isDisplayed());

		// List<String> aList = aFilt.map(f ->
		// f.getText()).collect(Collectors.toList());
		List<String> aList = aFilt.map(WebElement::getText).collect(Collectors.toList()); // Same as previous line

		// Store Product Name and Authors Name
		prodNameAndAuthors.put("Book Name", bName.getText());
		prodNameAndAuthors.put("Authors Name", aList.toString());

		Stream<WebElement> filt = mediaTabs.stream()
				.filter(p -> !p.findElement(mediaTitle).getText().equalsIgnoreCase(ignoreCategory));

		List<WebElement> al = filt.collect(Collectors.toList());

		String typeOfEdition = "";
		String editionPrice = "";

		WebElement tit, stit;

		List<WebElement> tits = new ArrayList<WebElement>();

		Map<String, String> editionAndPrice = new LinkedHashMap<String, String>();

		// Retrieve all type of Editions available for the selected product
		for (WebElement aa : al) {
			tit = aa.findElement(mediaTitle);
			stit = aa.findElement(mediaSubTitle);

			if (tit.isDisplayed() && stit.isDisplayed()) {
				typeOfEdition = tit.getText();
				editionPrice = stit.getText();

				if (typeOfEdition != null && editionPrice != null) {
					editionAndPrice.put(typeOfEdition, editionPrice);
				}
			}
		}

		allEditions.add(prodNameAndAuthors);

		String[] dtls;

		// Retrieve Product details
		for (String a : editionAndPrice.keySet()) {

			tits = driver.findElement(mediaTabs1).findElements(mediaTitle);

			for (WebElement a1 : tits) {
				if (a1.getText().equalsIgnoreCase(a) && a1.isDisplayed()) {

					prodDtls = new LinkedHashMap<String, String>();

					prodDtls.put("Edition Type", a);
					prodDtls.put("Price", editionAndPrice.get(a));

					// Select a particular Edition type
					a1.click();

					prodDetailsTable.click();

					// Retrieve select Edition type details
					for (WebElement pd : prodDetails) {
						dtls = pd.getText().split(":", 2);
						prodDtls.put(dtls[0], dtls[1].split("\\(", 2)[0].trim());
					}

					allEditions.add(prodDtls);

					break;
				}
			}
		}

		return allEditions;

	}

	public void printDetails(List<Map<String, String>> pDetails) {

		System.out.println("Inside print function:");

		pDetails.forEach(l -> {
			System.out.println("\n");
			l.entrySet().forEach(pk -> {
				System.out.println(pk.getKey() + "::" + pk.getValue());
			});
		});

	}

	public void writeDetails(List<Map<String, String>> pDetails) throws IOException {

		String dir = System.getProperty("user.dir");
		Path filePath = Paths.get(dir, "BookDetails.properties");

		File file = new File(filePath.toString());
		file.createNewFile();

		FileOutputStream fos = new FileOutputStream(file);

		Properties props;

		for (Map<String, String> l : pDetails) {

			props = new Properties();

			for (Entry<String, String> pk : l.entrySet()) {
				props.setProperty(pk.getKey().replace(" ", "_"), pk.getValue());
			}

			try {
				props.store(fos, "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		fos.close();

	}

}
