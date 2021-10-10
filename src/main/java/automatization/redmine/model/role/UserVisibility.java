package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum UserVisibility {
    ALL("Все активные пользователи", "all"),
    MEMBERS_OF_VISIBLE_PROJECTS("Участники видимых проектов", "members_of_visible_projects");

    private final String description;
    public final String userVisibilityCode;

    public static UserVisibility getUserVisibilityByCode(String code) {
        return Stream.of(values())
                .filter(userVisibility -> userVisibility.userVisibilityCode.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum UserVisibility"));
    }
}