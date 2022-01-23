package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Домашняя страница")
public class HomePage extends Page {

    @ElementName("Имя страницы")
    @FindBy(xpath = "//div[@id='content']//h2[text()='Домашняя страница']")
    public WebElement pageName;
}