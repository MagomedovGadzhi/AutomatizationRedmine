package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopMenu extends Page {
    //Войти
    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton;

    //Моя учетная запись
    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount;

    //Вошли как
    @FindBy(xpath = "//div[@id='loggedas']")
    public WebElement loggedAs;

    //Логин авторизованного пользователя
    @FindBy(xpath = "//div[@id='loggedas']//a[@class='user active']")
    public WebElement activeUserLogin;

    //Выйти
    @FindBy(xpath = "//div[@id='account']//a[@class='logout']")
    public WebElement logOut;

    public TopMenu() {
        super();
    }
}