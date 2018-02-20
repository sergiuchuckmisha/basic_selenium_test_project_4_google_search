package selenium.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import selenium.browsers.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 7:08 PM
 * purpose of the class is to contain simple smoke web UI test which calls factory for Chrome browser, opens google
 * todo: perform some search
 */
public class DriverHelperTest {

	@Before
	@After
	/**purpose of the method is to clear possible browser initializations*/
	public void closeBrowser()
	{
		WebDriverFactory.clearDriver();
	}

	@Test
	public void navigateToCertainUrlTest()
	{
		DriverHelper.navigateToCertainUrl("http://google.com");
		DriverHelper.waitUntilPageIsLoaded();
		System.out.println(DriverHelper.getTitle());
		org.junit.Assert.assertTrue(DriverHelper.isElementPresent(By.name("q")));
	}
}
