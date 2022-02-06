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

    protected String getStringFromObject(Object object) {
        return object != null ? object.toString() : null;
    }

    protected Integer getIntegerFromObject(Object object) {
        return object != null ? (Integer) object : null;
    }
}