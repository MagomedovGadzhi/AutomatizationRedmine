package automatization.redmine.model.project;

import automatization.redmine.db.requests.ProjectRequest;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.Deleteable;
import automatization.redmine.model.Readable;
import automatization.redmine.model.Updateable;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.User;
import automatization.redmine.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Project extends CreatableEntity implements Creatable<Project>, Deleteable<Project>, Updateable<Project>, Readable<Project> {

    private String name = "MGM_" + StringUtils.randomEnglishString(5);
    private String description = "Тестовый проект";
    private String homepage;
    private Boolean isPublic = true;
    private Integer parentId;
    private String identifier = "MGM_" + StringUtils.randomEnglishString(5);
    private ProjectStatus projectStatus = ProjectStatus.CREATED;
    private Integer ift = 1;        //захардкодил поля ift, rgt, т.к. падала БД.
    private Integer rgt = 1;
    private Boolean inheritMembers = false;
    private Integer defaultVersionId;
    private Integer defaultAssignedToId;
    private Map<User, List<Role>> usersAndRoles = new HashMap<>();

    @Override
    @Step("Создан проект в БД")
    public Project create() {
        new ProjectRequest().create(this);
        return this;
    }

    @Override
    @Step("Проект удален из БД")
    public Project delete() {
        new ProjectRequest().delete(this.id);
        return this;
    }

    @Override
    @Step("Проект изменен в БД")
    public Project update() {
        new ProjectRequest().update(this.id, this);
        return this;
    }

    @Override
    @Step("Проект прочитан из БД")
    public Project read() {
        return new ProjectRequest().read(this.id);
    }

    @Override
    public String toString() {
        return "Project { " + "\n"
                + "id = " + id + "\n"
                + "name = " + name + "\n"
                + "description = " + description + "\n"
                + "homepage = " + homepage + "\n"
                + "isPublic = " + isPublic + "\n"
                + "parentId = " + parentId + "\n"
                + "identifier = " + identifier + "\n"
                + "projectStatus = " + projectStatus + "\n"
                + "ift = " + ift + "\n"
                + "rgt = " + rgt + "\n"
                + "inheritMembers = " + inheritMembers + "\n"
                + "defaultVersionId = " + defaultVersionId + "\n"
                + "defaultAssignedToId = " + defaultAssignedToId + "\n"
                + "createdOn = " + createdOn + "\n"
                + "updatedOn = " + updatedOn + "\n"
                + "usersAndRoles = " + usersAndRoles + "\n"
                + "}";
    }
}