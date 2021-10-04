package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    UNREGISTERED(0),
    ACTIVE(1),
    UNACCEPTED(2),
    LOCKED(3);

    public final int statusCode;

    public static Status getStatusFromCode(Integer statusCode) {
        switch (statusCode) {
            case 0:
                return UNREGISTERED;
            case 1:
                return ACTIVE;
            case 2:
                return UNACCEPTED;
            case 3:
                return LOCKED;
        }
        throw new IllegalArgumentException("Указанного кода статуса не существует.");
    }
}