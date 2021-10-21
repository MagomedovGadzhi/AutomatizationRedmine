package tests.homework.api;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UserDto;
import automatization.redmine.api.dto.users.UserInfoDto;
import automatization.redmine.api.rest_assured.GsonProvider;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static automatization.redmine.utils.StringUtils.randomEmail;
import static automatization.redmine.utils.StringUtils.randomEnglishString;

public class CreateUserByNotAdminUserTest {

    private RestApiClient apiClient;
    private RestRequest request;
    private User notAdminUser;

    @BeforeClass
    public void prepareConditions() {
        notAdminUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        apiClient = new RestAssuredClient(notAdminUser);

        UserInfoDto dto = new UserInfoDto(
                new UserDto()
                        .setLogin("MGM_" + randomEnglishString(10))
                        .setLastName("MGM_" + randomEnglishString(10))
                        .setFirstName("MGM_" + randomEnglishString(5))
                        .setMail(randomEmail())
                        .setPassword("1qaz@WSX")
        );
        String body = GsonProvider.GSON.toJson(dto);

        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, body);
    }


    @Test
    public void createUserByNotAdminUserTest() {
        RestResponse response = apiClient.execute(request);
        Assert.assertEquals(response.getStatusCode(), 403);

    }

    @AfterClass
    private void postConditions() {
        notAdminUser.delete();
    }
}