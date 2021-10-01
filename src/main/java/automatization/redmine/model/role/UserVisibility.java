package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserVisibility {
    ALL("Все активные пользователи", "all"),
    MEMBERS_OF_VISIBLE_PROJECTS("Участники видимых проектов", "members_of_visible_projects");

    private final String description;
    private final String userVisibilityCode;
}