package tests.homework;

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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class GetUsersListByNotAdminTest {

    private RestApiClient apiClient;
    private RestRequest request;
    User notAdminUserWitApi;
    User notAdminUserWithoutApi;
    String uri;

    @BeforeMethod
    public void prepareFixtures() {
        notAdminUserWitApi = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create().read();

        notAdminUserWithoutApi = new User().create();
    }

    @Test
    public void getUsersByAdminTest() {
        //Шаг 1. Отправить запрос GET на получение пользователя notAdminUser, используя ключ API пользователя notAdminUser
        getCurrentUserByNotAdmin(notAdminUserWitApi);

        //Шаг 2. Отправить запрос GET на получение пользователя notAdminUserWithoutApi, используя ключ API пользователя notAdminUser
        getAnotherUserByNotAdmin(notAdminUserWitApi, notAdminUserWithoutApi);
    }

    private void getCurrentUserByNotAdmin(User user) {
        apiClient = new RestAssuredClient(user);
        uri = String.format("/users/%d.json", user.getId());
        request = new RestAssuredRequest(RestMethod.GET, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        Assert.assertEquals(responseUser.getId(), user.getId());
        Assert.assertEquals(responseUser.getLogin(), user.getLogin());
        Assert.assertEquals(responseUser.getIsAdmin(), user.getIsAdmin());
        Assert.assertEquals(responseUser.getFirstName(), user.getFirstName());
        Assert.assertEquals(responseUser.getLastName(), user.getLastName());
        //Пришлось добавить в ожидаемом результат withNano(0), т.к. в ответе API не передаются милисекунды
        Assert.assertEquals(responseUser.getCreatedOn(), user.getCreatedOn().withNano(0));
        Assert.assertEquals(responseUser.getLastLoginOn(), user.getLastLoginOn());
        Assert.assertEquals(responseUser.getApiKey(), user.getTokens().get(0).getValue());
    }

    private void getAnotherUserByNotAdmin(User seeker, User targetUser) {
        apiClient = new RestAssuredClient(seeker);
        uri = String.format("/users/%d.json", targetUser.getId());
        request = new RestAssuredRequest(RestMethod.GET, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        Assert.assertEquals(responseUser.getId(), targetUser.getId());
        Assert.assertEquals(responseUser.getLogin(), targetUser.getLogin());
        Assert.assertEquals(responseUser.getFirstName(), targetUser.getFirstName());
        Assert.assertEquals(responseUser.getLastName(), targetUser.getLastName());
        //Пришлось добавить в ожидаемом результат withNano(0), т.к. в ответе API не передаются милисекунды
        Assert.assertEquals(responseUser.getCreatedOn(), targetUser.getCreatedOn().withNano(0));
        Assert.assertEquals(responseUser.getLastLoginOn(), targetUser.getLastLoginOn());
        //Проверяем, что теги admin и api_key не содержатся в ответе
        Assert.assertNull(responseUser.getIsAdmin());
        Assert.assertNull(responseUser.getApiKey());
    }

    @AfterClass
    private void postConditions() {
        notAdminUserWitApi.delete();
        notAdminUserWithoutApi.delete();
    }
}