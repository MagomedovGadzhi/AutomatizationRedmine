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
                role.getStringOfPermissions(),          //метод возвращаюший строку со списком прав доступа
                role.getIssuesVisibility().issuesVisibilityCode,
                role.getUserVisibility().userVisibilityCode,
                role.getTimeEntriesVisibility().timeEntriesVisibilityCode,
                role.getAllRolesManaged(),
                role.getSettings()
        ).get(0).get("id");
        role.setId(roleId);
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.roles\n WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    @Override
    public Role read(Integer id) {
        String query = "SELECT id, \"name\", \"position\", assignable, builtin, permissions, issues_visibility, " +
                "users_visibility, time_entries_visibility, all_roles_managed, settings\n" +
                "FROM public.roles\n" +
                "WHERE id=?;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, id);
        return from(queryResult.get(0));
    }

    @Override
    public void update(Integer id, Role role) {
        String query = "UPDATE public.roles\n" +
                "SET \"name\"=?, \"position\"=?, assignable=?, builtin=?, permissions=?, issues_visibility=?, " +
                "users_visibility=?, time_entries_visibility=?, all_roles_managed=?, settings=?\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin(),
                role.getStringOfPermissions(),          //метод возвращаюший строку со списком прав доступа
                role.getIssuesVisibility().issuesVisibilityCode,
                role.getUserVisibility().userVisibilityCode,
                role.getTimeEntriesVisibility().timeEntriesVisibilityCode,
                role.getAllRolesManaged(),
                role.getSettings(),
                id
        );
    }

    private Role from(Map<String, Object> data) {
        return (Role) new Role()
                .setName((String) data.get("name"))
                .setPosition((Integer) data.get("position"))
                .setAssignable((Boolean) data.get("assignable"))
                .setBuiltin((Integer) (data.get("builtin")))
                .setPermissionsFromString(checkIsStringNull(data.get("permissions")))           //проверка, что поле не нульное
                .setIssuesVisibility(IssuesVisibility.valueOf(data.get("issues_visibility").toString().toUpperCase()))
                .setUserVisibility(UserVisibility.valueOf(data.get("users_visibility").toString().toUpperCase()))
                .setTimeEntriesVisibility(TimeEntriesVisibility.valueOf(data.get("time_entries_visibility").toString().toUpperCase()))
                .setAllRolesManaged((Boolean) data.get("all_roles_managed"))
                .setSettings((String) data.get("settings"))
                .setId((Integer) data.get("id"));
    }
}