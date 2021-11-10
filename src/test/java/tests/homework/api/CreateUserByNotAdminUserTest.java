package tests.homework.api;

import automatization.redmine.allure.AllureAssert;
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
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static automatization.redmine.utils.StringUtils.randomEmail;
import static automatization.redmine.utils.StringUtils.randomEnglishString;

public class CreateUserByNotAdminUserTest {

    private User notAdminUser;

    @BeforeClass(description = "В системе заведен пользователь. У пользователя есть доступ к API и ключ API")
    public void prepareConditions() {
        notAdminUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();
    }


    @Test(description = "2. Создание пользователя, пользователем без прав администратора.")
    @Owner("Магомедов Гаджи Магомедович")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserByNotAdminUserTest() {
        sendPostRequestToCreateUserAndCheckResponse();
    }

    @Step("1. Отправлен запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно)")
    private void sendPostRequestToCreateUserAndCheckResponse() {
        RestApiClient apiClient = new RestAssuredClient(notAdminUser);

        UserInfoDto dto = new UserInfoDto(
                new UserDto()
                        .setLogin("MGM_" + randomEnglishString(10))
                        .setLastName("MGM_" + randomEnglishString(10))
                        .setFirstName("MGM_" + randomEnglishString(5))
                        .setMail(randomEmail())
                        .setPassword("1qaz@WSX")
        );
        String body = GsonProvider.GSON.toJson(dto);

        RestRequest request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, body);
        RestResponse response = apiClient.execute(request);
        AllureAssert.assertEquals(response.getStatusCode(), 403, "Статус кода ответа");
    }

    @AfterClass(description = "Пользователь удален из системы")
    private void postConditions() {
        notAdminUser.delete();
    }
}