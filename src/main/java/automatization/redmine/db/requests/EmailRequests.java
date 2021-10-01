package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfases.*;
import automatization.redmine.model.user.Email;
import automatization.redmine.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class EmailRequests extends BaseRequests implements Create<Email>, ReadAll<Email>, Read<Email>, Update<Email>, Delete {
    private User user;

    @Override
    public void create(Email email) {
        String query = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        Integer id = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn()
        ).get(0).get("id");
        email.setId(id);
    }

    @Override
    public List<Email> readAll() {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "SELECT * FROM public.email_addresses WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userId);
        return queryResult.stream()
                .map(data -> from(data, user))
                .collect(Collectors.toList());
    }

    @Override
    public Email read(Integer id) {
        String query = "SELECT * FROM public.email_addresses WHERE id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, id);
        return from(queryResult.get(0), user);
    }

    @Override
    public void update(Integer id, Email email) {
        String query = "UPDATE public.email_addresses\n" +
                "SET user_id=?, address=?, is_default=?, \"notify\"=?, created_on=?, updated_on=?\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn(),
                id
        );
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.email_addresses WHERE id = ?;";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    private Email from(Map<String, Object> data, User user) {
        return (Email) new Email(user)
                .setAddress((String) data.get("address"))
                .setIsDefault((Boolean) data.get("is_default"))
                .setNotify((Boolean) data.get("notify"))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setId((Integer) data.get("id"));
    }
}