package actions;

import pageObjects.iPage;

/**
 * Created with IntelliJ IDEA.
 * User: sergiuchuckmisha
 * Date: 9/16/15
 * Time: 3:40 PM
 * purpose of this class is to describe methods common to all actions classes
 * actions class generally is related to one pageObject, can navigate to it, and has methods in terms of business-logic
 * implements singleton pattern
 */
public abstract class ActionsBase<T extends iPage>  {

	/**page should be initialized in constructor of the legatee*/
	protected T page;

	/**each Actions class should be able to navigate to appropriate page*/
	public abstract void navigateTo();

	/**purpose of this method is to navigate to the page of certain Actions class (this Actions is a parameter)*/
	public <T1 extends ActionsBase<T>> T1 navigateTo(Class<T1> clazz) {
		if (!page.isOnPage()) {
			this.navigateTo();
		}

		return clazz.cast(this);
	}


}
