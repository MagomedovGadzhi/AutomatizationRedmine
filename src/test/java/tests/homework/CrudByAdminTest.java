package tests.homework;

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

    @BeforeClass
    private void prepareConditions() {
        //Создаем админа для отравки запросов
        admin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        //Создаем тестового пользователя
        testUser = new User() {{
            setStatus(Status.UNACCEPTED);
            setEmails(Collections.singletonList(new Email(this)));
        }};
    }

    @Test(testName = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void userCrudByAdminTest() {

        //Шаг 1. Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)
        RestResponse positiveResponse = sendRequest(testUser, RestMethod.POST, "/users.json");
        //Проверяем статус-код 201
        Assert.assertEquals(positiveResponse.getStatusCode(), 201);
        //Проверяем пользователя из тела ответа
        checkUserFromJsonResponse(positiveResponse, testUser);
        //Получаем и проверяем информацию о пользователе из БД
        readAndCheckUserFromDataBase(testUser);

        //Шаг 2. Отправить запрос POST на создание пользователя повторно с тем же телом запроса
        RestResponse negativeResponse1 = sendRequest(testUser, RestMethod.POST, "/users.json");
        //Проверяем статус-код 422 и информацию об ошибках в "errors"
        Assert.assertEquals(negativeResponse1.getStatusCode(), 422);
        checkErrorsInResponse(negativeResponse1, "Email уже существует", "Пользователь уже существует");

        //Шаг 3. Отправить запрос POST на создание пользователя повторно с тем же телом запроса, при этом изменив "email" на невалидный, а "password" - на строку из 4 символов
        testUser.getEmails().get(0).setAddress("invalid_email.com");
        testUser.setPassword("1234");
        RestResponse negativeResponse2 = sendRequest(testUser, RestMethod.POST, "/users.json");
        //Проверяем статус-код 422 и информацию об ошибках в "errors"
        Assert.assertEquals(negativeResponse2.getStatusCode(), 422);
        checkErrorsInResponse(negativeResponse2, "Email имеет неверное значение", "Пользователь уже существует", "Пароль недостаточной длины (не может быть меньше 8 символа)");

        //Шаг 4. Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1
        testUser = testUser.read();
        testUser.setStatus(Status.ACTIVE);
        String uriWithUserId = String.format("/users/%d.json", testUser.getId());
        RestResponse responseFromPutRequest = sendRequest(testUser, RestMethod.PUT, uriWithUserId);
        //Проверяем статус-код 204 и сверяем информацию о пользователе с БД
        Assert.assertEquals(responseFromPutRequest.getStatusCode(), 204);
        readAndCheckUserFromDataBase(testUser);

        //Шаг 5. Отправить запрос GET на получение пользователя
        RestResponse responseFromGetRequest = sendRequest(null, RestMethod.GET, uriWithUserId);
        //Проверяем статус-код 200 и сверяем информацию о пользователе с БД
        Assert.assertEquals(responseFromGetRequest.getStatusCode(), 200);
        testUser.setStatus(Status.ACTIVE);
        checkUserFromJsonResponse(responseFromGetRequest, testUser);

        //Шаг 6. Отправить запрос DELETE на удаление пользователя
        RestResponse responseFromDeleteRequest1 = sendRequest(null, RestMethod.DELETE, uriWithUserId);
        //Проверяем статус-код 204 и проверяем удаление пользователя из БД
        Assert.assertEquals(responseFromDeleteRequest1.getStatusCode(), 204);
        Assert.assertTrue(isUserDeletedFromDataBase(testUser));

        //Шаг 7. Отправить запрос DELETE на удаление пользователя повторно
        RestResponse responseFromDeleteRequest2 = sendRequest(null, RestMethod.DELETE, uriWithUserId);
        //Проверяем статус-код 404
        Assert.assertEquals(responseFromDeleteRequest2.getStatusCode(), 404);
    }

    private RestResponse sendRequest(User expectedUser, RestMethod method, String uri) {
        UserInfoDto userApi;
        String body = null;

        if (expectedUser != null) {
            //Создаем объект UserInfoDto для преобразования в тело запроса
            userApi = new UserInfoDto(
                    new UserDto()
                            .setLogin(expectedUser.getLogin())
                            .setFirstName(expectedUser.getFirstName())
                            .setLastName(expectedUser.getLastName())
                            .setMail(expectedUser.getEmails().get(0).getAddress())
                            .setPassword(expectedUser.getPassword())
                            .setStatus(expectedUser.getStatus().statusCode)
            );
            //Сериализуем UserInfoDto в JSON, expectedUser не NULL
            body = new Gson().toJson(userApi);
        }
        //Формируем запрос
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, body);
        RestApiClient client = new RestAssuredClient(admin);
        //Отправляем запрос и возвращаем ответ
        return client.execute(postRequest);
    }

    private void checkUserFromJsonResponse(RestResponse response, User expectedUser) {
        //Десериализуем ответ
        UserInfoDto userInfoDtoFromResponse = response.getPayload(UserInfoDto.class);
        UserDto actualUserDto = userInfoDtoFromResponse.getUser();

        //Сверяем информацию о пользователе из ответного JSON
        Assert.assertNotNull(actualUserDto.getId());
        Assert.assertEquals(actualUserDto.getLogin(), expectedUser.getLogin());
        Assert.assertEquals(actualUserDto.getIsAdmin(), expectedUser.getIsAdmin());
        Assert.assertEquals(actualUserDto.getFirstName(), expectedUser.getFirstName());
        Assert.assertEquals(actualUserDto.getLastName(), expectedUser.getLastName());
        Assert.assertEquals(actualUserDto.getMail(), expectedUser.getEmails().get(0).getAddress());
        Assert.assertEquals(actualUserDto.getLastLoginOn(), expectedUser.getLastLoginOn());
        Assert.assertEquals(actualUserDto.getStatus().intValue(), expectedUser.getStatus().statusCode);
        //Если информация о пользователе, из полученного в ответе JSON, совпадает с expectedUser,
        //и у expectedUser id = NULL, то присваем ему id пользователя из JSON
        if (expectedUser.getId() == null) {
            expectedUser.setId(actualUserDto.getId());
        } else Assert.assertEquals(actualUserDto.getId(), expectedUser.getId());
    }

    private void readAndCheckUserFromDataBase(User expectedUser) {
        //Получем информацию о пользователе из БД, используя полученный идентификатор
        User userFromDataBase = expectedUser.read();

        //Сверяем информацию о пользователе из БД
        //password, createdOn, updatedOn, salt, hashedPassword, mailNotification, passwordChangedOn, tokens  не проверяем
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
        //Проверяем только адрес, т.к. нет информации об id записи
        Assert.assertEquals(userFromDataBase.getEmails().get(0).getAddress(), expectedUser.getEmails().get(0).getAddress());
    }

    private void checkErrorsInResponse(RestResponse response, String... errors) {
        ErrorsInfoDto errorsFromResponse = response.getPayload(ErrorsInfoDto.class);
        for (String error : errors) {
            if (!errorsFromResponse.getErrors().contains(error)) {
                throw new IllegalStateException("Ответ не содержит информацию об ошибках.");
            }
        }
    }

    private Boolean isUserDeletedFromDataBase(User user) {
        try {
            readAndCheckUserFromDataBase(testUser);
        } catch (IllegalArgumentException e) {
            return e.getMessage().equals("Пользователь не найден в БД.");
        }
        return false;
    }

    @AfterClass
    private void postConditions() {
        admin.delete();
    }
}