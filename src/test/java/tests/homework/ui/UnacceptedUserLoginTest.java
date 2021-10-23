package tests.homework.ui;

import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UnacceptedUserLoginTest extends BaseUITest {

    private User unacceptedUser;

    @BeforeClass
    public void prepareFixtures() {
        unacceptedUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
        topMenu.loginButton.click();
    }

    @Test
    public void testUnacceptedUserLogin() {
        loginPage.login(unacceptedUser);

        Assert.assertFalse(isElementDisplayed(homePage.homePage));

        Assert.assertEquals(loginPage.errorFlash.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");

        Assert.assertEquals(topMenu.homePage.getText(), "Домашняя страница");
        Assert.assertEquals(topMenu.projects.getText(), "Проекты");
        Assert.assertEquals(topMenu.help.getText(), "Помощь");

        Assert.assertFalse(isElementDisplayed(topMenu.myPage));
        Assert.assertFalse(isElementDisplayed(topMenu.myAccount));
        Assert.assertFalse(isElementDisplayed(topMenu.loggedAs));
        Assert.assertFalse(isElementDisplayed(topMenu.logoutButton));


        Assert.assertEquals(topMenu.loginButton.getText(), "Войти");
        Assert.assertEquals(topMenu.registration.getText(), "Регистрация");
    }
}