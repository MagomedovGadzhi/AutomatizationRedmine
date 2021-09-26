package automatization.redmine.model.project;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Member extends Entity implements Creatable<Member> {

    Integer userId;
    Integer projectId;
    LocalDateTime createdOn;
    Boolean mailNotification;
    List<MemberRoles> memberRoles;

    @Override
    public Member create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}