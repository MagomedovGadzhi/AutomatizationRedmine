package automatization.redmine.model;

public interface Readable <T extends Entity> {

    T read();
}