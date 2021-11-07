package automatization.redmine.model.role;

import automatization.redmine.db.requests.RoleRequest;
import automatization.redmine.model.Creatable;
import automatization.redmine.model.Deleteable;
import automatization.redmine.model.Entity;
import automatization.redmine.model.Readable;
import automatization.redmine.model.Updateable;
import automatization.redmine.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Role extends Entity implements Creatable<Role>, Deleteable<Role>, Updateable<Role>, Readable<Role> {

    private String name = "MGM_" + StringUtils.randomEnglishString(5);
    private Integer position = 1;
    private Boolean assignable = true;
    private Integer builtin = 0;
    private List<Permission> permissions = new ArrayList<>();
    private IssuesVisibility issuesVisibility = IssuesVisibility.ALL;
    private UserVisibility userVisibility = UserVisibility.ALL;
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.OWN;
    private Boolean allRolesManaged = false;
    private String settings;

    public String getStringOfPermissions() {
        if (permissions.size() == 1) {
            return permissions.get(0).toString().toLowerCase();
        }
        if (permissions.size() > 1) {
            StringBuilder result = new StringBuilder();
            result.append("---").append("\n");
            for (Permission permission : permissions) {
                result.append("- :").append(permission.toString().toLowerCase()).append("\n");
            }
            return result.toString();
        } else return null;
    }

    public Role setPermissionsFromString(String permissions) {
        if (permissions == null) return this;
        String formattedPermissions = permissions.replaceAll("-", "")
                .replaceAll(":", "")
                .replaceAll(" ", "")
                .trim();
        String[] permissionsList = formattedPermissions.split("\n");
        for (String perm : permissionsList) {
            perm = perm.toUpperCase();
            this.permissions.add(Permission.valueOf(perm));
        }
        return this;
    }

    @Override
    @Step("Создана роль в БД")
    public Role create() {
        new RoleRequest().create(this);
        return this;
    }

    @Override
    @Step("Роль удалена из БД")
    public Role delete() {
        new RoleRequest().delete(this.id);
        return this;
    }

    @Override
    @Step("Роль изменена в БД")
    public Role update() {
        new RoleRequest().update(this.id, this);
        return this;
    }

    @Override
    @Step("Роль прочитана из БД")
    public Role read() {
        return new RoleRequest().read(this.id);
    }

    @Override
    public String toString() {
        return "Role { " + "\n"
                + "id = " + id + "\n"
                + "name = " + name + "\n"
                + "position = " + position + "\n"
                + "assignable = " + assignable + "\n"
                + "builtin = " + builtin + "\n"
                + "permissions = " + permissions + "\n"
                + "issuesVisibility = " + issuesVisibility + "\n"
                + "userVisibility = " + userVisibility + "\n"
                + "timeEntriesVisibility = " + timeEntriesVisibility + "\n"
                + "allRolesManaged = " + allRolesManaged + "\n"
                + "settings = " + settings + "\n"
                + "}";
    }
}