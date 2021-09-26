package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IssuesVisibility {
    All("Все задачи"),
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользователю");

    String description;
}