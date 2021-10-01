package automatization.redmine.db.requests.interfases;

import automatization.redmine.model.Entity;

public interface Update<T extends Entity> {

    void update(Integer id, T entity);

}
