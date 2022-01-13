package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Новый пользователь")
public class NewUserPage extends Page {

    @ElementName("Имя страницы")
    @FindBy(xpath = "//div[@id='content']//h2")
    public WebElement pageName;

    @ElementName("Сообщение о создании пользователя")
    @FindBy(xpath = "//div[@id='content']//div[@id='flash_notice']")
    public WebElement notice;

    @ElementName("Пользователь")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_login']")
    public WebElement loginField;

    @ElementName("Имя")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_firstname']")
    public WebElement firstNameField;

    @ElementName("Фамилия")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_lastname']")
    public WebElement lastNameField;

    @ElementName("Email")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_mail']")
    public WebElement emailField;

    @ElementName("Создание пароля")
    @FindBy(xpath = "//div[@id='password_fields']//input[@id='user_generate_password']")
    public WebElement passwordCreationCheckbox;

    @ElementName("Создать")
    @FindBy(xpath = "//div[@id='content']//input[@value='Создать']")
    public WebElement createButton;
}