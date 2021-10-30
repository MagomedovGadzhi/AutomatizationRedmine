package tests.homework.ui;

import java.util.List;

import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.getElementsText;
import static automatization.redmine.utils.CompareUtils.*;

public class UserTableNameSortingTest extends BaseUITest {
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

    @Test(testName = "7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void testUsersTableNameSorting() {
        loginPage.login(admin);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        topMenuPage.administration.click();
        Assert.assertEquals(administrationPage.pageName.getText(), "Администрирование");

        administrationPage.users.click();
        Assert.assertEquals(usersPage.pageName.getText(), "Пользователи");
        List<String> lastNames = getElementsText(usersPage.lastName);
        List<String> firstNames = getElementsText(usersPage.firstName);
        assertIsNotSorted(lastNames);
        assertIsNotSorted(firstNames);

        usersPage.filterButton("Фамилия").click();
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameAsc(lastNames);
        assertIsNotSorted(firstNames);

        usersPage.filterButton("Фамилия").click();
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameDesc(lastNames);
        assertIsNotSorted(firstNames);

        usersPage.filterButton("Имя").click();
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameAsc(firstNames);
        assertIsNotSorted(lastNames);

        usersPage.filterButton("Имя").click();
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameDesc(firstNames);
        assertIsNotSorted(lastNames);
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
        user1.delete();
        user2.delete();
        user3.delete();
    }
}