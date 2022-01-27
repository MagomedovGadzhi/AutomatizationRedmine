package tests.cucumber.steps;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.context.Context;
import automatization.redmine.cucumber.PageObjectHelper;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.user.User;
import automatization.redmine.ui.browser.BrowserUtils;
import automatization.redmine.ui.pages.LoginPage;
import automatization.redmine.ui.pages.UsersPage;
import automatization.redmine.utils.CompareUtils;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.NotFoundException;
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

    @Если("На странице {string} в шапке таблицы нажать на элемент {string}")
    public void clickOnUsersTableElementOnPage(String pageName, String elementName) {
        UsersPage users = (UsersPage) PageObjectHelper.getPage(pageName);
        users.filterButton(elementName).click();
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

    @И("На странице {string} тексты элементов {string} отсортированы по убыванию")
    public void assertElementsTextsIsSortedByNameDesc(String pageName, String elementsName) {
        List<WebElement> elements = PageObjectHelper.findElements(pageName, elementsName);
        List<String> elementsTexts = BrowserUtils.getElementsText(elements);
        CompareUtils.assertListSortedByNameDesc(elementsTexts);
    }

    @И("На странице {string} тексты элементов {string} отсортированы по возрастанию")
    public void assertElementsTextsIsSortedByNameAsc(String pageName, String elementsName) {
        List<WebElement> elements = PageObjectHelper.findElements(pageName, elementsName);
        List<String> elementsTexts = BrowserUtils.getElementsText(elements);
        CompareUtils.assertListSortedByNameAsc(elementsTexts);
    }

    @И("На странице {string} тексты элементов {string} не отсортированы")
    public void assertElementsTextsIsNotSorted(String pageName, String elementsName) {
        List<WebElement> elements = PageObjectHelper.findElements(pageName, elementsName);
        List<String> elementsTexts = BrowserUtils.getElementsText(elements);
        CompareUtils.assertIsNotSorted(elementsTexts);
    }

    @И("На странице {string} текст элемента {string} равен {string}")
    public void assertElementText(String pageName, String elementName, String text) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        AllureAssert.assertEquals(webElement.getText(), text);
    }

    @И("В спике проектов отображается проект {string}")
    public void assertProjectListContainProject(String projectStashId) {
        List<WebElement> projects = PageObjectHelper.findElements("Проекты", "Список проектов");
        List<String> projectsNames = BrowserUtils.getElementsText(projects);
        Project project = Context.getStash().get(projectStashId, Project.class);
        AllureAssert.assertTrue(projectsNames.contains(project.getName()), "Проект отображается");
    }

    @И("В спике проектов не отображается проект {string}")
    public void assertProjectListNotContainProject(String projectStashId) {
        List<WebElement> projects = PageObjectHelper.findElements("Проекты", "Список проектов");
        List<String> projectsNames = BrowserUtils.getElementsText(projects);
        Project project = Context.getStash().get(projectStashId, Project.class);
        AllureAssert.assertFalse(projectsNames.contains(project.getName()), "Проект не отображается");
    }


    @И("На странице {string} в поле {string} ввести данные пользователя {string}")
    public void sendUsersInformationToElementOnPage(String pageName, String elementName, String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);
        if (elementName.equals("Пользователь")) {
            webElement.sendKeys(user.getLogin());
        } else if (elementName.equals("Имя")) {
            webElement.sendKeys(user.getFirstName());
        } else if (elementName.equals("Фамилия")) {
            webElement.sendKeys(user.getLastName());
        } else if (elementName.equals("Email")) {
            webElement.sendKeys(user.getEmails().get(0).getAddress());
        } else {
            throw new NotFoundException("Не найдены реквизиты пользователя");
        }
    }

    @Тогда("Сообщение о создании пользователя содержит логин пользователя {string}")
    public void assertMessageContainUserLogin(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        WebElement webElement = PageObjectHelper.findElement("Новый пользователь", "Сообщение о создании пользователя");
        String notice = "Пользователь " + user.getLogin() + " создан.";
        AllureAssert.assertEquals(webElement.getText(), notice);
    }

    @И("В БД в таблице пользователи есть запись о {string}")
    public void assertUserInfoInDataBase(String userStashId) {
        User expectedUser = Context.getStash().get(userStashId, User.class);
        User actualUser = expectedUser.readByLogin();
        AllureAssert.assertEquals(actualUser.getLogin(), expectedUser.getLogin());
        AllureAssert.assertEquals(actualUser.getFirstName(), expectedUser.getFirstName());
        AllureAssert.assertEquals(actualUser.getLastName(), expectedUser.getLastName());
    }
}