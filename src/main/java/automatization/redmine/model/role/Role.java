package automatization.redmine.model.role;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.Entity;
import automatization.redmine.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Role extends Entity implements Creatable<Role> {

    String name = "MGM_" + StringUtils.randomEnglishString(5);;
    Integer position = 1;
    Boolean assignable = true;
    Integer builtin = 0;
    List<String> permissions = new ArrayList<>();
    IssuesVisibility issuesVisibility = IssuesVisibility.All;
    UserVisibility userVisibility = UserVisibility.MEMBERS_OF_VISIBLE_PROJECTS;
    TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.All;
    Boolean allRolesManaged = true;
    String settings;

    @Override
    public Role create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}