package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IssuesVisibility {
    ALL("Все задачи", "all"),
    DEFAULT("Только общие задачи", "default"),
    OWN("Задачи созданные или назначенные пользователю", "own");

    private final String description;
    private final String issuesVisibilityCode;

}