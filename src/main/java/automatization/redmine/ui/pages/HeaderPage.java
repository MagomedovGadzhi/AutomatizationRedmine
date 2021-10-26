package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends Page {

    @FindBy(xpath = "//div[@id='quick-search']//a[@accesskey=4]")
    public WebElement quickSearch;

    @FindBy(xpath = "//div[@id='quick-search']//input[@id='q']")
    public WebElement quickSearchInputField;

    @FindBy(xpath = "//div[@id='project-jump']//span[@class='drdn-trigger']")
    public WebElement projectJump;

    public HeaderPage() {
        super();
    }
}