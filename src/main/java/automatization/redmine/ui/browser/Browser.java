package automatization.redmine.ui.browser;

import automatization.redmine.property.Property;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

@Getter
public class Browser {

    private WebDriver driver;
    private WebDriverWait wait;

    Browser() {
        this("");
    }

    Browser(String uri) {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        int timeout = Property.getIntegerProperty("element.timeout");
        driver.manage().timeouts().implicitlyWait(timeout, SECONDS);
        wait = new WebDriverWait(driver, timeout);
        get(uri);
    }

    public void get(String uri) {
        getDriver().get(Property.getStringProperty("url") + uri);
    }

    public void refresh() {
        getDriver().navigate().refresh();
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void executeJavascript(String js, Object... args) {
        ((JavascriptExecutor) driver).executeScript(js, args);
    }

    public Actions actions() {
        return new Actions(driver);
    }
}