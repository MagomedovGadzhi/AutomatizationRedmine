package automatization.redmine.db.requests.interfaсes;

import automatization.redmine.model.user.User;

public interface ReadByLogin<T extends User> {

    T readByLogin(String login);
}