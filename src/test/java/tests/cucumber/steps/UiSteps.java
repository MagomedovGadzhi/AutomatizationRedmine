package tests.cucumber.steps;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.context.Context;
import automatization.redmine.cucumber.PageObjectHelper;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import automatization.redmine.ui.pages.LoginPage;
import automatization.redmine.ui.pages.TopMenuPage;
import automatization.redmine.utils.CompareUtils;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;

import java.util.List;

import static automatization.redmine.ui.pages.Page.getPage;

public class UiSteps {

    @И("Авторизоваться как пользователь \"(.+)\"")
    public void authByUser(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        getPage(LoginPage.class).login(user);
    }

    @И("Авторизоваться по логину \"(.+)\" и паролю \"(.+)\"")
    public void authByLoginAndPassword(String login, String password) {
        getPage(LoginPage.class).login(login, password);
    }

    @И("Текст элемента Моя учётная запись - \"(.*)\"")
    public void assertMyAccountText(String expectedText) {
        AllureAssert.assertEquals(getPage(TopMenuPage.class).myAccount.getText(), expectedText);
    }

    @Если("На странице {string} нажать на элемент {string}")
    public void clickOnElementOnPage(String pageName, String elementName) {
        PageObjectHelper.findElement(pageName, elementName).click();
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void sendKeysToElementOnPage(String pageName, String elementName, String charSequence) {
        PageObjectHelper.findElement(pageName, elementName).sendKeys(charSequence);
    }

    @И("На странице {string} тексты элементов {string} отсортированы по дате по убыванию")
    public void assertElementsTextsIsSortedByDateDesc(String pageName, String elementsName) {
        List<WebElement> elements = PageObjectHelper.findElements(pageName, elementsName);
        List<String> elementsTexts = BrowserUtils.getElementsText(elements);
        CompareUtils.assertListSortedByDateDesc(elementsTexts);
    }
}