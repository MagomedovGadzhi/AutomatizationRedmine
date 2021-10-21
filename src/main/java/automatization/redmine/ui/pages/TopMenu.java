package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopMenu extends Page {
    //-----------Кнопки доступные всегда-----------
    //Домашняя страница
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='home']")
    public WebElement homePage;

    //Проекты
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='projects']")
    public WebElement projects;

    //Помощь
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='help']")
    public WebElement help;


    //-----------Кнопки достпуные до авторизации-----------
    //Войти
    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton;

    //Регистрация
    @FindBy(xpath = "//div[@id='account']//a[@class='register']")
    public WebElement registration;

    //-----------Кнопки достпуные после авторизации-----------
    //Моя страница
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='my-page']")
    public WebElement myPage;

    //Администрирование
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='administration']")
    public WebElement administration;

    //Вошли как
    @FindBy(xpath = "//div[@id='loggedas']")
    public WebElement loggedAs;

    //Логин авторизованного пользователя
    @FindBy(xpath = "//div[@id='loggedas']//a[@class='user active']")
    public WebElement activeUserLogin;

    //Моя учетная запись
    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount;

    //Выйти
    @FindBy(xpath = "//div[@id='account']//a[@class='logout']")
    public WebElement logoutButton;

    public TopMenu() {
        super();
    }
}