package automatization.redmine.ui.pages;

import automatization.redmine.model.user.User;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//div[@id='flash_error']")
    public WebElement errorFlash;

    public LoginPage() {
        super();
    }

    public void login(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public void login(User user) {
        login(user.getLogin(), user.getPassword());
    }


}
