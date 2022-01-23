package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import automatization.redmine.ui.browser.BrowserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Проекты")
public class ProjectsPage extends Page {

    @ElementName("Имя страницы")
    @FindBy(xpath = "//div[@id='content']/h2")
    public WebElement pageName;

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'project')]")
    public WebElement projectsTab;

    @ElementName("Действия")
    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'activity')]")
    public WebElement activityTab;

    @ElementName("Задачи")
    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'issues')]")
    public WebElement issuesTab;

    @ElementName("Список проектов")
    @FindBy(xpath = "//div[@id='projects-index']//li[@class='root']//a")
    public List<WebElement> projects;

    public WebElement projectButton(String projectName) {
        return BrowserManager.getBrowser().getDriver().findElement(By.xpath("//div[@id='projects-index']//a[.='" + projectName + "']"));
    }
}