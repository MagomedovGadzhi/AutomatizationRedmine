package automatization.redmine.db.requests;

import automatization.redmine.db.connection.PostgresConnection;
import automatization.redmine.db.requests.interfases.*;
import automatization.redmine.model.project.Project;
import automatization.redmine.model.project.ProjectStatus;

import java.util.List;
import java.util.Map;

public class ProjectRequest extends BaseRequests implements Create<Project>, Delete, Read<Project>, Update<Project> {
    @Override
    public void create(Project project) {
        String query = "INSERT INTO public.projects\n" +
                "(id, \"name\", description, homepage, is_public, parent_id, created_on, updated_on, identifier, " +
                "status, lft, rgt, inherit_members, default_version_id, default_assigned_to_id)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
        Integer projectId = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getProjectStatus().statusCode,
                project.getIft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId()
        ).get(0).get("id");
        project.setId(projectId);
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.projects\n WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                id
        );
    }

    @Override
    public Project read(Integer id) {
        String query = "SELECT id, \"name\", description, homepage, is_public, parent_id, created_on, updated_on, identifier, " +
                "status, lft, rgt, inherit_members, default_version_id, default_assigned_to_id\n" +
                "FROM public.projects\n" +
                "WHERE id=?;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, id);
        return from(queryResult.get(0));
    }

    @Override
    public void update(Integer id, Project project) {
        String query = "UPDATE public.projects\n" +
                "SET \"name\"=?, description=?, homepage=?, is_public=?, parent_id=?, created_on=?, updated_on=?, identifier=?, " +
                "status=?, lft=?, rgt=?, inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getProjectStatus().statusCode,
                project.getIft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId(),
                id
        );
    }

    // Для полей, которые редко используются добавлены проверки на NULL, во избежании ошибок.
    private Project from(Map<String, Object> data) {
        return (Project) new Project()
                .setName((String) data.get("name"))
                .setDescription((String) data.get("description"))
                .setHomepage(checkIsStringNull(data.get("homepage")))           //проверка, что поле не нульное
                .setIsPublic((Boolean) (data.get("is_public")))
                .setParentId(checkIsIntegerNull(data.get("parent_id")))        //проверка, что поле не нульное
                .setIdentifier((String) (data.get("identifier")))
                .setProjectStatus(ProjectStatus.getProjectStatusByCode((int) data.get("status")))
                .setIft(checkIsIntegerNull(data.get("ift")))
                .setRgt(checkIsIntegerNull(data.get("rgt")))
                .setInheritMembers((Boolean) data.get("inherit_members"))
                .setDefaultVersionId(checkIsIntegerNull(data.get("default_version_id")))        //проверка, что поле не нульное
                .setDefaultAssignedToId(checkIsIntegerNull(data.get("default_assigned_to_id")))    //проверка, что поле не нульное
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setId((Integer) data.get("id"));
    }
}