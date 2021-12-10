package automatization.redmine.ui.browser;

public class BrowserManager {
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();

    public static Browser getBrowser() {
        if (browser.get() == null) {
            browser.set(new Browser());
        }
        return browser.get();
    }

    public static Browser getBrowser(String uri) {
        if (browser.get() == null) {
            browser.set(new Browser(uri));
        }
        return browser.get();
    }

    public static void closeBrowser() {
        browser.get().takeScreenshot();
        browser.get().getDriver().quit();
        browser.set(null);
    }
}