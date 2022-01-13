package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Заголовок страницы")
public class HeaderPage extends Page {

    @ElementName("Поиск")
    @FindBy(xpath = "//div[@id='quick-search']//a[@accesskey=4]")
    public WebElement quickSearch;

    @ElementName("Поисковое поле для ввода текста")
    @FindBy(xpath = "//div[@id='quick-search']//input[@id='q']")
    public WebElement quickSearchInputField;

    @ElementName("Перейти к проекту")
    @FindBy(xpath = "//div[@id='project-jump']//span[@class='drdn-trigger']")
    public WebElement projectJump;
}