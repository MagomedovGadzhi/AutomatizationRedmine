package tests.homework.ui;

import automatization.redmine.model.project.Project;
import automatization.redmine.model.role.Permission;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectVisibilityByUserTest extends BaseUITest {
    private User user;
    private Role role;
    private Project publicProject1;
    private Project privateProject2;
    private Project privateProject3;


    @BeforeClass
    public void prepareConditions() {
        user = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        role = new Role() {{
            getPermissions().add(Permission.VIEW_ISSUES);
        }}.create();

        publicProject1 = new Project() {{
            setIsPublic(true);
        }}.create();

        privateProject2 = new Project() {{
            setIsPublic(false);
        }}.create();

        privateProject3 = new Project() {{
            setIsPublic(false);
        }}.create();

        user.addProject(privateProject3.getId(), role);

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "5. Видимость проектов. Пользователь")
    public void projectVisibilityByUserTest() {
        loginPage.login(user);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        topMenuPage.projects.click();
        Assert.assertEquals(projectsPage.actualTabName.getText(), "Проекты");

        List<String> projectsNames = BrowserUtils.getElementsText(projectsPage.projects);
        Assert.assertTrue(projectsNames.contains(publicProject1.getName()));
        Assert.assertFalse(projectsNames.contains(privateProject2.getName()));
        Assert.assertTrue(projectsNames.contains(privateProject3.getName()));
    }

    @AfterClass
    public void postConditions() {
        user.delete();
        role.delete();
        publicProject1.delete();
        privateProject2.delete();
        privateProject3.delete();
    }
}