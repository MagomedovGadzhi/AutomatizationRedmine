package tests.homework.ui;

import automatization.redmine.ui.browser.Browser;
import automatization.redmine.ui.browser.BrowserManager;
import automatization.redmine.ui.pages.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

public class BaseUITest {

    protected Browser browser;
    protected TopMenuPage topMenuPage;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;
    protected MyPage myPage;
    protected HomePage homePage;
    protected AdministrationPage administrationPage;

    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        initPages();
    }

    protected void openBrowser(String uri) {
        browser = BrowserManager.getBrowser(uri);
        topMenuPage = new TopMenuPage();
        headerPage = new HeaderPage();
        loginPage = new LoginPage();
        myPage = new MyPage();
        homePage = new HomePage();
        administrationPage = new AdministrationPage();
    }

    private void initPages() {
        headerPage = Page.getPage(HeaderPage.class);
        loginPage = Page.getPage(LoginPage.class);
        administrationPage = Page.getPage(AdministrationPage.class);
        userTablePage = Page.getPage(UserTablePage.class);
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