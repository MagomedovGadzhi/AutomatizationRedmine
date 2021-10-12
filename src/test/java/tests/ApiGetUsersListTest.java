package tests;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UsersListDto;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class ApiGetUsersListTest {

    private RestApiClient apiClient;
    private RestRequest request;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        apiClient = new RestAssuredClient(user);

        request = new RestAssuredRequest(RestMethod.GET, "/users.json", null, null, null);
    }

    @Test
    public void getUsersByAdminTest() {

        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UsersListDto responseData = response.getPayload(UsersListDto.class);

        Assert.assertEquals(responseData.getLimit().intValue(), 25);
    }
}