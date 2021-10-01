package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MailNotification {
    ALL("О всех событиях во всех моих проектах", "all"),
    ONLY_MY_EVENTS("Только для объектов, которые я отслеживаю или в которых участвую", "only_my_events"),
    ONLY_ASSIGNED("Только для объектов, которые я отслеживаю или которые мне назначены", "only_assigned"),
    ONLY_OWNER("Только для объектов, которые я отслеживаю или для которых я владелец", "only_owner"),
    NONE("Нет событий", "none");

    private final String description;
    public final String mailNotificationCode;
}