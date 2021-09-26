package automatization.redmine.model.user;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static automatization.redmine.utils.StringUtils.randomEnglishString;
import static automatization.redmine.utils.StringUtils.randomHexString;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@NoArgsConstructor
@Setter
@Getter
public class User extends CreatableEntity implements Creatable<User> {

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

    public void setPassword(String password) {
        this.password = password;
        this.hashedPassword = hashPassword();
    }

    public void setSalt(String salt) {
        this.salt = salt;
        this.hashedPassword = hashPassword();
    }

    private String hashPassword() {
        return sha1Hex(salt + sha1Hex(password));
    }

    @Override
    public User create() {
        // TODO: Реализовать с помощью SQL-Запроса
        tokens.forEach(Token::create);
        emails.forEach(Email::create);
        throw new UnsupportedOperationException();
    }

    public void addProject(Project project, List<Role> roles) {
        // TODO: Реализовать с помощью SQL-запроса
    }
}