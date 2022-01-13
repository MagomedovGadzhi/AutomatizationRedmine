package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Меню страницы")
public class TopMenuPage extends Page {
    //-----------Кнопки доступные всегда-----------
    @ElementName("Домашняя страница")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='home']")
    public WebElement homePage;

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='projects']")
    public WebElement projects;

    @ElementName("Помощь")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='help']")
    public WebElement help;

    //-----------Кнопки достпуные до авторизации-----------
    @ElementName("Войти")
    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton;

    @ElementName("Регистрация")
    @FindBy(xpath = "//div[@id='account']//a[@class='register']")
    public WebElement registration;

    //-----------Кнопки достпуные после авторизации-----------
    @ElementName("Моя учётная запись")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='my-page']")
    public WebElement myPage;

    @ElementName("Администрирование")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='administration']")
    public WebElement administration;

    @ElementName("Вошли как")
    @FindBy(xpath = "//div[@id='loggedas']")
    public WebElement loggedAs;

    @ElementName("Логин пользователя")
    @FindBy(xpath = "//div[@id='loggedas']//a[@class='user active']")
    public WebElement activeUserLogin;

    @ElementName("Моя учетная запись")
    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount;

    @ElementName("Выйти")
    @FindBy(xpath = "//div[@id='account']//a[@class='logout']")
    public WebElement logoutButton;
}