package tests.steps;

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
import automatization.redmine.context.Context;
import automatization.redmine.cucumber.validators.UserParametersValidator;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;
import com.google.gson.Gson;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import lombok.NonNull;

import java.util.*;

public class ApiSteps {
    RestApiClient apiClient;
    User admin;

    @Дано("Отправить POST запрос для создания пользователя \"(.+)\" и записать ответ \"(.+)\"")
    public void sendPostRequestToCreateUserAndSaveResponse(String userStashId, String responseStashId) {
        User user;
        if (Context.getStash().get(userStashId) == null) {
            user = new User() {{
                setStatus(Status.UNACCEPTED);
                setEmails(Collections.singletonList(new Email(this)));
            }};
            Context.getStash().put(userStashId, user);
        } else {
            user = Context.getStash().get(userStashId, User.class);
        }
        RestResponse response = sendRequest(user, RestMethod.POST, "/users.json");
        Context.getStash().put(responseStashId, response);
    }

    @Дано("Cтатус код равен \"(.+)\" у ответа \"(.+)\"")
    public void checkResponseStatus(Integer statusCode, String responseStashId) {
        RestResponse response = Context.getStash().get(responseStashId, RestResponse.class);
        AllureAssert.assertEquals(response.getStatusCode(), statusCode, "Статус кода ответа");
    }

    @То("Тело ответа \"(.+)\" содержит данные пользователя \"(.+)\"")
    public void checkIsResponseContainsUserInfoAndSetUserID(String responseStashId, String expectedUserStashId) {
        RestResponse response = Context.getStash().get(responseStashId, RestResponse.class);
        UserInfoDto userInfoDto = response.getPayload(UserInfoDto.class);
        UserDto actualUserDto = userInfoDto.getUser();
        User expectedUser = Context.getStash().get(expectedUserStashId, User.class);

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

        expectedUser.setId(actualUserDto.getId());
    }

    @То("В базе данных есть информация о созданном пользователе \"(.+)\" со статусом \"(.+)\"")
    public void getAndCheckUserFromDataBase(String expectedUserStashId, Integer statusCode) {
        User expectedUser = Context.getStash().get(expectedUserStashId, User.class);
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

    @То("Тело ответа \"(.+)\" содержит \"(.+)\", содержащий строки:")
    public void checkErrorsInResponse(String responseStashId, String tag, List<String> errorsText) {
        RestResponse negativeResponse = Context.getStash().get(responseStashId, RestResponse.class);
        AllureAssert.assertTrue(negativeResponse.getPayload().contains(tag), "Ответ содержит тег: " + tag);
        ErrorsInfoDto errorsFromResponse = negativeResponse.getPayload(ErrorsInfoDto.class);

        AllureAssert.assertTrue(errorsFromResponse.getErrors().size() == errorsText.size(), "Количество ошибок в ответе соответствует количеству ожидаемых ошибок");
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(0), errorsText.get(0));
        AllureAssert.assertEquals(errorsFromResponse.getErrors().get(1), errorsText.get(1));
    }

    @Если("Отправить POST запрос для создания пользователя \"(.+)\" с параметрами и записать ответ \"(.+)\"")
    public void sendPostRequestToCreateUserWithParametersAndSaveResponse(String userStashId, String responseStashId, Map<String, String> parameters) {
        UserParametersValidator.validateUserParameters(parameters.keySet());
        User user = Context.getStash().get(userStashId, User.class);
        User userWithParameters = user.read();
        if (parameters.containsKey("Пароль")) {
            String password = parameters.get("Пароль");
            userWithParameters.setPassword(password);
        }
        if (parameters.containsKey("E-Mail")) {
            String emailValue = parameters.get("E-Mail");
            Email email = new Email()
                    .setAddress(emailValue);
            List<Email> emails = Collections.singletonList(email);
            userWithParameters.setEmails(emails);
        }

        RestResponse response = sendRequest(userWithParameters, RestMethod.POST, "/users.json");
        Context.getStash().put(responseStashId, response);
    }

    private RestResponse sendRequest(@NonNull User user, RestMethod method, String uri) {
        UserInfoDto userInfoDto = new UserInfoDto(
                new UserDto()
                        .setLogin(user.getLogin())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setMail(user.getEmails().get(0).getAddress())
                        .setPassword(user.getPassword())
                        .setStatus(user.getStatus().statusCode)
        );
        String body = new Gson().toJson(userInfoDto);
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, body);
        admin = Context.getStash().get("АДМИН", User.class);
        apiClient = new RestAssuredClient(admin);
        return apiClient.execute(postRequest);
    }

    private RestResponse sendRequest(RestMethod method, String uri) {
        RestRequest postRequest = new RestAssuredRequest(method, uri, null, null, null);
        admin = Context.getStash().get("АДМИН", User.class);
        apiClient = new RestAssuredClient(admin);
        return apiClient.execute(postRequest);
    }

    @Если("Отправить PUT запрос на изменение пользователя \"(.+)\" с параметрами и записать ответ \"(.+)\"")
    public void sendPutRequestToChangeUserWithParametersAndSaveResponse(String userStashId, String responseStashId, Map<String, String> parameters) {
        UserParametersValidator.validateUserParameters(parameters.keySet());
        User user = Context.getStash().get(userStashId, User.class);
        if (parameters.containsKey("Статус")) {
            Status status = Status.getStatusFromDescription(parameters.get("Статус"));
            user.setStatus(status);
        }
        String uriWithUserId = String.format("/users/%s.json", user.getId());
        RestResponse response =  sendRequest(user, RestMethod.PUT, uriWithUserId);
        Context.getStash().put(responseStashId,response);
    }

    @То("В базе данных есть информация о пользователе \"(.+)\"")
    public void checkIsUserInDataBase(String userStashId) {
        User expectedUser = Context.getStash().get(userStashId, User.class);
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

    @Если("Отправить GET запрос на получение пользователя \"(.+)\" и записать ответ \"(.+)\"")
    public void sendGetRequestToGetUserAndSaveResponse(String userStashId, String responseStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        String uriWithUserId = String.format("/users/%s.json", user.getId());
        RestResponse response = sendRequest(RestMethod.GET, uriWithUserId);
        Context.getStash().put(responseStashId,response);
    }

    @То("Статус пользователя \"(.+)\" равен \"(.+)\"")
    public void checkUserStatus(String userStashId, String status) {
        User user = Context.getStash().get(userStashId, User.class);
        AllureAssert.assertEquals(user.getStatus(), Status.getStatusFromDescription(status));
    }

    @Если("Отправить DELETE запрос на удаление пользователя \"(.+)\" и записать ответ \"(.+)\"")
    public void sendDeleteRequestToDeleteUserAndSaveResponse(String userStashId, String responseStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        String uriWithUserId = String.format("/users/%s.json", user.getId());
        RestResponse response = sendRequest(RestMethod.DELETE, uriWithUserId);
        Context.getStash().put(responseStashId,response);
    }

    @То("В базе данных отсутствует информация о пользователе \"(.+)\"")
    public void checkThatDataBaseHaveNoInformationAboutUser(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        AllureAssert.assertNull(user.read());
    }
}