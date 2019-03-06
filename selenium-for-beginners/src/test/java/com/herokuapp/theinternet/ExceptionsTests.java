package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTests {
	WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod
	protected void setUp(@Optional("Chrome") String browser) {
		// Create driver
		System.out.println("Creating driver:" + browser);

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Starting default browser: Chrome");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();
	}

	@Parameters({ "username", "password", "expectedLoginFailedMessage" })
	@Test(priority = 1, groups = { "smokeTests", "negativeTests" })
	public void negativeTest(String username, String password, String expectedLoginFailedMessage) {
		System.out.println("Staring negative tests");
		// open page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// Enter incorrect username
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

		// Enter password
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.sendKeys(password);

		// press login button
		WebElement logInButton = driver.findElement(By.className("radius"));
		logInButton.click();

		// verifications

		// successful login message
		WebElement failedLoginMessageElement = driver.findElement(By.id("flash"));
		String actualLoginFailedMessage = failedLoginMessageElement.getText();
		Assert.assertTrue(actualLoginFailedMessage.contains(expectedLoginFailedMessage),
				"actualLoginFailedMessage does not contain expectedLoginFailedMessage\nexpectedFailedLoginMessage: "
						+ expectedLoginFailedMessage + "\nactualSuccessMessage: " + actualLoginFailedMessage);

	}

	@AfterMethod
	protected void tearDown() {
		// close driver
		System.out.println("Closing driver");
		driver.quit();
	}

	/*
	 * @Test(priority = 1, groups = { "smokeTests", "negativeTests" }) public void
	 * incorrectLogin() { System.out.println("Starting incorrectLogin test"); //
	 * Create driver System.setProperty("webdriver.gecko.driver",
	 * "src/main/resources/geckodriver.exe"); WebDriver driver = new
	 * FirefoxDriver(); driver.manage().window().maximize();
	 * 
	 * // open page String url = "http://the-internet.herokuapp.com/login";
	 * driver.get(url); System.out.println("Page is opened.");
	 * 
	 * // Enter incorrect username WebElement username =
	 * driver.findElement(By.id("username")); username.sendKeys("tomsmith1");
	 * 
	 * // Enter password WebElement password =
	 * driver.findElement(By.id("password"));
	 * password.sendKeys("SuperSecretPassword!");
	 * 
	 * // press login button WebElement logInButton =
	 * driver.findElement(By.className("radius")); logInButton.click();
	 * 
	 * // verifications
	 * 
	 * // successful login message WebElement failedLoginMessage =
	 * driver.findElement(By.id("flash")); String expectedLoginFailedMessage =
	 * "Your username is invalid!"; String actualLoginFailedMessage =
	 * failedLoginMessage.getText();
	 * Assert.assertTrue(actualLoginFailedMessage.contains(
	 * expectedLoginFailedMessage),
	 * "actualLoginFailedMessage does not contain expectedLoginFailedMessage\nexpectedFailedLoginMessage: "
	 * + expectedLoginFailedMessage + "\nactualSuccessMessage: " +
	 * actualLoginFailedMessage);
	 * 
	 * // Close browser driver.quit(); }
	 * 
	 * @Test(priority = 2, groups = { "negativeTests" }) public void
	 * incorrectPassword() { System.out.println("Starting incorrectPassword test");
	 * // Create driver System.setProperty("webdriver.chrome.driver",
	 * "src/main/resources/chromedriver.exe"); WebDriver driver = new
	 * ChromeDriver(); driver.manage().window().maximize();
	 * 
	 * // open page String url = "http://the-internet.herokuapp.com/login";
	 * driver.get(url); System.out.println("Page is opened.");
	 * 
	 * // Enter username WebElement username =
	 * driver.findElement(By.id("username")); username.sendKeys("tomsmith");
	 * 
	 * // Enter incorrect password WebElement password =
	 * driver.findElement(By.id("password"));
	 * password.sendKeys("incorrectpassword");
	 * 
	 * // press login button WebElement logInButton =
	 * driver.findElement(By.className("radius")); logInButton.click();
	 * 
	 * // verifications
	 * 
	 * // successful login message WebElement failedLoginMessage =
	 * driver.findElement(By.id("flash")); String expectedLoginFailedMessage =
	 * "Your password is invalid!"; String actualLoginFailedMessage =
	 * failedLoginMessage.getText();
	 * Assert.assertTrue(actualLoginFailedMessage.contains(
	 * expectedLoginFailedMessage),
	 * "actualLoginFailedMessage does not contain expectedLoginFailedMessage\nexpectedFailedLoginMessage: "
	 * + expectedLoginFailedMessage + "\nactualSuccessMessage: " +
	 * actualLoginFailedMessage);
	 * 
	 * // Close browser driver.quit(); }
	 */
}
