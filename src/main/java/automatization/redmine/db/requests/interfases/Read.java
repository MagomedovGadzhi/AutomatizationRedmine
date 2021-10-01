package automatization.redmine.db.requests.interfases;

import automatization.redmine.model.Entity;

public interface Read<T extends Entity> {

    T read(Integer id);

}
