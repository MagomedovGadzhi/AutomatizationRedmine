package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfases.Create;
import automatization.redmine.db.requests.interfases.Delete;
import automatization.redmine.db.requests.interfases.Update;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.User;

import java.util.List;

public class UserRequests extends BaseRequests implements Create<User>, Update<User>, Delete {

    @Override
    public void create(User user) {
        String query = "INSERT INTO public.users\n" +
                "(id, login, hashed_password, firstname, lastname, " +
                "\"admin\", status, last_login_on, \"language\", auth_source_id, " +
                "created_on, updated_on, \"type\", identity_url, mail_notification, " +
                "salt, must_change_passwd, passwd_changed_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        Integer userId = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().languageCode,
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().mailNotificationCode,
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn()
        ).get(0).get("id");
        user.setId(userId);
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.users WHERE id = ?";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    @Override
    public void update(Integer id, User user) {
        String query = "UPDATE public.users\n" +
                "SET login=?, hashed_password=?, firstname=?, lastname=?, \"admin\"=?, " +
                "status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, " +
                "updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, " +
                "must_change_passwd=?, passwd_changed_on=? \n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().languageCode,
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().mailNotificationCode,
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn(),
                id
        );
    }

    public void addUserToProject(User user, Integer projectId, List<Role> roles) {
        //TODO
    }
}