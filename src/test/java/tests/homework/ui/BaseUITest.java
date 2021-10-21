package tests.homework.ui;

import automatization.redmine.ui.browser.Browser;
import automatization.redmine.ui.browser.BrowserManager;
import automatization.redmine.ui.pages.Header;
import automatization.redmine.ui.pages.TopMenu;
import automatization.redmine.ui.pages.LoginPage;
import org.testng.annotations.AfterMethod;

public class BaseUITest {

    protected Browser browser;
    protected TopMenu topMenu;
    protected Header header;
    protected LoginPage loginPage;

    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        topMenu = new TopMenu();
        header = new Header();
        loginPage = new LoginPage();
    }

    protected void openBrowser(String uri) {
        browser = BrowserManager.getBrowser(uri);
        topMenu = new TopMenu();
        header = new Header();
        loginPage = new LoginPage();
    }

    @AfterMethod
    public void tearDown() {
        BrowserManager.closeBrowser();
    }
}
