package tests.cucumber.steps;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.context.Context;
import automatization.redmine.cucumber.PageObjectHelper;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import automatization.redmine.ui.pages.LoginPage;
import automatization.redmine.ui.pages.Page;
import automatization.redmine.ui.pages.TopMenuPage;
import automatization.redmine.utils.CompareUtils;
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

    @И("На странице {string} отображается элемент {string}")
    public void isElementDisplayed(String pageName, String elementName) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        AllureAssert.assertTrue(BrowserUtils.isElementDisplayed(webElement), "Элемент отображается");
    }

    @И("На странице {string} отображается элемент {string} с текстом {string}")
    public void isElementWithTextDisplayed(String pageName, String elementName, String text) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        AllureAssert.assertTrue(BrowserUtils.isElementDisplayed(webElement), "Элемент отображается");
        AllureAssert.assertEquals(webElement.getText(), text);
    }

    @И("На странице {string} не отображается элемент {string}")
    public void isElementNotDisplayed(String pageName, String elementName) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        AllureAssert.assertFalse(BrowserUtils.isElementDisplayed(webElement), "Элемент не отображается");
    }

    @И("На странице {string} нажать на элемент {string}")
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

    @И("На странице {string} текст элемента {string} равен {string}")
    public void assertElementText(String pageName, String elementName, String text) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        AllureAssert.assertEquals(getPage(TopMenuPage.class).myAccount.getText(), text);
    }

    @И("В спике проектов отображается проект {string}")
    public void doesProjectListContainProject(String projectStashId) {
        List<WebElement> projects = PageObjectHelper.findElements("Проекты", "Список проектов");
        List<String> projectsNames = BrowserUtils.getElementsText(projects);
        Project project = Context.getStash().get(projectStashId, Project.class);
        AllureAssert.assertTrue(projectsNames.contains(project.getName()), "Проект отображается");
    }

    @И("В спике проектов не отображается проект {string}")
    public void doesProjectListNotContainProject(String projectStashId) {
        List<WebElement> projects = PageObjectHelper.findElements("Проекты", "Список проектов");
        List<String> projectsNames = BrowserUtils.getElementsText(projects);
        Project project = Context.getStash().get(projectStashId, Project.class);
        AllureAssert.assertFalse(projectsNames.contains(project.getName()), "Проект не отображается");
    }
}