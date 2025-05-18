package services;

import enums.ProjectCategory;
import enums.ProjectStatus;
import enums.Role;
import exceptions.InvalidProjectStatus;
import exceptions.UnauthorizedUserException;
import models.Project;
import models.User;
import repository.ProjectRepo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectService {
    private final ProjectRepo projectRepo;
    public ProjectService(ProjectRepo projectRepo)
    {
        this.projectRepo = projectRepo;
    }

    public synchronized Project createProject(String title, String description, ProjectCategory projectCategory, User lead) throws UnauthorizedUserException {
            if(lead.getRole() != Role.LEAD)
            {
                throw new UnauthorizedUserException("Only leads can create a project");
            }
            Project project = new Project(title, description, projectCategory, lead);
            projectRepo.addProject(project);
            return project;

    }

    public void getProjectDetails(String project_id)
    {
        Project project = projectRepo.getProject(project_id);
        System.out.println(project.getProject_id() + " " + project.getCreatedUser() + " " + project.getDescription() + " " + project.getTitle());

    }

    public List<Project> getProject(ProjectStatus projectStatus)
    {
        List<Project> projects = new ArrayList<>();
        Map<String, Project> projectsMap = projectRepo.getProjects();
        for(Map.Entry<String, Project> entry: projectsMap.entrySet())
        {
            if(projectStatus == entry.getValue().getProjectStatus())
            {
                projects.add(entry.getValue());
            }
        }
        return projects;
    }
    public void getAvailableProjects()
    {
        Map<String, Project> allProjects = projectRepo.getProjects();
        for(Map.Entry<String, Project> entry: allProjects.entrySet())
        {
            if(entry.getValue().getProjectStatus() == ProjectStatus.ACTIVE)
            {
                System.out.println(entry.getValue().toString());
            }
        }
    }


    public synchronized void cancelProject(String project_id, User lead) throws UnauthorizedUserException, InvalidProjectStatus {
            if(lead.getRole() != Role.LEAD)
            {
                throw new UnauthorizedUserException("Only leads can create a project");
            }

            Project project = projectRepo.getProject(project_id);
            if(project.getProjectStatus() == ProjectStatus.ASSIGNED)
            {
                throw new InvalidProjectStatus("Project has already been assigned, cannot cancel it");
            }

            project.setProjectStatus(ProjectStatus.CANCELLED);
            project.cancelAllRequests();
            System.out.println("Project " + project_id + " has been successfully cancelled");


    }

    public synchronized void completeProject(String project_id, User user) throws UnauthorizedUserException {
            if(user.getRole() == Role.LEAD)
            {
                throw new UnauthorizedUserException("Only developers can complete a project");
            }
            Project project = projectRepo.getProject(project_id);
            if(project.getProjectStatus() == ProjectStatus.ACTIVE || project.getProjectStatus() == ProjectStatus.IN_PROGRESS)
            {
                project.setProjectStatus(ProjectStatus.COMPLETED);
                System.out.println("Project " + project_id + " has been successfully completed by " + user.getUser_id() );
            }else {
                System.out.println("Project is not in active or in progress state");
            }

    }

    // cancelling all project that have been active before current time
    public synchronized void cancelExpiredProjects()
    {
            LocalTime time = LocalTime.now();
            Map<String, Project> projectMap = projectRepo.getProjects();
            for(Map.Entry<String, Project> entry: projectMap.entrySet())
            {
                if(entry.getValue().getProjectStatus() == ProjectStatus.ACTIVE && entry.getValue().getCreationTime().isBefore(time))
                {
                    entry.getValue().setProjectStatus(  ProjectStatus.CANCELLED);
                }
            }
    }

    public synchronized void setRatingProject(String project_id, User lead, Double rating) throws UnauthorizedUserException {
        if(lead.getRole() != Role.LEAD)
        {
            throw new UnauthorizedUserException("Only leaders can rate a project");
        }
        Project project = projectRepo.getProject(project_id);
        if(project.getProjectStatus()!=ProjectStatus.COMPLETED)
        {
            System.out.println("Project isn't completed yet");
            return;
        }
        project.setRating(rating);
        System.out.println("Project " + project_id + " has been successfully rated with rating " + rating);
    }



}
