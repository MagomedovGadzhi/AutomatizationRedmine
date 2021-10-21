package tests.homework.api;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.api.dto.errors.ErrorsInfoDto;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class CrudByAdminTest {
    private User admin;
    private User testUser;
    private RestApiClient apiClient;
    private String uriWithUserId;

    @BeforeClass
    private void prepareConditions() {
        admin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        testUser = new User() {{
            setStatus(Status.UNACCEPTED);
            setEmails(Collections.singletonList(new Email(this)));
        }};

        apiClient = new RestAssuredClient(admin);
    }

    @Test(testName = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void userCrudByAdminTest() {
        //Шаг 1. Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)
        createAndValidateUser();

        //Шаг 2. Отправить запрос POST на создание пользователя повторно с тем же телом запроса
        createAndValidateDuplicateUser();

        //Шаг 3. Отправить запрос POST на создание пользователя повторно с тем же телом запроса, при этом изменив "email" на невалидный, а "password" - на строку из 4 символов
        createAndValidateDuplicateUserWithInvalidEmailAndShortPassword();

        //Шаг 4. Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1
        updateStatusAndValidateUser();

        //Шаг 5. Отправить запрос GET на получение пользователя
        getAndValidateUserWithNewStatus();

        //Шаг 6. Отправить запрос DELETE на удаление пользователя
        deleteUser();

        //Шаг 7. Отправить запрос DELETE на удаление пользователя повторно
        deleteUserAgain();
    }

    //------------------Методы по шагам теста------------------

    private void createAndValidateUser () {
        RestResponse positiveResponse = sendRequest(testUser, RestMethod.POST, "/users.json");
        Assert.assertEquals(positiveResponse.getStatusCode(), 201);
        UserDto dto = getUserDtoFromResponse(positiveResponse);
        checkUserFromJsonResponse(dto, testUser);
        testUser.setId(dto.getId());
        readAndCheckUserFromDataBase(testUser);
    }

    private void createAndValidateDuplicateUser () {
        RestResponse negativeResponse = sendRequest(testUser, RestMethod.POST, "/users.json");
        Assert.assertEquals(negativeResponse.getStatusCode(), 422);
        ErrorsInfoDto errorsFromResponse = negativeResponse.getPayload(ErrorsInfoDto.class);

        Assert.assertEquals(errorsFromResponse.getErrors().get(0), "Email уже существует");
        Assert.assertEquals(errorsFromResponse.getErrors().get(1), "Пользователь уже существует");
    }

    private void createAndValidateDuplicateUserWithInvalidEmailAndShortPassword () {
        User invalidUser = testUser.read();
        invalidUser.getEmails().get(0).setAddress("invalid_email.com");
        invalidUser.setPassword("1234");
        RestResponse negativeResponse = sendRequest(invalidUser, RestMethod.POST, "/users.json");

        Assert.assertEquals(negativeResponse.getStatusCode(), 422);

        ErrorsInfoDto errorsFromResponse = negativeResponse.getPayload(ErrorsInfoDto.class);
        Assert.assertEquals(errorsFromResponse.getErrors().get(0), "Email имеет неверное значение");
        Assert.assertEquals(errorsFromResponse.getErrors().get(1), "Пользователь уже существует");
        Assert.assertEquals(errorsFromResponse.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

    private void updateStatusAndValidateUser () {
        testUser = testUser.read();
        testUser.setStatus(Status.ACTIVE);
        uriWithUserId = String.format("/users/%d.json", testUser.getId());
        RestResponse responseFromPutRequest = sendRequest(testUser, RestMethod.PUT, uriWithUserId);
        Assert.assertEquals(responseFromPutRequest.getStatusCode(), 204);
        readAndCheckUserFromDataBase(testUser);
    }
    private void getAndValidateUserWithNewStatus () {
        RestResponse response = sendRequest(null, RestMethod.GET, uriWithUserId);
        Assert.assertEquals(response.getStatusCode(), 200);
        testUser.setStatus(Status.ACTIVE);
        UserDto dto = getUserDtoFromResponse(response);
        checkUserFromJsonResponse(dto, testUser);
    }

    private void deleteUser () {
        RestResponse responseFromDeleteRequest1 = sendRequest(null, RestMethod.DELETE, uriWithUserId);
        Assert.assertEquals(responseFromDeleteRequest1.getStatusCode(), 204);
        Assert.assertNull(testUser.read());
    }

    private void deleteUserAgain () {
        RestResponse responseFromDeleteRequest2 = sendRequest(null, RestMethod.DELETE, uriWithUserId);
        Assert.assertEquals(responseFromDeleteRequest2.getStatusCode(), 404);
    }

    //------------------Вспомогательные методы------------------

    private RestResponse sendRequest(User expectedUser, RestMethod method, String uri) {
        UserInfoDto userInfoDto;
        String body = null;

        if (expectedUser != null) {
            userInfoDto = new UserInfoDto(
                    new UserDto()
                            .setLogin(expectedUser.getLogin())
                            .setFirstName(expectedUser.getFirstName())
                            .setLastName(expectedUser.getLastName())
                            .setMail(expectedUser.getEmails().get(0).getAddress())
                            .setPassword(expectedUser.getPassword())
                            .setStatus(expectedUser.getStatus().statusCode)
            );
            body = new Gson().toJson(userInfoDto);
        }
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, body);
        return apiClient.execute(postRequest);
    }

    private void checkUserFromJsonResponse(UserDto actualUserDto, User expectedUser) {
        Assert.assertNotNull(actualUserDto.getId());
        Assert.assertEquals(actualUserDto.getLogin(), expectedUser.getLogin());
        Assert.assertEquals(actualUserDto.getIsAdmin(), expectedUser.getIsAdmin());
        Assert.assertEquals(actualUserDto.getFirstName(), expectedUser.getFirstName());
        Assert.assertEquals(actualUserDto.getLastName(), expectedUser.getLastName());
        Assert.assertEquals(actualUserDto.getMail(), expectedUser.getEmails().get(0).getAddress());
        Assert.assertEquals(actualUserDto.getLastLoginOn(), expectedUser.getLastLoginOn());
        Assert.assertEquals(actualUserDto.getStatus().intValue(), expectedUser.getStatus().statusCode);
        if (expectedUser.getId() != null) {
            Assert.assertEquals(actualUserDto.getId(), expectedUser.getId());
        }
    }

    private UserDto getUserDtoFromResponse (RestResponse response) {
        UserInfoDto userInfoDtoFromResponse = response.getPayload(UserInfoDto.class);
        return userInfoDtoFromResponse.getUser();
    }

    private void readAndCheckUserFromDataBase(User expectedUser) {
        User userFromDataBase = expectedUser.read();
        //Сверяем информацию о пользователе из БД. Password, createdOn, updatedOn, salt, hashedPassword, mailNotification, passwordChangedOn, tokens  не проверяем
        Assert.assertEquals(userFromDataBase.getId(), expectedUser.getId());
        Assert.assertEquals(userFromDataBase.getLogin(), expectedUser.getLogin());
        Assert.assertEquals(userFromDataBase.getFirstName(), expectedUser.getFirstName());
        Assert.assertEquals(userFromDataBase.getLastName(), expectedUser.getLastName());
        Assert.assertEquals(userFromDataBase.getIsAdmin(), expectedUser.getIsAdmin());
        Assert.assertEquals(userFromDataBase.getStatus(), expectedUser.getStatus());
        Assert.assertEquals(userFromDataBase.getLastLoginOn(), expectedUser.getLastLoginOn());
        Assert.assertEquals(userFromDataBase.getLanguage(), expectedUser.getLanguage());
        Assert.assertEquals(userFromDataBase.getAuthSourceId(), expectedUser.getAuthSourceId());
        Assert.assertEquals(userFromDataBase.getType(), expectedUser.getType());
        Assert.assertEquals(userFromDataBase.getIdentityUrl(), expectedUser.getIdentityUrl());
        Assert.assertEquals(userFromDataBase.getMustChangePassword(), expectedUser.getMustChangePassword());
        Assert.assertEquals(userFromDataBase.getEmails().get(0).getAddress(), expectedUser.getEmails().get(0).getAddress());
    }

    @AfterClass
    private void postConditions() {
        admin.delete();
        testUser.delete();
    }
}