package tests.model;

import automatization.redmine.db.requests.TokenRequests;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.List;

public class TokenTest {
    @Test
    public void tokenReadTest() {
        User user = new User();
        new Token(user);
        user.create();

        User user2 = user.read();

        List<Token> tokens = new TokenRequests(user2).readAll();
        System.out.println(tokens);

        Token token = new TokenRequests(user2).read(38639);
        System.out.println(token);
    }
}