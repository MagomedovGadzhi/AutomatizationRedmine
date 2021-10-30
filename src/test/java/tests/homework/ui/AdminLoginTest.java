package tests.homework.ui;

import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.isElementDisplayed;


public class AdminLoginTest extends BaseUITest {
    private User admin;

    @BeforeClass
    public void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(testName = "1. Авторизация администратором")
    public void positiveAdminLoginTest() {
        loginPage.login(admin);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        Assert.assertEquals(topMenuPage.loggedAs.getText(), "Вошли как " + admin.getLogin());

        Assert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenuPage.myPage.getText(), "Моя страница");
        Assert.assertEquals(topMenuPage.projects.getText(), "Проекты");
        Assert.assertEquals(topMenuPage.administration.getText(), "Администрирование");
        Assert.assertEquals(topMenuPage.help.getText(), "Помощь");
        Assert.assertEquals(topMenuPage.myAccount.getText(), "Моя учётная запись");
        Assert.assertEquals(topMenuPage.logoutButton.getText(), "Выйти");

        Assert.assertFalse(isElementDisplayed(topMenuPage.loginButton));
        Assert.assertFalse(isElementDisplayed(topMenuPage.registration));

        Assert.assertEquals(headerPage.quickSearch.getText(), "Поиск");
        Assert.assertTrue(headerPage.quickSearchInputField.isDisplayed());
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
    }
}