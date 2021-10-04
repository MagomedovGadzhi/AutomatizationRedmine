package automatization.redmine.db.requests;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class BaseRequests {

    protected LocalDateTime toLocalDate(Object timestamp) {
        if (timestamp == null) {
            return null;
        }
        Timestamp ts = (Timestamp) timestamp;
        return ts.toLocalDateTime();
    }

    /**
     * Отдельный методы проверяющие, что объект не равен NULL.
     * Созданы т.к. при попытке установить значение переменной объекта,
     * которая в БД равна NULL, возникало исключение.
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