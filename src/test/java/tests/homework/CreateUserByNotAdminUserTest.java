package tests.homework;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UserDto;
import automatization.redmine.api.dto.users.UserInfoDto;
import automatization.redmine.api.rest_assured.GsonProvider;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class CreateUserByNotAdminUserTest {

    private RestApiClient apiClient;
    private RestRequest request;

    @BeforeMethod
    public void prepareConditions() {
        User notAdminUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        User testUser = new User() {{
            setEmails(Collections.singletonList(new Email(this)));
        }};

        apiClient = new RestAssuredClient(notAdminUser);

        UserInfoDto dto = new UserInfoDto(
                new UserDto()
                        .setLogin(testUser.getLogin())
                        .setLastName(testUser.getLastName())
                        .setFirstName(testUser.getFirstName())
                        .setMail(testUser.getEmails().get(0).getAddress())
                        .setPassword(testUser.getPassword())
        );
        String body = GsonProvider.GSON.toJson(dto);

        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, body);
    }


    @Test
    public void createUserByNotAdminUserTest() {

        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 403);

    }
}