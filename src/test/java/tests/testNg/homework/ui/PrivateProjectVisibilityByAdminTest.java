package tests.testNg.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class PrivateProjectVisibilityByAdminTest extends BaseUITest {
    private User admin;
    private Project project;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора. Существует приватный проект, не привязанный к пользователю")
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
        authorization(admin);

        goToProjectsPage();
    }

    @Step("На главной странице нажать \"Проекты\"")
    private void goToProjectsPage() {
        BrowserUtils.click(topMenuPage.projects, "\"Проекты\"");
        AllureAssert.assertEquals(projectsPage.pageName.getText(), "Проекты", "Наименование страницы \"Проекты\"");

        List<String> projectsNames = BrowserUtils.getElementsText(projectsPage.projects);
        AllureAssert.assertTrue(projectsNames.contains(project.getName()), "На странице отображается проект из предусловия");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        admin.delete();
        project.delete();
    }
}