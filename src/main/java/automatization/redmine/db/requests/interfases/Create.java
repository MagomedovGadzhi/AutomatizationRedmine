package automatization.redmine.db.requests.interfases;

import automatization.redmine.model.Entity;

public interface Create<T extends Entity> {

    void create(T entity);

}
