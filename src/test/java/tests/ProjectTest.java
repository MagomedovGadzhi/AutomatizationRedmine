package tests;

import automatization.redmine.model.project.Project;
import org.testng.annotations.Test;

public class ProjectTest {

    @Test
    public void projectCreationTest() {
        Project project1 = new Project();
        project1.create();

        project1.setIsPublic(false);
        project1.update();

        project1.delete();
    }
}