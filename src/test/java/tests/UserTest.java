package tests;

import automatization.redmine.db.requests.UserRequests;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

public class UserTest {
    //Протестировать методы Token read и readAll, скорее всего не работают.
    @Test
    public void userCreationTest() {
        User user = new User();
        user.create();
        new Token(user);
        new Email(user);

        User user2 = new UserRequests().read(user.getId());
        System.out.println(user);
        System.out.println();
        System.out.println(user2);

        user.getTokens().forEach(Token::delete);
        user.getEmails().forEach(Email::delete);
        user.delete();
    }
}