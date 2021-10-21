package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {
    @FindBy(xpath = "//div[@id='content']//h2[text()='Домашняя страница']")
    public WebElement homePage;

    public HomePage() {
        super();
    }
}