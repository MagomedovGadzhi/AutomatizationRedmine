package automatization.redmine.model.user;

import automatization.redmine.db.requests.EmailRequests;
import automatization.redmine.db.requests.TokenRequests;
import automatization.redmine.db.requests.UserRequests;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.Deleteable;
import automatization.redmine.model.Readable;
import automatization.redmine.model.Updateable;
import automatization.redmine.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static automatization.redmine.utils.StringUtils.randomEnglishString;
import static automatization.redmine.utils.StringUtils.randomHexString;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class User extends CreatableEntity implements Creatable<User>, Updateable<User>, Deleteable<User>, Readable<User> {

    private String login = "MGM_" + randomEnglishString(10);
    private String password = "1qaz@WSX";
    private String salt = randomHexString(32);
    private String hashedPassword = hashPassword();
    private String firstName = "MGM_" + randomEnglishString(10);
    private String lastName = "MGM_" + randomEnglishString(10);
    private Boolean isAdmin = false;
    private Status status = Status.ACTIVE;
    private LocalDateTime lastLoginOn;
    private Language language = Language.RUSSIAN;
    private String authSourceId;
    private String type = "User";
    private String identityUrl;
    private MailNotification mailNotification = MailNotification.NONE;
    private Boolean mustChangePassword = false;
    private LocalDateTime passwordChangedOn;
    private List<Token> tokens = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();

    public User setPassword(String password) {
        this.password = password;
        this.hashedPassword = hashPassword();
        return this;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        this.hashedPassword = hashPassword();
        return this;
    }

    private String hashPassword() {
        return sha1Hex(salt + sha1Hex(password));
    }

    @Override
    public User create() {
        new UserRequests().create(this);
        tokens.forEach(t -> t.setUserId(id));
        tokens.forEach(Token::create);
        emails.forEach(e -> e.setUserId(id));
        emails.forEach(Email::create);
        return this;
    }

    @Override
    public User delete() {
        new UserRequests().delete(this.id);
        return this;
    }

    @Override
    public User update() {
        new UserRequests().update(this.id, this);
        return this;
    }

    @Override
    public User read() {
        User resultUser = new UserRequests().read(this.id);
        resultUser.setEmails(new EmailRequests(resultUser).readAll());
        resultUser.setTokens(new TokenRequests(resultUser).readAll());
        return resultUser;
    }

    public void addProject(Integer projectId, List<Role> roles) {
        new UserRequests().addUserToProject(this, projectId, roles);
    }

    @Override
    public String toString() {
        return "User { " + "\n"
                + "id = " + id + "\n"
                + "login = " + login + "\n"
                + "password (по умолчанию) = " + password + "\n"
                + "salt = " + salt + "\n"
                + "hashedPassword = " + hashedPassword + "\n"
                + "firstName = " + firstName + "\n"
                + "lastName = " + lastName + "\n"
                + "isAdmin = " + isAdmin + "\n"
                + "status = " + status + "\n"
                + "lastLoginOn = " + lastLoginOn + "\n"
                + "language = " + language + "\n"
                + "authSourceId = " + authSourceId + "\n"
                + "type = " + type + "\n"
                + "identityUrl = " + identityUrl + "\n"
                + "mailNotification = " + mailNotification + "\n"
                + "mustChangePassword = " + mustChangePassword + "\n"
                + "passwordChangedOn = " + passwordChangedOn + "\n"
                + "createdOn = " + createdOn + "\n"
                + "updatedOn = " + updatedOn + "\n"
                + "tokens = " + tokens + "\n"
                + "emails = " + emails + "\n"
                + "}";
    }
}