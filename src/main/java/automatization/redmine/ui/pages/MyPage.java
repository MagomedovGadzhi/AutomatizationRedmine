package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Моя страница")
public class MyPage extends Page {
    @ElementName("Имя страницы")
    @FindBy(xpath = "//div[@id='content' and h2='Моя страница']//h2")
    public WebElement pageName;

    @ElementName("Мои задачи")
    @FindBy(xpath = "//div[@id='my-page']//a[text()='Мои задачи']")
    public WebElement myIssues;

    @ElementName("Созданные задачи")
    @FindBy(xpath = "//div[@id='my-page']//a[text()='Созданные задачи']")
    public WebElement createdIssues;

    @ElementName("Добавить")
    @FindBy(xpath = "//div[@id='content']//form[label ='Добавить']//select[@id='block-select']")
    public WebElement add;
}