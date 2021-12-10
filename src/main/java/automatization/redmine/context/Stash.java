package automatization.redmine.context;

import java.util.HashMap;
import java.util.Map;

public class Stash {

    private Map<String, Object> entities = new HashMap<>();

    public void put(String stashId, Object entity) {
        entities.put(stashId, entity);
    }

    public Object get(String stashId) {
        return entities.get(stashId);
    }

    public <T> T get(String stashId, Class<T> clazz) {
        return (T) get(stashId);
    }

}
