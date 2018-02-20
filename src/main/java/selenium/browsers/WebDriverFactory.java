package selenium.browsers;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 11:23 AM
 * pattern Factory
 * purpose of the class is to generate selenium browsers objects
 * currently only one instance of WebDriver is supported
 * type of this single WebDriver is defined in Config file or in enviroment variable "browser"
 * currently the only public method  getDriver() should be used
 * todo: support multiple WebDriver instances
 */
public class WebDriverFactory {

	private static WebDriver driver;
	private static Logger LOG = Logger.getLogger(WebDriverFactory.class.toString());

	private static DesiredCapabilities capabilities;

	private static void setCommonCapabilities() {
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ACCEPT");
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	}

	/**purpose of this enum is to enlist browsers which can be used in the project
	 * WebDriverFactory will take member of this enum as parameter
	 * String label of browser should be converted to enum instance asap using method getBrowserByLabel()*/
	public enum browsers{
		FireFox("FF"), HtmlUnit("HU");
		public final String label;


		browsers(String label) {
			this.label = label;
		}

		public static browsers getBrowserByLabel(String label) throws Exception {
			for(browsers browser : browsers.values())
			{
				if(browser.label.equals(label))
					return browser;
			}
			throw new Exception("Unknown browser label: " + label);
		}
	}

	public static WebDriver getDriver() {
		return getDriver(Config.BROWSER);
	}

//	/**for public purposes method without parameters (which takes browser type from environment variable) should be used*/
	private static WebDriver getDriver(browsers browser) {
				LOG.log(Level.CONFIG, "Requested driver: " + browser);
		// 1. WebDriver instance is not created yet
		if (driver == null) {
			LOG.log(Level.CONFIG, "No previous driver found");
			driver = newWebDriver( browser);
			return driver;
		}
		try {
			driver.getCurrentUrl();//touch with stick
		} catch (Throwable t) {
			t.printStackTrace();
			newWebDriver( browser);
			return driver;
		}
		return driver;
	}

	public static void clearDriver()
	{
		if(null != driver)
		{driver.quit();}
		driver = null;
	}

	private static WebDriver newWebDriver(WebDriverFactory.browsers browser)
	{
		switch (browser)
		{
			case FireFox:
				return newFireFoxDiver();
			case HtmlUnit:
				return newHtmlUnitDiver();
			default:
				throw new IllegalArgumentException(browser.label);//this line should not be reached
		}
	}

	private static WebDriver newHtmlUnitDiver() {
//		capabilities = new DesiredCapabilities().htmlUnitWithJs();
		capabilities = new DesiredCapabilities().htmlUnit();
		capabilities.setBrowserName("HtmlUnit");
		setCommonCapabilities();

//		return new HtmlUnitDriver(capabilities);
		return new HtmlUnitDriver(true);
	}

	private static WebDriver newFireFoxDiver() {
		capabilities = new DesiredCapabilities().firefox();
		capabilities.setBrowserName("firefox");
		//capabilities.setCapability("unexpectedAlertBehaviour", "ACCEPT");
		//setCommonCapabilities();
		//capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ACCEPT");

		FirefoxDriver firefoxDriver;

		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("setAcceptUntrustedCertificates", "true");
		firefoxProfile.setPreference("extensions.firebug.currentVersion", Config.FIREBUG_VER);
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpg, text/csv, text/xml, " +
				"application/xml, application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/excel, application/pdf");

		try {
			firefoxDriver = new FirefoxDriver(new FirefoxBinary(new File(Config.FIREFOX_PATH)), firefoxProfile, capabilities);
		} catch (Exception e1) {
			LOG.log(Level.SEVERE, "creation of firefox webdriver instance with capabilities has failed \n try to create firefox webdriver instance without capabilities");
			firefoxDriver = new FirefoxDriver(firefoxProfile);  //without capabilities
		}

		return firefoxDriver;
	}







}
