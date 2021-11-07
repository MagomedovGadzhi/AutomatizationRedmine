package tests.homework.ui;

import java.util.List;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.getElementsText;
import static automatization.redmine.utils.CompareUtils.*;

public class UserTableNameSortingTest extends BaseUITest {
    private User admin;
    private User user1;
    private User user2;
    private User user3;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора. Заведено несколько пользователей в системе")
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

    @Test(description = "7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void testUsersTableNameSorting() {
        loginPage.login(admin);
        AllureAssert.assertEquals(homePage.pageName.getText(), "Домашняя страница", "Наименование страницы \"Домашняя страница\"");

        BrowserUtils.click(topMenuPage.administration, "\"Администрирование\"");
        AllureAssert.assertEquals(administrationPage.pageName.getText(), "Администрирование", "Наименование страницы \"Администрирование\"");

        BrowserUtils.click(administrationPage.users, "\"Пользователи\"");
        AllureAssert.assertEquals(usersPage.pageName.getText(), "Пользователи", "Наименование страницы \"Пользователи\"");
        List<String> lastNames = getElementsText(usersPage.lastName);
        List<String> firstNames = getElementsText(usersPage.firstName);
        assertIsNotSorted(lastNames);
        assertIsNotSorted(firstNames);

        BrowserUtils.click(usersPage.filterButton("Фамилия"), "\"Фамилия\"");
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameAsc(lastNames);
        assertIsNotSorted(firstNames);

        BrowserUtils.click(usersPage.filterButton("Фамилия"), "\"Фамилия\"");
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameDesc(lastNames);
        assertIsNotSorted(firstNames);

        BrowserUtils.click(usersPage.filterButton("Имя"), "\"Имя\"");
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameAsc(firstNames);
        assertIsNotSorted(lastNames);

        BrowserUtils.click(usersPage.filterButton("Имя"), "\"Имя\"");
        lastNames = getElementsText(usersPage.lastName);
        firstNames = getElementsText(usersPage.firstName);
        assertListSortedByNameDesc(firstNames);
        assertIsNotSorted(lastNames);
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        admin.delete();
        user1.delete();
        user2.delete();
        user3.delete();
    }
}