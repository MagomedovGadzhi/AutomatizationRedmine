package tests;

import automatization.redmine.model.role.Role;
import org.testng.annotations.Test;

public class RoleTest {

    @Test
    public void roleCreationTest() {
        Role role1 = new Role();

        Role role2 = new Role();
        role2.setName("TestRoleName");
    }
}