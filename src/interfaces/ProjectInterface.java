package interfaces;

import models.Project;

import java.util.Map;

public interface ProjectInterface {
    void addProject(Project project);
    void removeProject(String project_id);
    Map<String, Project> getProjects();
}
