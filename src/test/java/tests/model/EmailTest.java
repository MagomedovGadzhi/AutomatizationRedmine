package tests.model;

import automatization.redmine.db.requests.EmailRequests;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.List;

public class EmailTest {
    @Test
    public void emailReadTest () {
        User user = new User();
        new Email(user);
        user.create();

        User user2 = user.read();

        List<Email> emails = new EmailRequests(user2).readAll();
        System.out.println(emails);

        Email email = new EmailRequests(user2).read(user.getEmails().get(0).getId());
        System.out.println(email);
    }
}
