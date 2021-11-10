package tests.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.isElementDisplayed;


public class AcceptedUserLoginTest extends BaseUITest {
    private User acceptedUser;

    @BeforeMethod(description = "В системе заведен пользователь. Пользователь подтвержден администратором и не заблокирован")
    public void prepareConditions() {
        acceptedUser = new User() {{
            setStatus(Status.ACTIVE);
        }};
        acceptedUser.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "2. Авторизация подтвержденным пользователем")
    public void positiveUserLoginTest() {
        activeUserAuthorization(acceptedUser);
    }

    @Step("Авторизация подтвержденным пользователем")
    private void activeUserAuthorization (User activeUser) {
        loginPage.login(activeUser);
        AllureAssert.assertEquals(homePage.pageName.getText(), "Домашняя страница", "Наименование страницы \"Домашняя страница\"");

        AllureAssert.assertEquals(topMenuPage.loggedAs.getText(), "Вошли как " + acceptedUser.getLogin(), "Текст элемента \"Вошли как " + acceptedUser.getLogin() + "\"");

        AllureAssert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница", "Текст элемента \"Домашняя страница\"");
        AllureAssert.assertEquals(topMenuPage.myPage.getText(), "Моя страница", "Текст элемента \"Моя страница\"");
        AllureAssert.assertEquals(topMenuPage.projects.getText(), "Проекты", "Текст элемента \"Проекты\"");
        AllureAssert.assertEquals(topMenuPage.help.getText(), "Помощь", "Текст элемента \"Помощь\"");
        AllureAssert.assertEquals(topMenuPage.myAccount.getText(), "Моя учётная запись", "Текст элемента \"Моя учётная запись\"");
        AllureAssert.assertEquals(topMenuPage.logoutButton.getText(), "Выйти", "Текст элемента \"Выйти\"");

        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.loginButton), "Отображается элемент \"Войти\"");
        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.registration), "Отображается элемент \"Регистрация\"");
        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.administration), "Отображается элемент \"Администрирование\"");

        AllureAssert.assertEquals(headerPage.quickSearch.getText(), "Поиск", "Текст элемента \"Поиск\"");
        AllureAssert.assertTrue(headerPage.quickSearchInputField.isDisplayed(), "Отображается поле для ввода поискового запроса");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        acceptedUser.delete();
    }
}