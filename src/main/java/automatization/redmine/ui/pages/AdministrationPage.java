package automatization.redmine.ui.pages;

import automatization.redmine.cucumber.ElementName;
import automatization.redmine.cucumber.PageName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Администрирование")
public class AdministrationPage extends Page {
    @FindBy(xpath = "//div[@id='content']//h2")
    public WebElement pageName;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'projects')]")
    public WebElement projects;

    @ElementName("Пользователи")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'users')]")
    public WebElement users;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'groups')]")
    public WebElement groups;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'roles')]")
    public WebElement roles;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'trackers')]")
    public WebElement trackers;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'issue-statuses')]")
    public WebElement issueStatuses;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'workflows')]")
    public WebElement workflows;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'custom-fields')]")
    public WebElement customFields;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'enumerations')]")
    public WebElement enumerations;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'settings')]")
    public WebElement settings;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'ldap-authentication')]")
    public WebElement ldapAuthentication;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'plugins')]")
    public WebElement plugins;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'info')]")
    public WebElement information;
}