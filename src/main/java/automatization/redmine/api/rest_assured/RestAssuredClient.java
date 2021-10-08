package automatization.redmine.api.rest_assured;

import automatization.redmine.api.client.RestApiClient;
import automatization.redmine.api.client.RestMethod;
import automatization.redmine.api.client.RestRequest;
import automatization.redmine.api.client.RestResponse;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import automatization.redmine.property.Property;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient implements RestApiClient {

    protected RequestSpecification specification;

    public RestAssuredClient() {
        this.specification = given()
                .baseUri(Property.getStringProperty("url"))
                .contentType(ContentType.JSON);
    }

    public RestAssuredClient(User user) {
        this();
        String token = user.getTokens().stream()
                .filter(tkn -> tkn.getAction() == Token.TokenType.API)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("У пользователя нет API-токена"))
                .getValue();
        specification.header("X-Redmine-API-Key", token);
    }

    @Override
    public RestResponse execute(RestRequest request) {
        RequestSpecification spec = given(specification)
                .queryParams(request.getQueryParameters())
                .headers(request.getHeaders());
        if (request.getBody() != null) {
            spec.body(request.getBody());
        }
        spec.log().all();

        Response response = spec.request(
                toRestAssuredMethod(request.getMethod()),
                request.getUri()
        );

        response.then().log().all();

        return new RestAssuredResponse(response);
    }

    private Method toRestAssuredMethod(RestMethod method) {
        return Method.valueOf(method.name());
    }
}