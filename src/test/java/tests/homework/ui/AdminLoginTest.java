package tests.homework.ui;

import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AdminLoginTest extends BaseUITest {
    private User admin;

    @BeforeMethod
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser("/login");
    }

    @Test
    public void positiveAdminLoginTest() {
        loginPage.login(admin);
        Assert.assertEquals(topMenu.myAccount.getText(), "Моя учётная запись");
    }
}
