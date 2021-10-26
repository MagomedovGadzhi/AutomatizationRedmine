package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AdministrationPage extends Page {

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'users')]")
    public WebElement users;

}