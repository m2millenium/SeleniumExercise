package com.seleniumTests.pages;

import org.openqa.selenium.By;

/**
 * Main page
 * 
 * @author mbiasi
 *
 */
public abstract class MainPage {
    protected MainPage() {
    }

    public static By movieDetailsBtn(String title) {
	return By.xpath("//*[@title=\"" + title + "\"]//..//button");
    }

    public static By movieTitleElem(String title) {
	return By.xpath("//*[contains(text(),\"" + title + "\")]");
    }

    public static By searchTxtBox()
    // cancellation button in pop ups
    {
	return By.xpath("//*[@type=\"search\"]");
    }

    public static By movieTitles()
    // the selector for the movie titles
    {
	return By.cssSelector("h2.jss44");
    }

    public static By movieTitleByIndex(int i)
    // one specific movie title by index
    {
	return By.xpath("(//h2[@class=\"jss39 jss44\"])[" + i + "]");
    }
}
