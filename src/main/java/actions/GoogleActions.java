package actions;

import pageObjects.GooglePage;
import selenium.utils.DriverHelper;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 3:11 PM
 * purpose of this class is to describe business logic actions that can be performed on Calender page
 */
public class GoogleActions extends ActionsBase<GooglePage> {

	private static String url = "https://google.com";

	/**purpose of the constructor is to initialize page field*/
	public GoogleActions()
	{
		super();
		page = new GooglePage();//in this case we
	}

	@Override
	public void navigateTo() {
		if(!page.isOnPage())
		DriverHelper.navigateToCertainUrl(url);
	}



	public GoogleActions searchString(String searchString)
	{
		page.fillSearchField(searchString);
		page.pressEnter();
		DriverHelper.waitUntilPageIsLoaded();
		return this;
	}

	public GoogleActions assertStringIsPresentAmongSearchResults(String str)
	{
		page.assertStringIsPresentAmongSearchResults(str);
		return this;
	}
}
