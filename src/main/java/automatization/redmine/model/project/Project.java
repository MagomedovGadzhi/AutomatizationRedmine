package automatization.redmine.model.project;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.User;
import automatization.redmine.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Creatable<Project> {

    String name = "MGM_" + StringUtils.randomEnglishString(5);
    String description = "Тестовый проект";
    String homepage = "http://edu-at.dfu.i-teco.ru";
    Boolean isPublic = true;
    Integer parentId;
    String identifier = "MGM_" + StringUtils.randomEnglishString(5);
    Integer status = 1;
    Integer ift;
    Integer rgt;
    Boolean inheritMembers = false;
    Integer defaultVersionId;
    Integer defaultAssignedToId;
    Map<Member, List<Role>> members_roles;

    @Override
    public Project create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }

    public void addUserWithRoles (User user, Role role) {
    }
}