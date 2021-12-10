package automatization.redmine.allure;

import org.testng.Assert;

import io.qameta.allure.Step;

public class AllureAssert {

    @Step("Проверка равенства: {2}")
    public static void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Проверка равенства: ")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("Проверка на NULL")
    public static void assertNull(Object object) {
        Assert.assertNull(object);
    }

    @Step("Проверка на НЕ NULL")
    public static void assertNotNull(Object object) {
        Assert.assertNotNull(object);
    }

    @Step("Проверка на ложность условия: {1}")
    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Step("Проверка на истинность условия: {1}")
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }
}