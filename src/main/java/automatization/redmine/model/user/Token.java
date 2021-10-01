package automatization.redmine.model.user;

import automatization.redmine.db.requests.TokenRequests;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static automatization.redmine.utils.StringUtils.randomHexString;

@Accessors(chain = true)
@Setter
@Getter
public class Token extends CreatableEntity implements Creatable<Token> {

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
    public Token create() {
        new TokenRequests().create(this);
        return this;
    }
}