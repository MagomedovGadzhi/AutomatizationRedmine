package automatization.redmine.model;

import automatization.redmine.model.user.Entity;

public interface Creatable<T extends Entity> {

    T create();
}