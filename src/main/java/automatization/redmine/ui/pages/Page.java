package automatization.redmine.ui.pages;

import automatization.redmine.ui.browser.BrowserManager;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {

    Page() {
        PageFactory.initElements(BrowserManager.getBrowser().getDriver(), this);
    }

}
