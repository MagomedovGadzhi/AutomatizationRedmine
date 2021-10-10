package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserVisibility {
    ALL("Все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECTS("Участники видимых проектов");

    private final String description;
}