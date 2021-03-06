package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfaсes.*;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.user.Language;
import automatization.redmine.model.user.MailNotification;
import automatization.redmine.model.user.Status;
import automatization.redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserRequests extends BaseRequests implements Create<User>, Update<User>, Read<User>, ReadByLogin<User>, Delete {

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
                user.getMailNotification().name().toLowerCase(),
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
                user.getMailNotification().name().toLowerCase(),
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn(),
                id
        );
    }

    @Override
    public User read(Integer userId) {
        String query = "SELECT * FROM public.users WHERE id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userId);
        if (queryResult == null || queryResult.size() == 0) {
            return null;
        }
        return from(queryResult.get(0));
    }

    @Override
    public User readByLogin(String userLogin) {
        String query = "SELECT * FROM public.users WHERE login = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userLogin);
        if (queryResult == null || queryResult.size() == 0) {
            return null;
        }
        return from(queryResult.get(0));
    }

    private User from(Map<String, Object> data) {
        return (User) new User()
                .setLogin((String) data.get("login"))
                .setSalt((String) data.get("salt"))
                .setHashedPassword((String) data.get("hashed_password"))
                .setFirstName((String) data.get("firstname"))
                .setLastName((String) data.get("lastname"))
                .setIsAdmin((Boolean) data.get("admin"))
                .setStatus(Status.getStatusFromCode((Integer) data.get("status")))
                .setLastLoginOn(toLocalDate(data.get("last_login_on")))
                .setLanguage(Language.getLanguageByCode(data.get("language").toString()))
                .setAuthSourceId((String) data.get("auth_source_id"))
                .setType((String) data.get("type"))
                .setIdentityUrl((String) data.get("identity_url"))
                .setMailNotification(MailNotification.valueOf(data.get("mail_notification").toString().toUpperCase()))
                .setMustChangePassword((Boolean) data.get("must_change_passwd"))
                .setPasswordChangedOn(toLocalDate(data.get("passwd_changed_on")))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setId((Integer) data.get("id"));
    }

    public Integer addMember(User user, Integer projectId) {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "INSERT INTO public.members\n" +
                "(id, user_id, project_id, created_on, mail_notification)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?) RETURNING id;\n";
        return (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                userId,
                projectId,
                LocalDateTime.now(),
                false
        ).get(0).get("id");
    }

    public void addMemberRoles(Integer memberId, Role role) {
        String query = "INSERT INTO public.member_roles\n" +
                "(id, member_id, role_id, inherited_from)\n" +
                "VALUES(DEFAULT, ?, ?, ?) RETURNING id;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                memberId,
                role.getId(),
                null
        );
    }
}