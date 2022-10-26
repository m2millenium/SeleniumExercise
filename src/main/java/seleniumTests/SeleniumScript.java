package seleniumTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumScript {
    public static void main(String[] args) {
	final String DRIVER_PATH = ".\\drivers\\chromedriver\\chromedriver.exe";
	final String DRIVER_TYPE = "webdriver.chrome.driver";
	final String WEB_SITE = "https://top-movies-qhyuvdwmzt.now.sh";
	System.setProperty(DRIVER_TYPE, DRIVER_PATH);

	WebDriver driver = new ChromeDriver();
	driver.get(WEB_SITE);
	System.out.println(driver.getTitle());
	driver.quit();
    }
}