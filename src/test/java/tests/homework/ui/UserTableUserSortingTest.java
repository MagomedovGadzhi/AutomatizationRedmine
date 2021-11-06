package tests.homework.ui;

import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static automatization.redmine.ui.browser.BrowserUtils.getElementsText;
import static automatization.redmine.utils.CompareUtils.*;

public class UserTableUserSortingTest extends BaseUITest {
    private User admin;
    private User user1;
    private User user2;
    private User user3;

    @BeforeMethod
    public void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        user1 = new User().create();
        user2 = new User().create();
        user3 = new User().create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "6. Администрирование. Сортировка списка пользователей по пользователю")
    public void testUsersTableLoginSorting() {
        loginPage.login(admin);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        topMenuPage.administration.click();
        Assert.assertEquals(administrationPage.pageName.getText(), "Администрирование");

        administrationPage.users.click();
        Assert.assertEquals(usersPage.pageName.getText(), "Пользователи");
        List<String> logins = getElementsText(usersPage.login);
        assertListSortedByNameAsc(logins);

        usersPage.filterButton("Пользователь").click();
        logins = getElementsText(usersPage.login);
        assertListSortedByNameDesc(logins);
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
        user1.delete();
        user2.delete();
        user3.delete();
    }
}