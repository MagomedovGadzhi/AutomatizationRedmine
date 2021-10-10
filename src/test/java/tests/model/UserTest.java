package tests.model;

import automatization.redmine.model.project.Project;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.Token;
import automatization.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void addUserToProjectTets () {
        Project project = new Project();
        project.create();

        Role role1 = new Role();
        role1.create();
        Role role2 = new Role();
        role2.create();

        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        User user = new User();
        new Token(user);
        new Email(user);
        user.create();
        user.addProject(project.getId(), roles);

        System.out.println(project.getId());
        System.out.println();
        System.out.println(user.getId());

        /*
        user.getTokens().forEach(Token::delete);
        user.getEmails().forEach(Email::delete);
        user.delete();
         */
    }
}