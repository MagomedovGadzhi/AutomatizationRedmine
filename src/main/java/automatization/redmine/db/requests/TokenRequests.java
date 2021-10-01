package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfases.Create;
import automatization.redmine.db.requests.interfases.ReadAll;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class TokenRequests extends BaseRequests implements Create<Token>, ReadAll<Token> {
    private User user;

    @Override
    public void create(Token token) {
        String query = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(
                query,
                token.getUserId(),
                token.getAction().name().toLowerCase(),
                token.getValue(),
                token.getCreatedOn(),
                token.getUpdatedOn()
        );
        Integer tokenId = (Integer) queryResult.get(0).get("id");
        token.setId(tokenId);
    }

    @Override
    public List<Token> readAll() {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "SELECT * FROM tokens WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(
                query,
                userId
        );
        return queryResult.stream()
                .map(data -> from(data, user))
                .collect(Collectors.toList());
    }

    private Token from(Map<String, Object> data, User user) {
        return (Token) new Token(user)
                .setAction(
                        Token.TokenType.valueOf(data.get("action").toString().toUpperCase())
                )
                .setValue((String) data.get("value"))
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setId((Integer) data.get("id"));
    }
}
