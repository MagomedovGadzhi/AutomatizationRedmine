package tests.homework;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.users.UserDto;
import automatization.redmine.api.dto.users.UserInfoDto;
import automatization.redmine.api.rest_assured.RestAssuredClient;
import automatization.redmine.api.rest_assured.RestAssuredRequest;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class CRUDByAdminTest {
    User admin;

    @BeforeClass
    public void prepareConditions () {
        admin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();
    }

    @Test
    public void mainTest () {
        sendPostRequestToCreateUser();
    }

    @Test
    private void sendPostRequestToCreateUser () {
        User expectedUser = new User() {{
            setStatus(Status.UNACCEPTED);
            setEmails(Collections.singletonList(new Email(this)));
        }};

        UserInfoDto userApi = new UserInfoDto(
                new UserDto()
                        .setLogin(expectedUser.getLogin())
                        .setFirstName(expectedUser.getFirstName())
                        .setLastName(expectedUser.getLastName())
                        .setMail(expectedUser.getEmails().get(0).getAddress())
                        .setPassword(expectedUser.getPassword())
                        .setStatus(2)
        );
        String body = new Gson().toJson(userApi);

        RestRequest postRequest = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, body);
        RestApiClient client = new RestAssuredClient(admin);
        RestResponse response = client.execute(postRequest);

        Assert.assertEquals(response.getStatusCode(), 201);
        UserInfoDto responseUserInfo = response.getPayload(UserInfoDto.class);
        UserDto actualUserDto = responseUserInfo.getUser();

        Assert.assertNotNull(actualUserDto.getId());        // Проверяем, что был возвращен id, далее сверям остальные поля
        Assert.assertEquals(actualUserDto.getLogin(), expectedUser.getLogin());
        Assert.assertEquals(actualUserDto.getIsAdmin(), expectedUser.getIsAdmin());
        Assert.assertEquals(actualUserDto.getFirstName(), expectedUser.getFirstName());
        Assert.assertEquals(actualUserDto.getLastName(), expectedUser.getLastName());
        Assert.assertEquals(actualUserDto.getMail(), expectedUser.getEmails().get(0).getAddress());
        Assert.assertEquals(actualUserDto.getLastLoginOn(), expectedUser.getLastLoginOn());
        Assert.assertEquals(actualUserDto.getStatus().intValue(), expectedUser.getStatus().statusCode);
        expectedUser.delete();
        admin.delete();
    }
}