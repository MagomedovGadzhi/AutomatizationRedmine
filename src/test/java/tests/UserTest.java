package tests;

import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.Collections;

public class UserTest {

    @Test
    public void userCreationTest() {
        User user = new User();

        User user2 = new User();
        user2.setPassword("qwriqwiqw");
        user2.setFirstName("Иван");
        user2.setLastName("Петров");
        user2.setTokens(Collections.singletonList(new Token()));
        user2.setEmails(Collections.singletonList(new Email()));

    }
}