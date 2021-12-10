package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum MailNotification {
    ALL("О всех событиях во всех моих проектах"),
    ONLY_MY_EVENTS("Только для объектов, которые я отслеживаю или в которых участвую"),
    ONLY_ASSIGNED("Только для объектов, которые я отслеживаю или которые мне назначены"),
    ONLY_OWNER("Только для объектов, которые я отслеживаю или для которых я владелец"),
    NONE("Нет событий");

    private final String description;

    public static MailNotification getMailNotificationFromDescription(String description) {
        return Stream.of(values())
                .filter(mailNotification -> mailNotification.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект MailNotification с описанием " + description));
    }
}