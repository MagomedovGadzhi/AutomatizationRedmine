package tests.homework.ui;

import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserCreateTest extends BaseUITest {
    private User admin;
    private User userUi;
    private User userDb;

    @BeforeMethod
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
        loginPage.login(admin);
        Assert.assertEquals(homePage.pageName.getText(), "Домашняя страница");

        topMenuPage.administration.click();
        Assert.assertEquals(administrationPage.pageName.getText(), "Администрирование");

        administrationPage.users.click();
        Assert.assertEquals(usersPage.pageName.getText(), "Пользователи");

        usersPage.newUserButton.click();
        Assert.assertEquals(newUserPage.pageName.getText(), "Пользователи » Новый пользователь");

        newUserPage.loginField.sendKeys(userUi.getLogin());
        newUserPage.firstNameField.sendKeys(userUi.getFirstName());
        newUserPage.lastNameField.sendKeys(userUi.getLastName());
        newUserPage.emailField.sendKeys(userUi.getEmails().get(0).getAddress());

        newUserPage.passwordCreationCheckbox.click();

        newUserPage.createButton.click();
        Assert.assertEquals(newUserPage.notice.getText(), "Пользователь " + userUi.getLogin() +" создан.");
        userDb = userUi.readByLogin();
        Assert.assertEquals(userDb.getLogin(), userUi.getLogin());
        Assert.assertEquals(userDb.getFirstName(), userUi.getFirstName());
        Assert.assertEquals(userDb.getLastName(), userUi.getLastName());
        Assert.assertEquals(userDb.getEmails().get(0).getAddress(), userUi.getEmails().get(0).getAddress());
    }

    @AfterClass
    public void postConditions() {
        admin.delete();
        userDb.delete();
    }
}