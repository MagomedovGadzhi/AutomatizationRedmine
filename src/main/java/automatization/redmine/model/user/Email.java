package automatization.redmine.model.user;

import automatization.redmine.model.Creatable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static automatization.redmine.utils.StringUtils.randomEmail;

@NoArgsConstructor
@Setter
@Getter
public class Email extends CreatableEntity implements Creatable<Email> {

    private Integer userId;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    @Override
    public Email create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}