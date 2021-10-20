package automatization.redmine.model.user;

import automatization.redmine.db.requests.EmailRequests;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.Deleteable;
import automatization.redmine.model.Readable;
import automatization.redmine.model.Updateable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static automatization.redmine.utils.StringUtils.randomEmail;

@Accessors(chain = true)
@Setter
@Getter
public class Email extends CreatableEntity implements Creatable<Email>, Deleteable<Email>, Updateable<Email>, Readable<Email> {

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

    @Override
    public Email delete() {
        new EmailRequests().delete(this.id);
        return this;
    }

    @Override
    public Email update() {
        new EmailRequests().update(this.id, this);
        return this;
    }

    @Override
    public Email read() {
        return new EmailRequests().read(this.id);
    }

    @Override
    public String toString() {
        return "Email { " + "\n"
                + "id = " + id + "\n"
                + "userId = " + userId + "\n"
                + "address = " + address + "\n"
                + "isDefault = " + isDefault + "\n"
                + "notify = " + notify + "\n"
                + "createdOn = " + createdOn + "\n"
                + "updatedOn = " + updatedOn + "\n"
                + "}";
    }
}