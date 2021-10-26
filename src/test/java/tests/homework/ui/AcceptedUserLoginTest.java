package tests.homework.ui;

import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class AcceptedUserLoginTest extends BaseUITest {
    private User acceptedUser;

    @BeforeClass
    public void prepareFixtures() {
        acceptedUser = new User() {{
            setStatus(Status.ACTIVE);
        }};
        acceptedUser.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test
    public void positiveAdminLoginTest() {
        loginPage.login(acceptedUser);
        Assert.assertEquals(homePage.homePage.getText(), "Домашняя страница");

        Assert.assertEquals(topMenuPage.loggedAs.getText(), "Вошли как " + acceptedUser.getLogin());

        Assert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenuPage.myPage.getText(), "Моя страница");
        Assert.assertEquals(topMenuPage.projects.getText(), "Проекты");
        Assert.assertEquals(topMenuPage.help.getText(), "Помощь");
        Assert.assertEquals(topMenuPage.myAccount.getText(), "Моя учётная запись");
        Assert.assertEquals(topMenuPage.logoutButton.getText(), "Выйти");

        Assert.assertFalse(isElementDisplayed(topMenuPage.loginButton));
        Assert.assertFalse(isElementDisplayed(topMenuPage.registration));
        Assert.assertFalse(isElementDisplayed(topMenuPage.administration));

        Assert.assertEquals(headerPage.quickSearch.getText(), "Поиск");
        Assert.assertTrue(headerPage.quickSearchInputField.isDisplayed());
    }

    @AfterClass
    public void postConditions() {
        acceptedUser.delete();
    }
}