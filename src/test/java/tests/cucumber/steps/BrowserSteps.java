package tests.cucumber.steps;

import automatization.redmine.ui.browser.BrowserManager;
import cucumber.api.java.ru.Дано;

public class BrowserSteps {

    @Дано("Открыт браузер на странице \"(.+)\"")
    public void openBrowserOnPage(String url) {
        BrowserManager.getBrowser(url);
    }

    @Дано("Открыт браузер на главной странице")
    public void openBrowserOnMainPage() {
        BrowserManager.getBrowser();
    }

}
