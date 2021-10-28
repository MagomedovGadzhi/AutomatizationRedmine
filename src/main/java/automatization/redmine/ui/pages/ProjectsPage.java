package automatization.redmine.ui.pages;

import automatization.redmine.ui.browser.BrowserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectsPage extends Page {

    @FindBy(xpath = "//div[@id='content']/h2")
    public WebElement actualTabName;

    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'project')]")
    public WebElement projectsTab;

    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'activity')]")
    public WebElement activityTab;

    @FindBy(xpath = "//div[@id='main-menu']//a[contains(@class, 'issues')]")
    public WebElement issuesTab;

    @FindBy(xpath = "//div[@id='projects-index']//a[@class='project root parent ']")
    public List<WebElement> projects;


    public WebElement projectButton (String projectName) {
        return BrowserManager.getBrowser().getDriver().findElement(By.xpath("//div[@id='projects-index']//a[.='" + projectName + "']"));
    }
}