package tests.homework.ui;

import automatization.redmine.model.user.User;
import automatization.redmine.ui.pages.TopMenu;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
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

        String actualLoggedAsUser = topMenu.loggedAs.getText();
        String expectedLoggedAsUser = "Вошли как " + admin.getLogin();
        Assert.assertEquals(actualLoggedAsUser, expectedLoggedAsUser);

        Assert.assertEquals(topMenu.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenu.myPage.getText(), "Моя страница");
        Assert.assertEquals(topMenu.projects.getText(), "Проекты");
        Assert.assertEquals(topMenu.administration.getText(), "Администрирование");
        Assert.assertEquals(topMenu.help.getText(), "Помощь");
        Assert.assertEquals(topMenu.myAccount.getText(), "Моя учётная запись");
        Assert.assertEquals(topMenu.logoutButton.getText(), "Выйти");

        isElementDisplayed(topMenu.loginButton);
        isElementDisplayed(topMenu.registration);

        header.quickSearch.isDisplayed();
        header.quickSearchInputField.isDisplayed();
    }

    private Boolean isElementDisplayed(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
    }
}