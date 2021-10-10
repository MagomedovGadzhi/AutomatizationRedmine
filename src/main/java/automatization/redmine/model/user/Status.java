package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Status {
    UNREGISTERED(0),
    ACTIVE(1),
    UNACCEPTED(2),
    LOCKED(3);

    public final int statusCode;

    public static Status getStatusFromCode(int code) {
        return Stream.of(values())
                .filter(status -> status.statusCode == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum Status"));
    }
}