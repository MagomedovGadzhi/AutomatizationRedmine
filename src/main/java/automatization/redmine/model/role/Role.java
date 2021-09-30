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

    private String name = "MGM_" + StringUtils.randomEnglishString(5);;
    private Integer position = 1;
    private Boolean assignable = true;
    private Integer builtin = 0;
    private List<Permission> permissions = new ArrayList<>();
    private IssuesVisibility issuesVisibility = IssuesVisibility.ALL;
    private UserVisibility userVisibility = UserVisibility.MEMBERS_OF_VISIBLE_PROJECTS;
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged = true;
    private String settings;

    @Override
    public Role create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }
}