package tests.homework.api;

import automatization.redmine.allure.AllureAssert;
import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UserDto;
import automatization.redmine.api.dto.users.UserInfoDto;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class GetUsersListByNotAdminTest {

    private RestApiClient apiClient;
    private RestRequest request;
    private User notAdminUserWitApi;
    private User notAdminUserWithoutApi;
    private String uri;

    @BeforeClass(description = "1. Заведен пользователь в системе. " +
            "2. У пользователя есть доступ к API и ключ API. " +
            "3. Заведен еще один пользователь в системе.")
    public void prepareFixtures() {
        notAdminUserWitApi = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create().read();

        notAdminUserWithoutApi = new User().create();
    }

    @Test(description = "3. Получение информации о пользователе, пользователем без прав администратора")
    @Owner("Магомедов Гаджи Магомедович")
    @Severity(SeverityLevel.CRITICAL)
    public void getUsersByAdminTest() {
        getCurrentUserByNotAdmin(notAdminUserWitApi);

        getAnotherUserByNotAdmin(notAdminUserWithoutApi, notAdminUserWitApi);
    }

    @Step("1. Отправлен запрос GET на получение пользователя из п.1, используя ключ API из п.2 (получение информации о себе)")
    private void getCurrentUserByNotAdmin(User targetUser) {
        apiClient = new RestAssuredClient(targetUser);
        uri = String.format("/users/%d.json", targetUser.getId());
        request = new RestAssuredRequest(RestMethod.GET, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        AllureAssert.assertEquals(response.getStatusCode(), 200, "Статус кода ответа");

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        AllureAssert.assertEquals(responseUser.getId(), targetUser.getId(), "ID");
        AllureAssert.assertEquals(responseUser.getLogin(), targetUser.getLogin(), "Логин");
        AllureAssert.assertEquals(responseUser.getIsAdmin(), targetUser.getIsAdmin(), "Признак наличия прав администратора");
        AllureAssert.assertEquals(responseUser.getFirstName(), targetUser.getFirstName(), "Имя");
        AllureAssert.assertEquals(responseUser.getLastName(), targetUser.getLastName(), "Фамилия");
        //Пришлось добавить в ожидаемом результат withNano(0), т.к. в ответе API не передаются милисекунды
        AllureAssert.assertEquals(responseUser.getCreatedOn(), targetUser.getCreatedOn().withNano(0), "Дата и время создания");
        AllureAssert.assertEquals(responseUser.getLastLoginOn(), targetUser.getLastLoginOn(), "Дата и время последней авторизации");
        AllureAssert.assertEquals(responseUser.getApiKey(), targetUser.getTokens().get(0).getValue(), "Токен");
    }

    @Step("2. Отправлен запрос GET на получения пользователя из п.3, используя ключ API из п.2 (получение информации о другом пользователе)")
    private void getAnotherUserByNotAdmin(User targetUser, User seeker) {
        apiClient = new RestAssuredClient(seeker);
        uri = String.format("/users/%d.json", targetUser.getId());
        request = new RestAssuredRequest(RestMethod.GET, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        AllureAssert.assertEquals(response.getStatusCode(), 200, "Статус кода ответа");

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        AllureAssert.assertEquals(responseUser.getId(), targetUser.getId(), "ID");
        AllureAssert.assertEquals(responseUser.getLogin(), targetUser.getLogin(), "Логин");
        AllureAssert.assertEquals(responseUser.getFirstName(), targetUser.getFirstName(), "Имя");
        AllureAssert.assertEquals(responseUser.getLastName(), targetUser.getLastName(), "Фамилия");
        //Пришлось добавить в ожидаемом результат withNano(0), т.к. в ответе API не передаются милисекунды
        AllureAssert.assertEquals(responseUser.getCreatedOn(), targetUser.getCreatedOn().withNano(0), "Дата и время создания");
        AllureAssert.assertEquals(responseUser.getLastLoginOn(), targetUser.getLastLoginOn(), "Дата и время последней авторизации");
        AllureAssert.assertNull(responseUser.getIsAdmin());
        AllureAssert.assertNull(responseUser.getApiKey());
    }

    @AfterClass(description = "Пользователи удалены из системы")
    private void postConditions() {
        notAdminUserWitApi.delete();
        notAdminUserWithoutApi.delete();
    }
}