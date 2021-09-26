package automatization.redmine.model.project;

import automatization.redmine.model.Creatable;
import automatization.redmine.model.CreatableEntity;
import automatization.redmine.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Creatable<Project> {

    String name;
    String description;
    String homepage;
    Boolean isPublic;
    Integer parentId;
    String identifier;
    Integer status;
    Integer ift;
    Integer rgt;
    Boolean inheritMembers;
    Integer defaultVersionId;
    Integer defaultAssignedToId;
    List<Member> members;

    @Override
    public Project create() {
        // TODO: Реализовать с помощью SQL-Запроса
        throw new UnsupportedOperationException();
    }

    public void addUser (User user) {
        // TODO:
    }
}