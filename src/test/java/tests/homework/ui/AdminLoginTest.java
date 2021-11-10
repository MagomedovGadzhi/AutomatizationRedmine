package tests.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.User;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.isElementDisplayed;


public class AdminLoginTest extends BaseUITest {
    private User admin;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора")
    public void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "1. Авторизация администратором")
    public void positiveAdminLoginTest() {
        adminAuthorization(admin);
    }

    @Step("Авторизация администратором")
    private void adminAuthorization(User admin) {
        authorization(admin);

        AllureAssert.assertEquals(topMenuPage.loggedAs.getText(), "Вошли как " + admin.getLogin(), "Текст элемента \"Вошли как " + admin.getLogin() + "\"");

        AllureAssert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница", "Текст элемента \"Домашняя страница\"");
        AllureAssert.assertEquals(topMenuPage.myPage.getText(), "Моя страница", "Текст элемента \"Моя страница\"");
        AllureAssert.assertEquals(topMenuPage.projects.getText(), "Проекты", "Текст элемента \"Проекты\"");
        AllureAssert.assertEquals(topMenuPage.administration.getText(), "Администрирование", "Текст элемента \"Администрирование\"");
        AllureAssert.assertEquals(topMenuPage.help.getText(), "Помощь", "Текст элемента \"Помощь\"");
        AllureAssert.assertEquals(topMenuPage.myAccount.getText(), "Моя учётная запись", "Текст элемента \"Моя учётная запись\"");
        AllureAssert.assertEquals(topMenuPage.logoutButton.getText(), "Выйти", "Текст элемента \"Выйти\"");

        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.loginButton), "Не отображается элемент \"Войти\"");
        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.registration), "Не отображается элемент \"Регистрация\"");

        AllureAssert.assertEquals(headerPage.quickSearch.getText(), "Поиск", "Текст элемента \"Поиск\"");
        AllureAssert.assertTrue(headerPage.quickSearchInputField.isDisplayed(), "Отображается поле для ввода поискового запроса");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        admin.delete();
    }
}