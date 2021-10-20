package tests.homework.api;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.Assert;
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

    @BeforeMethod
    public void prepareFixtures() {
        notAdminUserWithApi = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create().read();

        notAdminUserWithoutApi = new User().create();
    }

    @Test
    public void getUsersByAdminTest() {
        //Шаг 1. Отправить запрос GET на получение пользователя notAdminUser, используя ключ API пользователя notAdminUser
        deleteCurrentUserByNotAdmin(notAdminUserWithApi);

        //Шаг 2. Отправить запрос GET на получение пользователя notAdminUserWithoutApi, используя ключ API пользователя notAdminUser
        deleteAnotherUserByNotAdmin(notAdminUserWithApi, notAdminUserWithoutApi);
    }

    private void deleteCurrentUserByNotAdmin(User currentUser) {
        apiClient = new RestAssuredClient(currentUser);
        uri = String.format("/users/%d.json", currentUser.getId());
        request = new RestAssuredRequest(RestMethod.DELETE, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 403);
        Assert.assertNotNull(currentUser.read());
    }

    private void deleteAnotherUserByNotAdmin(User seeker, User targetUser) {
        apiClient = new RestAssuredClient(seeker);
        uri = String.format("/users/%d.json", targetUser.getId());
        request = new RestAssuredRequest(RestMethod.DELETE, uri, null, null, null);
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 403);
        Assert.assertNotNull(targetUser.read());
    }

    @AfterClass
    private void postConditions() {
        notAdminUserWithApi.delete();
        notAdminUserWithoutApi.delete();
    }
}