package tests.homework.ui;

import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.isElementDisplayed;

public class UnacceptedUserLoginTest extends BaseUITest {

    private User unacceptedUser;

    @BeforeClass
    public void prepareFixtures() {
        unacceptedUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test
    public void testUnacceptedUserLogin() {
        loginPage.login(unacceptedUser);

        Assert.assertFalse(isElementDisplayed(homePage.homePage));

        Assert.assertEquals(loginPage.errorFlash.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");

        Assert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenuPage.projects.getText(), "Проекты");
        Assert.assertEquals(topMenuPage.help.getText(), "Помощь");

        Assert.assertFalse(isElementDisplayed(topMenuPage.myPage));
        Assert.assertFalse(isElementDisplayed(topMenuPage.myAccount));
        Assert.assertFalse(isElementDisplayed(topMenuPage.loggedAs));
        Assert.assertFalse(isElementDisplayed(topMenuPage.logoutButton));


        Assert.assertEquals(topMenuPage.loginButton.getText(), "Войти");
        Assert.assertEquals(topMenuPage.registration.getText(), "Регистрация");
    }
}