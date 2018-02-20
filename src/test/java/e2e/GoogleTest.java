package e2e;

import actions.GoogleActions;
import base.SeleniumBaseTest;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 3:01 PM
 * purpose of the class is to contain relatively "long" scenarios like search and verify results
 */
public class GoogleTest extends SeleniumBaseTest {

	private static final GoogleActions googleActions = new GoogleActions();

	/**
	 *  * purpose of the method is to check following scenario:
	 * 1. Opens google.com in browser (HtmlUnit is preferred, but you're free to use any)
	 * 2. fill search field with "qwerty" and press Enter
	 * 3. verify that search results contain string "qwerty"
	 * */
	@Test
	public void test() {
		googleActions.navigateTo();
		googleActions.searchString("qwerty");
		googleActions.assertStringIsPresentAmongSearchResults("qwerty");
	}

}
