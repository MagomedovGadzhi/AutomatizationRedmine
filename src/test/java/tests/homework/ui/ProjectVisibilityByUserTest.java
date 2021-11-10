package tests.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.role.Permission;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectVisibilityByUserTest extends BaseUITest {
    private User user;
    private Role role;
    private Project publicProject1;
    private Project privateProject2;
    private Project privateProject3;


    @BeforeMethod(description = "1. Заведен пользователь в системе.\n" +
            "2. Пользователь подтвержден администратором и не заблокирован\n" +
            "3. В системе заведена Роль пользователя с правами на просмотр задач\n" +
            "4. В системе заведен публичный проект (№ 1)\n" +
            "5. В системе заведен приватный проект (№ 2)\n" +
            "6. В системе заведен приватный проект (№ 3)\n" +
            "7. У пользователя нет доступа к проектам №1, №2\n" +
            "8. У пользователя есть доступ к проекту №3 c ролью из п.3 предусловия")
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
        authorization(user);

        goToProjectsPage();
    }

    @Step("На главной странице нажать \"Проекты\"")
    private void goToProjectsPage() {
        BrowserUtils.click(topMenuPage.projects, "\"Проекты\"");
        AllureAssert.assertEquals(projectsPage.pageName.getText(), "Проекты", "Текст элемента \"Проекты\"");

        List<String> projectsNames = BrowserUtils.getElementsText(projectsPage.projects);
        AllureAssert.assertTrue(projectsNames.contains(publicProject1.getName()), "Отображается проект из п.4 предусловия");
        AllureAssert.assertTrue(!projectsNames.contains(privateProject2.getName()), "Не отображается проект из п.5 предусловия");
        AllureAssert.assertTrue(projectsNames.contains(privateProject3.getName()), "Отображается проект из п.6 предусловия");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        user.delete();
        role.delete();
        publicProject1.delete();
        privateProject2.delete();
        privateProject3.delete();
    }
}