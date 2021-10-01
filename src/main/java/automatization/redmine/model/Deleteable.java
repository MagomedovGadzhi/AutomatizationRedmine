package automatization.redmine.model;

public interface Deleteable<T extends Entity> {

    T delete();

}
