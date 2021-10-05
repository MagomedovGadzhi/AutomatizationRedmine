package tests;

import automatization.redmine.db.requests.TokenRequests;
import automatization.redmine.db.requests.UserRequests;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.List;

public class TokenTest {
    @Test
    public void tokenReadTest() {
        User user = new UserRequests().read(26624);

        List<Token> tokens = new TokenRequests(user).readAll();
        System.out.println(tokens);

        Token token = new TokenRequests(user).read(37965);
        System.out.println(token);
    }
}