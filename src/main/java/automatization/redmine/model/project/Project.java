package automatization.redmine.model.project;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.User;
import automatization.redmine.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Creatable<Project> {

    private String name = "MGM_" + StringUtils.randomEnglishString(5);
    private String description = "Тестовый проект";
    private String homepage;
    private Boolean isPublic = true;
    private Integer parentId;
    private String identifier = "MGM_" + StringUtils.randomEnglishString(5);
    private ProjectStatus projectStatus = ProjectStatus.CREATED;
    private Integer ift;
    private Integer rgt;
    private Boolean inheritMembers = false;
    private Integer defaultVersionId;
    private Integer defaultAssignedToId;
    private Map<User, List<Role>> usersAndRoles = new HashMap<>();

    @Override
    public Project create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }

    public void addUserWithRoles(User user, List<Role> roles) {
        usersAndRoles.put(user, roles);
    }
}