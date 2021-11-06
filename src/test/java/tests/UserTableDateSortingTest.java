package tests;

import java.util.List;

import automatization.redmine.model.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.homework.ui.BaseUITest;

import static automatization.redmine.ui.browser.BrowserUtils.getElementsText;
import static automatization.redmine.utils.CompareUtils.*;

public class UserTableDateSortingTest extends BaseUITest {

    @BeforeMethod
    public void prepareFixtures() {
        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser("/login");
        loginPage.login(admin);
        topMenuPage.administration.click();
        administrationPage.users.click();
    }

    @Test
    public void testUsersTableDateSorting() {
        usersPage.filterButton("Создано").click();
        List<String> creationDatesByDesc = getElementsText(usersPage.creationDates);
        assertListSortedByDateDesc(creationDatesByDesc);

        usersPage.filterButton("Создано").click();
        List<String> creationDatesByAsc = getElementsText(usersPage.creationDates);
        assertListSortedByDateAsc(creationDatesByAsc);
    }

}
