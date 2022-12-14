package ui;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seleniumTests.pages.LearnMoreDialog;
import com.seleniumTests.pages.MainPage;

public class SeleniumExerciseTest {
    WebDriver driver;
    WebDriverWait wait;
    String title1 = "The Shawshank Redemption", title2 = "A New Brain";

    @BeforeTest
    public void gotoWebSite() {

	final String DRIVER_PATH = ".\\drivers\\chromedriver\\chromedriver.exe";
	final String DRIVER_TYPE = "webdriver.chrome.driver";
	final String WEB_SITE = "https://top-movies-qhyuvdwmzt.now.sh";
	String expectedTitle = "Top Movies";
	System.setProperty(DRIVER_TYPE, DRIVER_PATH);

	driver = new ChromeDriver();

	driver.get(WEB_SITE);
	System.out.println("BeforeTest successful! Title: " + driver.getTitle());
	Assert.assertEquals(driver.getTitle(), expectedTitle);
    }

    @AfterTest
    public void closeBrowser() {
	driver.quit();
    }

    @Test(priority = 1, description = "1st Test - Open the application and make sure a list of movie titles is displayed.")
    public void firstTest() throws InterruptedException {
	System.out.println("1. Starting firstTest");

	// Wait for a title to be ready
	wait = new WebDriverWait(driver, Duration.ofMillis(2000));
	wait.until(ExpectedConditions.presenceOfElementLocated(MainPage.movieTitleElem(title1)));

	List<WebElement> titles = driver.findElements(MainPage.movieTitles());

	// Assert there are all the expected titles
	Assert.assertTrue(titles.size() == 20);

	// Assert all H2 titles contains some text
	for (WebElement t : titles) {
	    Assert.assertTrue(t.getText().length() > 0);
	}

	System.out.println("firstTest Completed with " + titles.size() + " movie titles");
    }

    @Test(priority = 2, description = "2nd Test - Open the movie The Shawshank Redemption and make sure the release date is correctly displayed.")
    public void secondTest() throws InterruptedException {
	System.out.println("2. Starting secondTest");
	// Using Page Object Model we can describe all the used objects of a Page into a
	// Class
	By movie = MainPage.movieDetailsBtn(title1);
	driver.findElement(movie).click();

	// Check there's a dialog opened with expected title
	checkDialog(title1);

	// Assert that the text under the element with text 'Released on' contains a
	// valid date from 1800
	WebElement dateElem = driver.findElement(LearnMoreDialog.releasedOnTxt());

	String date = dateElem.getAttribute("value");
	Thread.sleep(500); // wait for the elements to be ready
	Assert.assertTrue(date.matches("([12][890][0-9]{2})-([0-2][0-9])-([0-3][0-9])"));

	System.out.println("secondTest Completed ");
    }

    @Test(priority = 3, description = "3rd Test - Retrieve the url of the image and open it in another tab then close the tab.")
    public void thirdTest() {
	System.out.println("3. Starting thirdTest");

	WebElement movieImageElem = driver.findElement(LearnMoreDialog.movieImage());
	String url = movieImageElem.getCssValue("background-image");
	url = url.substring(5, url.length() - 2);

	System.out.println("thirdTest - URL: " + url);

	ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	Assert.assertTrue(tabs.size() == 1);
	// Open a new tab and use the URL of the image
	((JavascriptExecutor) driver).executeScript("window.open()");
	tabs = new ArrayList<String>(driver.getWindowHandles());
	driver.switchTo().window(tabs.get(1));
	driver.get(url);

	Assert.assertTrue(tabs.size() > 1);
	// Assert the title of the new tab contains .jpg
	Assert.assertTrue(driver.getTitle().contains(".jpg"));

	// close the tab and go back to the first one
	driver.close();
	driver.switchTo().window(tabs.get(0));

	checkDialog(title1);

	System.out.println("thirdTest Completed");
    }

    @Test(priority = 4, description = "4th Test - Search for Star Trek and make sure that the movie Star Trek: First Contact is displayed in the search results and the movie The Shawshank Redemption is no longer visible.")
    public void fourthTest() throws InterruptedException {
	System.out.println("4. Starting fourthTest");
	// close the dialog window
	WebElement closeDialogBtn = driver.findElement(LearnMoreDialog.closeBtn());
	closeDialogBtn.click();

	// Wait for the movie The Shawshank Redemption to be present
	wait.until(ExpectedConditions.presenceOfElementLocated(MainPage.movieTitleElem(title1)));

	// Type into the search textbox "Star Trek" and click ENTER
	WebElement searchTxtbox = driver.findElement(MainPage.searchTxtBox());
	Thread.sleep(500);
	searchTxtbox.sendKeys("Star Trek" + Keys.ENTER);
	Thread.sleep(500);
	// Assert that the movie The Shawshank Redemption is no more visible
	List<WebElement> expectedMovie = driver.findElements(MainPage.movieTitleElem(title1));

	Assert.assertEquals(expectedMovie.size(), 0);

	System.out.println("fourthTest Completed ");
    }

    @Test(priority = 5, description = "5th Test - Search for A New and verify that all the titles displayed contain the search phrase.")
    public void fifthTest() throws InterruptedException {
	System.out.println("5. Starting fifthTest");
	String searchString = "A New";

	// Type into the search textbox "A New" and click ENTER
	WebElement searchTxtbox = driver.findElement(MainPage.searchTxtBox());

	searchTxtbox.sendKeys(Keys.CONTROL + "A" + Keys.ENTER);

	searchTxtbox.sendKeys(searchString + Keys.ENTER);
	List<WebElement> titles = driver.findElements(MainPage.movieTitles());

	// Assert there are some titles
	Assert.assertTrue(titles.size() > 0);
	Thread.sleep(1000);

	// This test is expected to fail since the whole phrase "A New" is not found in
	// the 10th movie title

	// Assert all H2 titles contains some text
	for (int i = 1; i < titles.size(); i++) {
	    WebElement t = driver.findElement(MainPage.movieTitleByIndex(i));

	    Assert.assertTrue(t.getText().toLowerCase().contains(searchString.toLowerCase()),
		    "The title #" + i + ": " + t.getText() + " doesn't contain the search phrase");
	}

    }

    @Test(priority = 6, description = "6th Test - Take any movie you like and make sure the Released on, popularity, vote average and vote count fields have the expected values.")
    public void sixthTest() throws InterruptedException {
	String expReleasedOn = "2015-06-24";

	System.out.println("6. Starting sixthTest");

	WebElement movie = driver.findElement(MainPage.movieDetailsBtn(title2));
	movie.click();

	// Check there's a dialog opened with expected title
	checkDialog(title2);

	// Assert that the text under the element with text 'Released on' contains the
	// expected date
	WebElement dateElem = driver.findElement(LearnMoreDialog.releasedOnTxt());

	String date = dateElem.getAttribute("value");
//	Thread.sleep(500);
	Assert.assertEquals(date, expReleasedOn);

	// Assert that the text under the element with text 'Popularity' contains a
	// valid positive number
	WebElement populElem = driver.findElement(LearnMoreDialog.popularityTxt());

	String popularity = populElem.getAttribute("value").replace(".", "");
//	Thread.sleep(500);
	Assert.assertTrue(isPositiveInteger(popularity));

	// Assert that the text under the element with text 'Vote average' contains a
	// valid number
	WebElement voteAverElem = driver.findElement(LearnMoreDialog.voteAverageTxt());

	String voteAver = voteAverElem.getAttribute("value");
	Thread.sleep(500);

	Assert.assertTrue(isPositiveFloat(voteAver));

	// Assert that the text under the element with text 'Vote count' contains a
	// valid positive number
	WebElement voteCountElem = driver.findElement(LearnMoreDialog.voteCountTxt());

	String voteCount = voteCountElem.getAttribute("value");
	Thread.sleep(500);

	Assert.assertTrue(isPositiveInteger(voteCount));
	System.out.println("sixthTest Completed ");
    }

    @Test(priority = 7, description = "7th Test - Search with empty field and check that some movies are there.")
    public void seventhTest() throws InterruptedException {
	System.out.println("7. Starting seventhTest");
	// close the dialog window
	WebElement closeDialogBtn = driver.findElement(LearnMoreDialog.closeBtn());
	closeDialogBtn.click();

	// Assert that the movie The Shawshank Redemption is not present
	List<WebElement> expectedMovie = driver.findElements(MainPage.movieTitleElem(title1));

	Assert.assertEquals(expectedMovie.size(), 0);

	// Empty the search textbox and click ENTER
	WebElement searchTxtbox = driver.findElement(MainPage.searchTxtBox());
	Thread.sleep(500);
	searchTxtbox.sendKeys(Keys.CONTROL + "A" + Keys.ENTER);

	searchTxtbox.sendKeys(Keys.DELETE, Keys.ENTER);
	Thread.sleep(500);
	// Assert that the movie The Shawshank Redemption is now visible
	expectedMovie = driver.findElements(MainPage.movieTitleElem(title1));

	Assert.assertEquals(expectedMovie.size(), 1, "The movie " + title1 + " is expected to be present");

	System.out.println("seventhTest Completed ");
    }

    private void checkDialog(String title) {
	wait.until(ExpectedConditions.presenceOfElementLocated(LearnMoreDialog.dialogTitle()));

	WebElement dialog = driver.findElement(LearnMoreDialog.dialog());
	Assert.assertTrue(dialog.isDisplayed());

	WebElement dialogTitle = driver.findElement(LearnMoreDialog.dialogTitle());
	Assert.assertEquals(dialogTitle.getAccessibleName(), title);
	System.out.println("dialogTitle: " + dialogTitle.getAccessibleName());
    }

    private boolean isPositiveInteger(String input) {
	if (input.contains("-"))
	    return false;
	try {
	    Integer.parseInt(input);
	} catch (NumberFormatException ne) {
	    return false;
	}
	return true;
    }

    private boolean isPositiveFloat(String input) {
	if (input.contains("-"))
	    return false;
	try {
	    Float.parseFloat(input);
	} catch (NumberFormatException ne) {
	    return false;
	}
	return true;
    }
}
