package repository;

import interfaces.ProjectInterface;
import models.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectRepo implements ProjectInterface {
    Map<String, Project> projects;
    private static ProjectRepo instance;


    private ProjectRepo()
    {
        projects = new HashMap<>();
    }
    public static ProjectRepo getInstance()
    {
        if(instance == null)
        {
            synchronized (ProjectRepo.class)
            {
                if(instance == null)
                {
                    instance =  new ProjectRepo();
                }
                return instance;
            }
        }
        return instance;
    }
    public void addProject(Project project)
    {
        projects.put(project.getProject_id(), project);
    }

    public void removeProject(String project_id)
    {
        projects.remove(project_id);
    }

    public Map<String, Project> getProjects() {
        return projects;
    }

    public Project getProject(String project_id)
    {
        return projects.get(project_id);
    }


}
