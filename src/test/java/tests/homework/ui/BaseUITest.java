package tests.homework.ui;

import automatization.redmine.ui.browser.Browser;
import automatization.redmine.ui.browser.BrowserManager;
import automatization.redmine.ui.pages.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

public class BaseUITest {

    protected Browser browser;
    protected TopMenu topMenu;
    protected Header header;
    protected LoginPage loginPage;
    protected MyPage myPage;
    protected HomePage homePage;

    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        topMenu = new TopMenu();
        header = new Header();
        loginPage = new LoginPage();
        myPage = new MyPage();
        homePage = new HomePage();
    }

    protected void openBrowser(String uri) {
        browser = BrowserManager.getBrowser(uri);
        topMenu = new TopMenu();
        header = new Header();
        loginPage = new LoginPage();
        myPage = new MyPage();
        homePage = new HomePage();
    }

    protected Boolean isElementDisplayed(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @AfterMethod
    public void tearDown() {
        BrowserManager.closeBrowser();
    }
}