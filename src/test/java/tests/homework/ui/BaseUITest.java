package tests.homework.ui;

import automatization.redmine.ui.browser.Browser;
import automatization.redmine.ui.browser.BrowserManager;
import automatization.redmine.ui.pages.*;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;

public class BaseUITest {

    protected Browser browser;
    protected TopMenuPage topMenuPage;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;
    protected MyPage myPage;
    protected HomePage homePage;
    protected AdministrationPage administrationPage;
    protected UsersPage usersPage;
    protected ProjectsPage projectsPage;
    protected NewUserPage newUserPage;

    @Step("Открыт браузер на главной странице")
    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        initPages();
    }

    @Step("Открыт браузер на странице: {0}")
    protected void openBrowser(String uri) {
        browser = BrowserManager.getBrowser(uri);
        initPages();
    }

    private void initPages() {
        topMenuPage = Page.getPage(TopMenuPage.class);
        headerPage = Page.getPage(HeaderPage.class);
        loginPage = Page.getPage(LoginPage.class);
        myPage = Page.getPage(MyPage.class);
        homePage = Page.getPage(HomePage.class);
        administrationPage = Page.getPage(AdministrationPage.class);
        usersPage = Page.getPage(UsersPage.class);
        projectsPage = Page.getPage(ProjectsPage.class);
        newUserPage = Page.getPage(NewUserPage.class);
    }

    @AfterMethod (description = "Закрытие браузера")
    public void tearDown() {
        BrowserManager.closeBrowser();
    }
}