package selenium.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.browsers.WebDriverFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 12:46 PM
 * this class wraps calls to WebDriver instance in order to add checks, try-catch etc to common WebDriver functions
 */
public class DriverHelper {
	private static Logger LOG = Logger.getLogger(DriverHelper.class.toString());
	public static final int DEFAULT_TIMEOUT_SECONDS = 5; //wait up to this time for element to appear or page to be loaded

	/**
	 * Gets text from element
	 *
	 * @param locator target element locator
	 * @return String text
	 */
	public static String getText(By locator) {
		if (!isElementPresent(locator))
			return "";

		return findElement(locator).getText();
	}

	public static String getTitle() {
		waitUntilPageIsLoaded();
		return WebDriverFactory.getDriver().getTitle();
	}

	public static boolean isElementPresent(By locator)
	{
		waitUntilPageIsLoaded();
		return WebDriverFactory.getDriver().findElements(locator).size() != 0;
	}

	public static void navigateToCertainUrl(String url)
	{
		try{
			WebDriverFactory.getDriver().get(url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			WebDriverFactory.getDriver().quit();
		}
	}

	public static void waitUntilPageIsLoaded()
	{
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT_SECONDS);

		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return AjaxHelper.isPageLoaded();
				}
			});
		} catch (Exception ignored) {
		}
	}

	public static WebElement findElement(By locator) {
		return findElement(locator, DEFAULT_TIMEOUT_SECONDS);
	}

	public static WebElement findElement(By locator, int newTimeOutSeconds) {
		LOG.log(Level.WARNING,"Find element: " + locator);
		WebElement webElement = null;

		try {
			webElement = new WebDriverWait(WebDriverFactory.getDriver(), newTimeOutSeconds).until(ExpectedConditions.presenceOfElementLocated(locator));
			LOG.log(Level.WARNING,"Find element: " + locator + " => true");
		} catch (Exception ex) {
				LOG.log(Level.WARNING,"Web element not found: " + locator);
			}
		return webElement;
	}

	/**purpose of the method is to enrich standard click with try/catch and possible click with JavaScript*/
	public static void click(By locator)
	{
		waitUntilPageIsLoaded();
		WebElement element = findElement(locator);
		try {
			element.click();
		} catch (Exception ex) {
			ex.printStackTrace();
			AjaxHelper.click(element);
		}
		waitUntilPageIsLoaded();
	}


	public static void type(By locator, String str)
	{
		waitUntilPageIsLoaded();
		WebElement element = findElement(locator);
		element.clear();
		element.sendKeys(str);
	}
}
