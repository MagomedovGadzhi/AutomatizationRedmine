package tests.homework.ui;

import automatization.redmine.model.project.Project;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class PrivateProjectVisibilityByAdminTest extends BaseUITest {
    private User admin;
    private Project project;

    @BeforeClass
    public void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        project = new Project() {{
            setIsPublic(false);
        }}.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "4. Видимость проекта. Приватный проект. Администратор")
    public void privateProjectVisibilityByAdminTest() {
        loginPage.login(admin);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        topMenuPage.projects.click();
        Assert.assertEquals(projectsPage.actualTabName.getText(), "Проекты");

        List<String> projectsNames = BrowserUtils.getElementsText(projectsPage.projects);
        Assert.assertTrue(projectsNames.contains(project.getName()));
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
        project.delete();
    }
}