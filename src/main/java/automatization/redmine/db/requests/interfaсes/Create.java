package automatization.redmine.db.requests.interfaсes;

import automatization.redmine.model.Entity;

public interface Create<T extends Entity> {

    void create(T entity);

}
