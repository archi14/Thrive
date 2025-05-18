package models;

import enums.ProjectCategory;
import enums.ProjectStatus;
import exceptions.InvalidRequestException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    Double rating;
    String project_id;
    String title;
    String description;
    ProjectStatus projectStatus;
    List<WorkRequest> workRequests;
    ProjectCategory projectCategory;
    User createdUser;
    LocalTime creationTime;

    public Project(String title, String description, ProjectCategory projectCategory, User createdUser) {
        this.project_id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.projectCategory = projectCategory;
        this.createdUser = createdUser;
        this.projectStatus = ProjectStatus.ACTIVE;
        this.creationTime = LocalTime.now();
        workRequests = new ArrayList<>();
        rating = null;
    }
    public WorkRequest getRequest(String request_id) throws InvalidRequestException {
        for(int i=0;i<workRequests.size();i++)
        {
            if(workRequests.get(i).getWorkrequest_id().equals(request_id))
            {
                return workRequests.get(i);
            }
        }
        throw new InvalidRequestException(request_id + " is an invalid Request Id");
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setProjectStatus(ProjectStatus projectStatus)
    {
        this.projectStatus = projectStatus;
    }

    public void addWorkRequest(WorkRequest workRequest)
    {
        workRequests.add(workRequest);
    }

    public String getProject_id() {
        return project_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public List<WorkRequest> getWorkRequests() {
        return workRequests;
    }

    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public void cancelAllRequests()
    {
        workRequests = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id='" + project_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", projectStatus=" + projectStatus +
                ", workRequests=" + workRequests +
                ", projectCategory=" + projectCategory +
                ", createdUser=" + createdUser +
                ", creationTime=" + creationTime +
                '}';
    }
}
