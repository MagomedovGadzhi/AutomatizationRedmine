package tests.testNg;

import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

public class DatabaseUserLifecycleTest {

    @Test
    public void userLifecycleTest() {
        User user = new User()
                .setFirstName("НовоеИмя" + new Random().nextInt(100))
                .setLastName("НоваяФамилия" + new Random().nextInt(100))
                .setPassword("1qaz@WSX3edc");
        user.setTokens(
                Arrays.asList(
                        new Token(user),
                        new Token(user).setAction(Token.TokenType.SESSION),
                        new Token(user).setAction(Token.TokenType.SESSION)
                )
        ).setEmails(
                Arrays.asList(
                        new Email(user),
                        new Email(user).setIsDefault(false),
                        new Email(user).setIsDefault(false),
                        new Email(user).setIsDefault(false).setAddress("my_manual@mail.ru")
                )
        );
        user.create();
        user.setStatus(Status.UNACCEPTED);
        user.update();
        user.setStatus(Status.LOCKED);
        user.update();
        user.delete();
    }

}
