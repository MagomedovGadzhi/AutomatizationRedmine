package automatization.redmine.model.role;

import automatization.redmine.model.user.MailNotification;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum IssuesVisibility {
    ALL("Все задачи", "all"),
    DEFAULT("Только общие задачи", "default"),
    OWN("Задачи созданные или назначенные пользователю", "own");

    private final String description;
    public final String issuesVisibilityCode;

    public static IssuesVisibility getIssuesVisibilityByCode(String code) {
        return Stream.of(values())
                .filter(issuesVisibility -> issuesVisibility.issuesVisibilityCode.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum IssuesVisibility"));
    }
}