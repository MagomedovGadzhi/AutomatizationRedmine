package tests.homework.api;

import automatization.redmine.allure.AllureAssert;
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
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import lombok.NonNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class CrudByAdminTest {
    private User admin;
    private User testUser;
    private RestApiClient apiClient;
    private String uriWithUserId;

    @BeforeClass(description = "В системе заведен пользователь с правами администратора. У пользователя есть доступ к API и ключ API")
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

    @Test(description = "1. Создание, изменение, получение, удаление пользователя администратором системы")
    @Owner("Магомедов Гаджи Магомедович")
    @Severity(SeverityLevel.CRITICAL)
    public void userCrudByAdminTest() {
        createAndValidateUser();

        createAndValidateDuplicateUser();

        createAndValidateDuplicateUserWithInvalidEmailAndShortPassword();

        updateStatusAndValidateUser();

        getAndValidateUserWithNewStatus();

        deleteUser();

        deleteUserAgain();
    }

    //------------------Методы по шагам теста------------------
    @Step("1. Отправлен запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    private void createAndValidateUser() {
        RestResponse positiveResponse = sendRequest(testUser, RestMethod.POST, "/users.json");
        AllureAssert.assertEquals(positiveResponse.getStatusCode(), 201, "Статус кода ответа");
        UserDto dto = getUserDtoFromResponse(positiveResponse);
        checkUserFromJsonResponse(dto, testUser);
        testUser.setId(dto.getId());
        readAndCheckUserFromDataBase(testUser);
    }

    @Step("2. Отправлен запрос POST на создание пользователя повторно с тем же телом запроса")
    private void createAndValidateDuplicateUser() {
        RestResponse negativeResponse = sendRequest(testUser, RestMethod.POST, "/users.json");
        AllureAssert.assertEquals(negativeResponse.getStatusCode(), 422, "Статус кода ответа");
        ErrorsInfoDto errorsFromResponse = negativeResponse.getPayload(ErrorsInfoDto.class);

        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(0), "Email уже существует", "Текст ошибки \"Email уже существует\"");
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(1), "Пользователь уже существует", "Текст ошибки \"Пользователь уже существует\"");
    }

    @Step("3. Отправлен запрос POST на создание пользователя повторно с тем же телом запроса, при этом изменив \"email\" на невалидный, а \"password\" - на строку из 4 символов")
    private void createAndValidateDuplicateUserWithInvalidEmailAndShortPassword() {
        User invalidUser = testUser.read();
        invalidUser.getEmails().get(0).setAddress("invalid_email.com");
        invalidUser.setPassword("1234");
        RestResponse negativeResponse = sendRequest(invalidUser, RestMethod.POST, "/users.json");

        AllureAssert.assertEquals(negativeResponse.getStatusCode(), 422, "Статус кода ответа");

        ErrorsInfoDto errorsFromResponse = negativeResponse.getPayload(ErrorsInfoDto.class);
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(0), "Email имеет неверное значение", "Текст ошибки \"Email имеет неверное значение\"");
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(1), "Пользователь уже существует", "Текст ошибки \"Пользователь уже существует\"");
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)", "Текст ошибки \"Пароль недостаточной длины (не может быть меньше 8 символа)\"");
    }

    @Step("4. Отправлен запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1")
    private void updateStatusAndValidateUser() {
        testUser = testUser.read();
        testUser.setStatus(Status.ACTIVE);
        uriWithUserId = String.format("/users/%d.json", testUser.getId());
        RestResponse responseFromPutRequest = sendRequest(testUser, RestMethod.PUT, uriWithUserId);
        AllureAssert.assertEquals(responseFromPutRequest.getStatusCode(), 204, "Статус кода ответа");
        readAndCheckUserFromDataBase(testUser);
    }

    @Step("5. Отправлен запрос GET на получение пользователя")
    private void getAndValidateUserWithNewStatus() {
        RestResponse response = sendRequest(RestMethod.GET, uriWithUserId);
        AllureAssert.assertEquals(response.getStatusCode(), 200, "Статус кода ответа");
        testUser.setStatus(Status.ACTIVE);
        UserDto dto = getUserDtoFromResponse(response);
        checkUserFromJsonResponse(dto, testUser);
    }

    @Step("6. Отправлен запрос DELETE на удаление пользователя")
    private void deleteUser() {
        RestResponse responseFromDeleteRequest1 = sendRequest(RestMethod.DELETE, uriWithUserId);
        AllureAssert.assertEquals(responseFromDeleteRequest1.getStatusCode(), 204, "Статус кода ответа");
        AllureAssert.assertNull(testUser.read());
    }

    @Step("7. Отправлен запрос DELETE на удаление пользователя повторно")
    private void deleteUserAgain() {
        RestResponse responseFromDeleteRequest2 = sendRequest(RestMethod.DELETE, uriWithUserId);
        AllureAssert.assertEquals(responseFromDeleteRequest2.getStatusCode(), 404, "Статус кода ответа");
    }

    private RestResponse sendRequest(@NonNull User expectedUser, RestMethod method, String uri) {
        UserInfoDto userInfoDto = new UserInfoDto(
                new UserDto()
                        .setLogin(expectedUser.getLogin())
                        .setFirstName(expectedUser.getFirstName())
                        .setLastName(expectedUser.getLastName())
                        .setMail(expectedUser.getEmails().get(0).getAddress())
                        .setPassword(expectedUser.getPassword())
                        .setStatus(expectedUser.getStatus().statusCode)
        );
        String body = new Gson().toJson(userInfoDto);
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, body);
        return apiClient.execute(postRequest);
    }

    private RestResponse sendRequest(RestMethod method, String uri) {
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, null);
        return apiClient.execute(postRequest);
    }

    private void checkUserFromJsonResponse(UserDto actualUserDto, User expectedUser) {
        AllureAssert.assertNotNull(actualUserDto.getId());
        AllureAssert.assertEquals(actualUserDto.getLogin(), expectedUser.getLogin(), "Логин");
        AllureAssert.assertEquals(actualUserDto.getIsAdmin(), expectedUser.getIsAdmin(), "Признак наличия прав администратора");
        AllureAssert.assertEquals(actualUserDto.getFirstName(), expectedUser.getFirstName(), "Имя");
        AllureAssert.assertEquals(actualUserDto.getLastName(), expectedUser.getLastName(), "Фамилия");
        AllureAssert.assertEquals(actualUserDto.getMail(), expectedUser.getEmails().get(0).getAddress(), "Email адрес");
        AllureAssert.assertEquals(actualUserDto.getLastLoginOn(), expectedUser.getLastLoginOn(), "Дата и время последней авторизации");
        AllureAssert.assertEquals(actualUserDto.getStatus(), expectedUser.getStatus().statusCode, "Статус");
        if (expectedUser.getId() != null) {
            AllureAssert.assertEquals(actualUserDto.getId(), expectedUser.getId(), "ID");
        }
    }

    private UserDto getUserDtoFromResponse(RestResponse response) {
        UserInfoDto userInfoDtoFromResponse = response.getPayload(UserInfoDto.class);
        return userInfoDtoFromResponse.getUser();
    }

    private void readAndCheckUserFromDataBase(User expectedUser) {
        User userFromDataBase = expectedUser.read();
        //Сверяем информацию о пользователе из БД. Password, createdOn, updatedOn, salt, hashedPassword, mailNotification, passwordChangedOn, tokens  не проверяем
        AllureAssert.assertEquals(userFromDataBase.getId(), expectedUser.getId(), "ID");
        AllureAssert.assertEquals(userFromDataBase.getLogin(), expectedUser.getLogin(), "Логин");
        AllureAssert.assertEquals(userFromDataBase.getFirstName(), expectedUser.getFirstName(), "Имя");
        AllureAssert.assertEquals(userFromDataBase.getLastName(), expectedUser.getLastName(), "Фамилия");
        AllureAssert.assertEquals(userFromDataBase.getIsAdmin(), expectedUser.getIsAdmin(), "Признак наличия прав администратора");
        AllureAssert.assertEquals(userFromDataBase.getStatus(), expectedUser.getStatus(), "Статус");
        AllureAssert.assertEquals(userFromDataBase.getLastLoginOn(), expectedUser.getLastLoginOn(), "Дата и время последней авторизации");
        AllureAssert.assertEquals(userFromDataBase.getLanguage(), expectedUser.getLanguage(), "Язык");
        AllureAssert.assertEquals(userFromDataBase.getAuthSourceId(), expectedUser.getAuthSourceId(), "AuthSourceId");
        AllureAssert.assertEquals(userFromDataBase.getType(), expectedUser.getType(), "Тип пользователя");
        AllureAssert.assertEquals(userFromDataBase.getIdentityUrl(), expectedUser.getIdentityUrl(), "IdentityUrl");
        AllureAssert.assertEquals(userFromDataBase.getMustChangePassword(), expectedUser.getMustChangePassword(), "Признак необходимости изменения пароля при следующем входе");
        AllureAssert.assertEquals(userFromDataBase.getEmails().get(0).getAddress(), expectedUser.getEmails().get(0).getAddress(), "Email адрес");
    }

    @AfterClass(description = "Пользователи удалены из системы")
    private void postConditions() {
        admin.delete();
        testUser.delete();
    }
}