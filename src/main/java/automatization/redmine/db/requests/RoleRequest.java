package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfases.*;
import automatization.redmine.model.role.*;

import java.util.List;
import java.util.Map;

public class RoleRequest extends BaseRequests implements Create<Role>, Delete, Read<Role>, Update<Role> {
    @Override
    public void create(Role role) {
        String query = "INSERT INTO public.roles\n" +
                "(id, name, \"position\", assignable, builtin, permissions, issues_visibility, " +
                "users_visibility, time_entries_visibility, all_roles_managed, settings)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        Integer roleId = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin(),
                role.getStringOfPermissions(),          //вызывается метод возвращаюший строку со списком прав доступа
                role.getIssuesVisibility().issuesVisibilityCode,
                role.getUserVisibility().userVisibilityCode,
                role.getTimeEntriesVisibility().timeEntriesVisibilityCode,
                role.getAllRolesManaged(),
                role.getSettings()).get(0).get("id");
        role.setId(roleId);
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.roles\n WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    @Override
    public Role read(Integer id) {
        String query = "SELECT id, \"name\", \"position\", assignable, builtin, permissions, issues_visibility, users_visibility, time_entries_visibility, all_roles_managed, settings\n" +
                "FROM public.roles\n" +
                "WHERE id=?;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, id);
        return from(queryResult.get(0));
    }

    @Override
    public void update(Integer id, Role role) {
        //TODO:
    }

    private Role from(Map<String, Object> data) {
        return (Role) new Role()
                .setName((String) data.get("name"))
                .setPosition((Integer) data.get("position"))
                .setAssignable((Boolean) data.get("assignable"))
                .setBuiltin((Integer) (data.get("builtin")))
                .setPermissionsFromString(checkIsStringNull(data.get("permissions")))
                .setIssuesVisibility(IssuesVisibility.valueOf(data.get("issues_visibility").toString().toUpperCase()))
                .setUserVisibility(UserVisibility.valueOf(data.get("users_visibility").toString().toUpperCase()))
                .setTimeEntriesVisibility(TimeEntriesVisibility.valueOf(data.get("time_entries_visibility").toString().toUpperCase()))
                .setAllRolesManaged((Boolean) data.get("all_roles_managed"))
                .setSettings((String) data.get("settings"))
                .setId((Integer) data.get("id"));
    }

    /**
     * Отдельный метод проверяющий, что объект не равен NULL.
     * Был создан т.к. при попытке установить значение переменной,
     * которая в БД равна NULL, возникало исключение.
     * Пока использую только для стрингов.
     */
    private String checkIsStringNull(Object object) {
        if (object == null) return null;
        else return object.toString();
    }
}