package tests.model;

import automatization.redmine.db.requests.EmailRequests;
import automatization.redmine.db.requests.UserRequests;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.List;

public class EmailTest {
    @Test
    public void emailReadTest () {
        User user = new UserRequests().read(26477);

        List<Email> emails = new EmailRequests(user).readAll();
        System.out.println(emails);

        Email email = new EmailRequests(user).read(23235);
        System.out.println(email);
    }
}
