package automatization.redmine.model.user;

import automatization.redmine.db.requests.TokenRequests;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.Deleteable;
import automatization.redmine.model.Readable;
import automatization.redmine.model.Updateable;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static automatization.redmine.utils.StringUtils.randomHexString;

@Accessors(chain = true)
@Setter
@Getter
public class Token extends CreatableEntity implements Creatable<Token>, Deleteable<Token>, Updateable<Token>, Readable<Token> {

    private Integer userId;
    private TokenType action = TokenType.API;
    private String value = randomHexString(40);

    public Token(User user) {
        this.userId = user.getId();
        user.getTokens().add(this);
    }

    public enum TokenType {
        SESSION,
        API,
        FEEDS
    }

    @Override
    @Step("Токен создан в БД")
    public Token create() {
        new TokenRequests().create(this);
        return this;
    }

    @Override
    @Step("Токен удален из БД")
    public Token delete() {
        new TokenRequests().delete(this.id);
        return this;
    }

    @Override
    @Step("Токен изменен в БД")
    public Token update() {
        new TokenRequests().update(this.id, this);
        return this;
    }

    @Override
    @Step("Токен прочитан из БД")
    public Token read() {
        return new TokenRequests().read(this.id);
    }

    @Override
    public String toString() {
        return "Token { " + "\n"
                + "id = " + id + "\n"
                + "userId = " + userId + "\n"
                + "action = " + action + "\n"
                + "value = " + value + "\n"
                + "createdOn = " + createdOn + "\n"
                + "updatedOn = " + updatedOn + "\n"
                + "}";
    }
}