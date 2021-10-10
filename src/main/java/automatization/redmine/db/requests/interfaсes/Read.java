package automatization.redmine.db.requests.interfaсes;

import automatization.redmine.model.Entity;

public interface Read<T extends Entity> {

    T read(Integer id);

}
