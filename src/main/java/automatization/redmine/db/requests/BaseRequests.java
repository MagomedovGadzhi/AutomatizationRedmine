package automatization.redmine.db.requests;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class BaseRequests {

    protected LocalDateTime toLocalDate(Object timestamp) {
        Timestamp ts = (Timestamp) timestamp;
        return ts.toLocalDateTime();
    }

    /**
     * Отдельный метод проверяющий, что объект не равен NULL.
     * Был создан т.к. при попытке установить значение переменной объекта,
     * которая в БД равна NULL, возникало исключение.
     * Пока использую только для String.
     */
    protected String checkIsStringNull(Object object) {
        if (object == null) return null;
        else return object.toString();
    }

    protected Integer checkIsIntegerNull(Object object) {
        if (object == null) return null;
        else return (Integer) object;
    }
}