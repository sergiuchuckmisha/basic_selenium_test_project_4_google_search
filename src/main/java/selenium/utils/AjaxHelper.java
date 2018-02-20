package selenium.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import selenium.browsers.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 2:08 PM
 * purpose of the class is to accumulate methods which works with WebDriver page via JavaScript
 * this class has dependency only on WebDriverFactory to fet instance of WebDriver
 */
public class AjaxHelper {

	private static JavascriptExecutor getJsExecutor() {
		return (JavascriptExecutor) WebDriverFactory.getDriver();
	}

	public static boolean isAjaxComplete() {
		try {
			return (Boolean) getJsExecutor().executeScript("return $.active == 0");
		} catch (WebDriverException ex) {
			return true;
		}
	}

	public static Boolean isPageLoaded() {
		try {
			return getJsExecutor().executeScript("return document.readyState").equals("complete");
		} catch (WebDriverException ex) {
			return true;
		}
	}

	public static void click(WebElement element) {
		try {
			getJsExecutor().executeScript("arguments[0].click();", element);
		} catch (WebDriverException ex) {
			ex.printStackTrace();
			throw new WebDriverException();
		}
	}
}
