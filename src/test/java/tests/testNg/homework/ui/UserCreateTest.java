package tests.testNg.homework.ui;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserCreateTest extends BaseUITest {
    private User admin;
    private User userUi;
    private User userDb;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора")
    public void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        userUi = new User() {{
            getEmails().add(new Email(this));
        }};

        openBrowser();
        topMenuPage.loginButton.click();
    }

    @Test(description = "8. Администрирование. Создание пользователя.")
    public void userCreateTest() {
        authorization(admin);

        goToAdministrationPage();

        goToUsersPage();

        goToNewUserPage();

        fillInTheFields();

        clickPasswordCreationCheckbox();

        clickCreateButton();
    }

    @Step("Выбрать из списка меню \"Пользователи\"")
    private void goToUsersPage() {
        BrowserUtils.click(administrationPage.users, "\"Пользователи\"");
        AllureAssert.assertEquals(usersPage.pageName.getText(), "Пользователи", "Наименование страницы \"Пользователи\"");
    }

    @Step("Нажать \"Новый пользователь\"")
    private void goToNewUserPage() {
        BrowserUtils.click(usersPage.newUserButton, "\"Новый пользователь\"");
        AllureAssert.assertEquals(newUserPage.pageName.getText(), "Пользователи » Новый пользователь", "Наименование страницы \"Пользователи » Новый пользователь\"");
    }

    @Step("Заполнить поля \"Пользователь\", \"Имя\", \"Фамилия\", \"Email\" корректными значениями")
    private void fillInTheFields() {
        BrowserUtils.sendKeys(newUserPage.loginField, "Пользователь", userUi.getLogin());
        BrowserUtils.sendKeys(newUserPage.firstNameField, "Имя", userUi.getFirstName());
        BrowserUtils.sendKeys(newUserPage.lastNameField, "Фамилия", userUi.getLastName());
        BrowserUtils.sendKeys(newUserPage.emailField, "Email", userUi.getEmails().get(0).getAddress());
    }

    @Step("Установить чекбокс \"Создание пароля\"")
    private void clickPasswordCreationCheckbox() {
        BrowserUtils.click(newUserPage.passwordCreationCheckbox, "чек-бокс \"Создание пароля\"");
    }

    @Step("Нажать кнопку \"Создать\"")
    private void clickCreateButton() {
        BrowserUtils.click(newUserPage.createButton, "\"Создать\"");

        String notice = "Пользователь " + userUi.getLogin() + " создан.";
        AllureAssert.assertEquals(newUserPage.notice.getText(), notice, "Текст элемента \"" + notice + "\"");

        userDb = userUi.readByLogin();
        AllureAssert.assertEquals(userDb.getLogin(), userUi.getLogin(), "Логина пользователя");
        AllureAssert.assertEquals(userDb.getFirstName(), userUi.getFirstName(), "Имени пользователя");
        AllureAssert.assertEquals(userDb.getLastName(), userUi.getLastName(), "Фамилии пользователя");
        AllureAssert.assertEquals(userDb.getEmails().get(0).getAddress(), userUi.getEmails().get(0).getAddress(), "Email адреса пользователя");
    }

    @AfterMethod(description = "Удаление тестовых данных")
    public void postConditions() {
        admin.delete();
        userDb.delete();
    }
}