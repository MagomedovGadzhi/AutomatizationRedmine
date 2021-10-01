package automatization.redmine.model.user;

import automatization.redmine.db.requests.EmailRequests;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static automatization.redmine.utils.StringUtils.randomEmail;

@Accessors(chain = true)
@Setter
@Getter
public class Email extends CreatableEntity implements Creatable<Email> {

    private Integer userId;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    public Email(User user) {
        this.userId = user.getId();
        user.getEmails().add(this);
    }

    @Override
    public Email create() {
        new EmailRequests().create(this);
        return this;
    }
}