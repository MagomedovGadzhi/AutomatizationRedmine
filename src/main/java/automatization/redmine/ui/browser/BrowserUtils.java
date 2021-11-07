package automatization.redmine.ui.browser;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import automatization.redmine.property.Property;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class BrowserUtils {

    public static List<String> getElementsText(List<WebElement> elements) {
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }

    @Step("Нажать на элемент {1}")
    public static void click(WebElement webElement, String description) {
        webElement.click();
    }

    @Step("Заполнение элемента \"{1}\" значением \"{2}\"")
    public static void sendKeys(WebElement webElement, String description, CharSequence... var1) {
        webElement.sendKeys(var1);
    }
}