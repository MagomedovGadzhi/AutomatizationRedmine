package automatization.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyPage extends Page {
    @FindBy(xpath = "//div[@id='content' and h2='Моя страница']//h2")
    public WebElement myPage;

    @FindBy(xpath = "//div[@id='my-page']//a[text()='Мои задачи']")
    public WebElement myIssues;

    @FindBy(xpath = "//div[@id='my-page']//a[text()='Созданные задачи']")
    public WebElement createdIssues;

    @FindBy(xpath = "//div[@id='content']//form[label ='Добавить']//select[@id='block-select']")
    public WebElement add;

    public MyPage() {
        super();
    }
}
