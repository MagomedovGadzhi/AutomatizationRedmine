package automatization.redmine.model.project;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class MemberRoles extends Entity implements Creatable<MemberRoles> {

    Integer memberId;
    Integer roleId;
    Integer inheritedFrom;

    @Override
    public MemberRoles create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}
