package automatization.redmine.ui.pages;

import java.util.List;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import automatization.redmine.ui.browser.BrowserManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Пользователи")
public class UsersPage extends Page {

    @ElementName("Имя страницы")
    @FindBy(xpath = "//div[@id='content']//h2")
    public WebElement pageName;

    @ElementName("Новый пользователь")
    @FindBy(xpath = "//div[@id='content']//a[contains(@class, 'add')]")
    public WebElement newUserButton;

    @ElementName("Логины")
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='username']")
    public List<WebElement> login;

    @ElementName("Имена")
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='firstname']")
    public List<WebElement> firstName;

    @ElementName("Фамилии")
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='lastname']")
    public List<WebElement> lastName;

    @ElementName("Даты создания")
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='created_on']")
    public List<WebElement> creationDates;

    @ElementName("Создано")
    @FindBy(xpath = "//th[@title='Сортировать по \"Создано\"']/a")
    private WebElement created;

    public WebElement filterButton(String text) {
        return BrowserManager.getBrowser().getDriver().findElement(By.xpath("//table[@class='list users']/thead//th[.='" + text + "']"));
    }
}