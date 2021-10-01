package automatization.redmine.model;

public interface Updateable<T extends Entity> {

    T update();

}