package tests.testNg.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
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

    @Test(description = "6. Администрирование. Сортировка списка пользователей по пользователю")
    public void testUsersTableLoginSorting() {
        authorization(admin);

        goToAdministrationPage();

        goToUsersPage();

        clickUserInUsersTable();
    }

    @Step("Выбрать из списка меню \"Пользователи\"")
    private void goToUsersPage() {
        BrowserUtils.click(administrationPage.users, "\"Пользователи\"");
        AllureAssert.assertEquals(usersPage.pageName.getText(), "Пользователи", "Наименование страницы \"Пользователи\"");
        List<String> logins = getElementsText(usersPage.login);
        assertListSortedByNameAsc(logins);
    }

    @Step("В шапке таблицы нажать на \"Пользователь\"")
    private void clickUserInUsersTable() {
        BrowserUtils.click(usersPage.filterButton("Пользователь"), "\"Пользователь\"");
        List<String> logins = getElementsText(usersPage.login);
        assertListSortedByNameDesc(logins);
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        admin.delete();
        user1.delete();
        user2.delete();
        user3.delete();
    }
}