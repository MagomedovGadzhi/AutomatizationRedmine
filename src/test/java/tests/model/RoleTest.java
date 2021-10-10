package tests.model;

import automatization.redmine.db.requests.RoleRequest;
import automatization.redmine.model.role.Permission;
import automatization.redmine.model.role.Role;
import automatization.redmine.model.role.UserVisibility;
import org.testng.annotations.Test;

public class RoleTest {

    @Test
    public void roleCreationTest() {
        Role role1 = new Role();
        role1.create();
        /*
        role1.getPermissions().add(Permission.ADD_DOCUMENTS);
        role1.getPermissions().add(Permission.ADD_PROJECT);
        String s = "---\n" +
                "- :add_project\n" +
                "- :edit_project\n" +
                "- :edit_messages\n" +
                "- :manage_wiki\n";
         */
        Role role2 = new RoleRequest().read(1863);
        System.out.println(role2);
        role2.setBuiltin(1)
                .setAllRolesManaged(true)
                .setUserVisibility(UserVisibility.MEMBERS_OF_VISIBLE_PROJECTS)
                .getPermissions().add(Permission.LOG_TIME);
        role2.update();
        System.out.println(role2);
        //role2.delete();
    }
}