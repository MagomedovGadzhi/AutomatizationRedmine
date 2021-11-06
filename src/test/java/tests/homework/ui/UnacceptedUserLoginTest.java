package tests.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static automatization.redmine.ui.browser.BrowserUtils.isElementDisplayed;

public class UnacceptedUserLoginTest extends BaseUITest {

    private User unacceptedUser;

    @BeforeMethod(description = "В системе заведен пользователь. Пользователь не подтвержден администратором и не заблокирован")
    public void prepareConditions() {
        unacceptedUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "3. Авторизация неподтвержденным пользователем")
    public void negativeUnacceptedUserLoginTest() {
        loginPage.login(unacceptedUser);

        AllureAssert.assertFalse(isElementDisplayed(homePage.pageName), "Отображается наименование страницы \"Домашняя страница\"");

        AllureAssert.assertEquals(loginPage.errorFlash.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.", "Текст ошибки \"Ваша учётная запись создана и ожидает подтверждения администратора.\"");

        AllureAssert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница", "Текст элемента \"Домашняя страницы\"");
        AllureAssert.assertEquals(topMenuPage.projects.getText(), "Проекты", "Текст элемента \"Проекты\"");
        AllureAssert.assertEquals(topMenuPage.help.getText(), "Помощь", "Текст элемента \"Помощь\"");

        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.myPage), "Отображается элемент \"Моя страница\"");
        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.myAccount), "Отображается элемент \"Моя учётная запись\"");
        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.loggedAs), "Отображается элемент \"Вошли как " + unacceptedUser.getLogin() + "\"");
        AllureAssert.assertFalse(isElementDisplayed(topMenuPage.logoutButton), "Отображается элемент \"Выйти\"");


        AllureAssert.assertEquals(topMenuPage.loginButton.getText(), "Войти", "Текст элемента \"Войти\"");
        AllureAssert.assertEquals(topMenuPage.registration.getText(), "Регистрация", "Текст элемента \"Регистрация\"");
    }

    @AfterMethod(description = "Удаление тестового пользователя")
    public void postConditions() {
        unacceptedUser.delete();
    }
}