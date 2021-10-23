package tests.homework.ui;

import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ApprovedUserLoginTest extends BaseUITest {
    private User user;

    @BeforeClass
    public void prepareFixtures() {
        user = new User() {{
            setStatus(Status.ACTIVE);
            new Token(this);
            new Email(this);
        }};
        user.create();

        openBrowser();
        topMenu.loginButton.click();
    }

    @Test
    public void positiveAdminLoginTest() {
        loginPage.login(user);
        Assert.assertEquals(homePage.homePage.getText(), "Домашняя страница");

        Assert.assertEquals(topMenu.loggedAs.getText(), "Вошли как " + user.getLogin());

        Assert.assertEquals(topMenu.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenu.myPage.getText(), "Моя страница");
        Assert.assertEquals(topMenu.projects.getText(), "Проекты");
        Assert.assertEquals(topMenu.help.getText(), "Помощь");
        Assert.assertEquals(topMenu.myAccount.getText(), "Моя учётная запись");
        Assert.assertEquals(topMenu.logoutButton.getText(), "Выйти");

        Assert.assertFalse(isElementDisplayed(topMenu.loginButton));
        Assert.assertFalse(isElementDisplayed(topMenu.registration));
        Assert.assertFalse(isElementDisplayed(topMenu.administration));

        Assert.assertTrue(header.quickSearch.isDisplayed());
        Assert.assertTrue(header.quickSearchInputField.isDisplayed());
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
        user.delete();
    }
}