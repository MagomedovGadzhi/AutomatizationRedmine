package tests.testNg;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UserDto;
import automatization.redmine.api.dto.users.UserInfoDto;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class GetUserByIdTest {
    private RestApiClient client;
    private RestRequest request;
    private User createdUser;

    @BeforeMethod
    public void prepareFixtures() {
        User apiUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setIsAdmin(true);
        }}.create();

        createdUser = new User() {{
            setStatus(Status.LOCKED);
        }}.create();

        client = new RestAssuredClient(apiUser);

        String endpoint = "/users/" + createdUser.getId() + ".json";
        request = new RestAssuredRequest(RestMethod.GET, endpoint, null, null, null);
    }

    @Test
    public void getUserByIdTest() {
        RestResponse response = client.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        Assert.assertEquals(responseUser.getLastName(), createdUser.getLastName());
        Assert.assertEquals(responseUser.getFirstName(), createdUser.getFirstName());
        Assert.assertEquals(responseUser.getStatus().intValue(), Status.LOCKED.statusCode);

    }
}