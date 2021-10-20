package tests.homework.ui;

import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UnacceptedUserLoginTest extends BaseUITest {

    private User unacceptedUser;

    @BeforeMethod
    public void prepareFixtures() {
        unacceptedUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser("/login");
    }

    @Test
    public void testUnacceptedUserLogin() {
        loginPage.login(unacceptedUser);
        assertEquals(loginPage.errorFlash.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
    }
}