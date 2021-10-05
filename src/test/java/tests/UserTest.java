package tests;

import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

public class UserTest {
    @Test
    public void userCreationTest() {
        User user = new User();
        new Token(user);
        new Email(user);
        user.create();

        User user2 = user.read();
        System.out.println(user);
        System.out.println();
        System.out.println(user2);

        user.getTokens().forEach(Token::delete);
        user.getEmails().forEach(Email::delete);
        user.delete();
    }
}