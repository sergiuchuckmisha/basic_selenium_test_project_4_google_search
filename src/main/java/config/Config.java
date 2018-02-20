package config;

import org.apache.commons.lang3.StringUtils;
import selenium.browsers.WebDriverFactory;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 10:41 AM
 * purpose of the class is to contain global configuration properties of the project
 */
public class Config {
	public static WebDriverFactory.browsers BROWSER;
	public static final File DRIVER_CHROME_PATH;
	public static final String CHROME_BROWSER_PATH;
	public static final String FIREFOX_PATH;
	public static final String FIREBUG_VER;
	public static final File DRIVER_IE_PATH;

	static {

		FIREBUG_VER = "1.10.4";
//		FIREFOX_PATH = "C:\\dev\\programs\\firefox\\firefox.exe";
		FIREFOX_PATH = "D:\\dev\\programs\\firefox\\firefox.exe";
//		DRIVER_CHROME_PATH = new File("C:\\dev\\drivers\\chromedriver.exe");
		DRIVER_CHROME_PATH = new File("D:\\dev\\programs\\drivers\\chromedriver.exe");
		CHROME_BROWSER_PATH = null; //"C:\\dev\\programs\\Google\\Chrome\\Application\\chrome.exe";
//		DRIVER_IE_PATH = new File("C:\\dev\\drivers\\IEDriverServer.exe");
		DRIVER_IE_PATH = new File("D:\\dev\\programs\\drivers\\IEDriverServer.exe");

		try {
			if(StringUtils.isEmpty(getEnvVariable("browser")))//todo refactoring seems to be required because 2 checks (try/catch and if) are intersected
			{BROWSER = WebDriverFactory.browsers.getBrowserByLabel("FF");}
			else
			{BROWSER = WebDriverFactory.browsers.getBrowserByLabel(getEnvVariable("browser"));}
		} catch (Exception e) {
			e.printStackTrace();
			BROWSER = WebDriverFactory.browsers.FireFox;//Chrome is a default browser
		}
	}

	/**purpose of the method is to catch certain property from environment variables with local or Jenkins use*/
	public static final String getEnvVariable(String variable) {
		String property = System.getProperty(variable);  // use to run on Jenkins
		if (property == null)
			property = System.getenv(variable);   // use to run local
		if (property == null) {
			property = "";
		}
		return property;
	}
}
