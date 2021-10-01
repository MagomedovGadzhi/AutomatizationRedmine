package automatization.redmine.db.requests;

import automatization.redmine.db.requests.interfases.*;
import automatization.redmine.model.project.Project;

import java.util.List;

public class ProjectRequest extends BaseRequests implements Create<Project>, Delete, Read<Project>, ReadAll<Project>, Update<Project> {
    @Override
    public void create(Project project) {
        //TODO
    }

    @Override
    public void delete(Integer id) {
        //TODO
    }

    @Override
    public Project read(Integer id) {
        return null;
        //TODO
    }

    @Override
    public List<Project> readAll() {
        return null;
        //TODO
    }

    @Override
    public void update(Integer id, Project project) {
        //TODO
    }
}
