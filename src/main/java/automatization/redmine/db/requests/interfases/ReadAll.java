package automatization.redmine.db.requests.interfases;

import automatization.redmine.model.Entity;

import java.util.List;

public interface ReadAll<T extends Entity> {

    List<T> readAll();

}