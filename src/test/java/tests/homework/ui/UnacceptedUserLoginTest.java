package tests.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import io.qameta.allure.Step;
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
        unacceptedUserAuthorization(unacceptedUser);
    }

    @Step("1. Авторизация неподтвержденным пользователем")
    private void unacceptedUserAuthorization(User user) {
        loginPage.login(user);

        AllureAssert.assertTrue(!isElementDisplayed(homePage.pageName), "Не отображается наименование страницы \"Домашняя страница\"");

        AllureAssert.assertEquals(loginPage.errorFlash.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.", "Текст ошибки \"Ваша учётная запись создана и ожидает подтверждения администратора.\"");

        AllureAssert.assertEquals(topMenuPage.homePage.getText(), "Домашняя страница", "Текст элемента \"Домашняя страница\"");
        AllureAssert.assertEquals(topMenuPage.projects.getText(), "Проекты", "Текст элемента \"Проекты\"");
        AllureAssert.assertEquals(topMenuPage.help.getText(), "Помощь", "Текст элемента \"Помощь\"");

        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.myPage), "Не отображается элемент \"Моя страница\"");
        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.myAccount), "Не отображается элемент \"Моя учётная запись\"");
        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.loggedAs), "Не отображается элемент \"Вошли как " + unacceptedUser.getLogin() + "\"");
        AllureAssert.assertTrue(!isElementDisplayed(topMenuPage.logoutButton), "Не отображается элемент \"Выйти\"");


        AllureAssert.assertEquals(topMenuPage.loginButton.getText(), "Войти", "Текст элемента \"Войти\"");
        AllureAssert.assertEquals(topMenuPage.registration.getText(), "Регистрация", "Текст элемента \"Регистрация\"");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        unacceptedUser.delete();
    }
}