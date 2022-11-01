package com.seleniumTests.pages;

import org.openqa.selenium.By;

/**
 * LearnMore Dialog page
 * 
 * @author mbiasi
 *
 */
public abstract class LearnMoreDialog {
    protected LearnMoreDialog() {
    }

    public static By dialog()
    // Dialog window
    {
	return By.xpath("//*[@role=\"dialog\"]");
    }

    public static By dialogTitle()
    // Dialog window title
    {
	return By.xpath("//*[@id=\"form-dialog-title\"]//h2");
    }

    public static By releasedOnTxt()
    // ReleasedOn text value
    {
	return By.xpath("//*[contains(text(),'Released on')]//..//input");
    }

    public static By movieImage() {
	return By.cssSelector("div.jss89.movie-detail-image");
    }

    public static By closeBtn()
    // close button
    {
	return By.xpath("//*[contains(text(),'Close')]");
    }

    public static By popularityTxt() {
	return By.xpath("//*[contains(text(),'Popularity')]//..//input");
    }

    public static By voteAverageTxt() {
	return By.xpath("//*[contains(text(),'Vote average')]//..//input");
    }

    public static By voteCountTxt() {
	return By.xpath("//*[contains(text(),'Vote count')]//..//input");
    }

    /**
     * Get the ID for web if available. This method could be used if developers
     * added IDs to web elements
     * 
     * @param Sting id, to pass the id of the element.
     *
     */
    public static By getId(String id) {
	return getId(id, 0);
    }

    /**
     * Get the ID for web
     * 
     * @param Sting id, to pass the id of the element.
     * @param int   index, to manage the index for tables/cells (if 0 : no index)
     *
     */
    public static By getId(String id, int index) {
	By result = null;

	if (index != 0)
	    result = By.xpath("(//*[@id=\"" + id + "\"])[" + index + "]");
	else
	    result = By.xpath("//*[@id=\"" + id + "\"]");

	return result;
    }
}
