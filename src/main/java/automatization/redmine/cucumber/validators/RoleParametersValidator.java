package automatization.redmine.cucumber.validators;

import automatization.redmine.model.role.Permission;

import java.util.List;

public class RoleParametersValidator {

    public static void validateRolePermissions(List<String> permissions) {
        for (String permissionDescription : permissions) {
            Permission.getPermissionByDescription(permissionDescription);
        }
    }
}