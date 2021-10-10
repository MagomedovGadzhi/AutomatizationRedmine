package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Status {
    UNREGISTERED(0, "Не зарегистрирован"),
    ACTIVE(1, "Активирован"),
    UNACCEPTED(2, "Зарегестрирован, но еще не подтвердил почтовый ящик или не актвирован администратором"),
    LOCKED(3, "Заблокирован");

    public final int statusCode;
    public final String description;

    public static Status getStatusFromCode(int code) {
        return Stream.of(values())
                .filter(status -> status.statusCode == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum Status"));
    }
}