package automatization.redmine.model.role;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Role extends Entity implements Creatable<Role> {

    String name;
    Integer position;
    Boolean assignable;
    Integer builtin;
    List<String> permissions = new ArrayList<>();
    String issuesVisibility;
    String userVisibility;
    String timeEntriesVisibility;
    Boolean allRolesManaged;
    String settings;

    @Override
    public Role create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}