package automatization.redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class NewUserPage extends Page {

    @FindBy(xpath = "//div[@id='content']//h2")
    public WebElement pageName;

    @FindBy(xpath = "//div[@id='content']//div[@id='flash_notice']")
    public WebElement notice;

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_login']")
    public WebElement loginField;

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_firstname']")
    public WebElement firstNameField;

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_lastname']")
    public WebElement lastNameField;

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_mail']")
    public WebElement emailField;

    @FindBy(xpath = "//div[@id='password_fields']//input[@id='user_generate_password']")
    public WebElement passwordCreationCheckbox;

    @FindBy(xpath = "//div[@id='content']//input[@value='Создать']")
    public WebElement createButton;
}