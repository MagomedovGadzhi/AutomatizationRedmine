package tests.testNg.homework.api;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class DeleteUsersListByNotAdminTest {

    private RestApiClient apiClient;
    private RestRequest request;
    private User notAdminUserWithApi;
    private User notAdminUserWithoutApi;
    private String uri;

    @BeforeMethod(description = "1. Заведен пользователь в системе. " +
            "2. У пользователя есть доступ к API и ключ API. " +
            "3. Заведен еще один пользователь в системе.")
    public void prepareFixtures() {
        notAdminUserWithApi = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create().read();

        notAdminUserWithoutApi = new User().create();
    }

    @Test(description = "4. Проверка удаления другого пользователя и себя, пользователем без прав администратора")
    @Owner("Магомедов Гаджи Магомедович")
    @Severity(SeverityLevel.CRITICAL)
    public void getUsersByAdminTest() {
        deleteCurrentUserByNotAdmin(notAdminUserWithApi);

        deleteAnotherUserByNotAdmin(notAdminUserWithoutApi, notAdminUserWithApi);
    }

    @Step("1. Отправлен запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)")
    private void deleteCurrentUserByNotAdmin(User currentUser) {
        apiClient = new RestAssuredClient(currentUser);
        uri = String.format("/users/%d.json", currentUser.getId());
        request = new RestAssuredRequest(RestMethod.DELETE, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        AllureAssert.assertEquals(response.getStatusCode(), 403, "Статус код ответа");
        AllureAssert.assertNotNull(currentUser.read());
    }

    @Step("2. Отправлен запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)")
    private void deleteAnotherUserByNotAdmin(User targetUser, User seeker) {
        apiClient = new RestAssuredClient(seeker);
        uri = String.format("/users/%d.json", targetUser.getId());
        request = new RestAssuredRequest(RestMethod.DELETE, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        AllureAssert.assertEquals(response.getStatusCode(), 403, "Статус код ответа");
        AllureAssert.assertNotNull(targetUser.read());
    }

    @AfterClass(description = "Пользователи удалены из системы")
    private void postConditions() {
        notAdminUserWithApi.delete();
        notAdminUserWithoutApi.delete();
    }
}