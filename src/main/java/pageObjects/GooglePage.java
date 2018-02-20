package pageObjects;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import selenium.browsers.WebDriverFactory;
import selenium.utils.DriverHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 3:03 PM
 * purpose of the class is to describe html elements on page
 * http://dev.sencha.com/extjs/5.1.0/examples/calendar/index.html
 * pageObject pattern is implemented
 */
public class GooglePage implements iPage {

    private static By searchFieldLocator = By.name("q");

    @Override
    public boolean isOnPage() {
        return DriverHelper.isElementPresent(searchFieldLocator) && "Google".equals(DriverHelper.getTitle());
    }

    public void pressEnter() {
        DriverHelper.findElement(searchFieldLocator).sendKeys(Keys.RETURN);
    }

    public void fillSearchField(String str) {
        DriverHelper.type(searchFieldLocator, str);
    }

    public void assertStringIsPresentAmongSearchResults(String str) {
        Collection<WebElement> collectionOfSearchResults = WebDriverFactory.getDriver().findElements(By.xpath(".//h3/a"));

        for(WebElement searchResult: collectionOfSearchResults){
            if (!searchResult.getText().isEmpty() && searchResult.getText().toLowerCase().contains(str.toLowerCase()))
                return;
        }

        //if we here that means that search string is not found and testcase is failed.
        // So, we print all search results and throw an exception.
        for(WebElement searchResult: collectionOfSearchResults){
            System.out.println("search result:\n" + searchResult.getText());
        }
        throw new NoSuchElementException(String.format("search string %s is not found among search results from Google", str));
    }
}
