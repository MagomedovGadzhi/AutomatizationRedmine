package tests.homework.ui;

import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class AdminLoginTest extends BaseUITest {
    private User admin;

    @BeforeClass
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
        topMenu.loginButton.click();
    }

    @Test
    public void positiveAdminLoginTest() {
        loginPage.login(admin);
        Assert.assertEquals(homePage.homePage.getText(), "Домашняя страница");

        Assert.assertEquals(topMenu.loggedAs.getText(), "Вошли как " + admin.getLogin());

        Assert.assertEquals(topMenu.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenu.myPage.getText(), "Моя страница");
        Assert.assertEquals(topMenu.projects.getText(), "Проекты");
        Assert.assertEquals(topMenu.administration.getText(), "Администрирование");
        Assert.assertEquals(topMenu.help.getText(), "Помощь");
        Assert.assertEquals(topMenu.myAccount.getText(), "Моя учётная запись");
        Assert.assertEquals(topMenu.logoutButton.getText(), "Выйти");

        Assert.assertFalse(isElementDisplayed(topMenu.loginButton));
        Assert.assertFalse(isElementDisplayed(topMenu.registration));

        Assert.assertEquals(header.quickSearch.getText(), "Поиск");
        Assert.assertTrue(header.quickSearchInputField.isDisplayed());
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
    }
}