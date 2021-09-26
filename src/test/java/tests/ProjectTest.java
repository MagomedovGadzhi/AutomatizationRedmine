package tests;

import automatization.redmine.model.project.Project;
import org.testng.annotations.Test;

public class ProjectTest {

    @Test
    public void projectCreationTest() {
        Project project1 = new Project();

        Project project2 = new Project();
        project2.setName("TestProjectName");
    }
}