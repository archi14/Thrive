package services;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.Role;
import exceptions.InvalidRequestException;
import exceptions.UnauthorizedUserException;
import models.Project;
import models.User;
import models.WorkRequest;
import repository.ProjectRepo;
import repository.WorkRequestRepo;

import java.util.List;

public class WorkRequestService {
    private final WorkRequestRepo workRequestRepo;
    private final ProjectRepo projectRepo;

    public WorkRequestService(WorkRequestRepo workRequestRepo, ProjectRepo projectRepo)
    {
        this.projectRepo = projectRepo;
        this.workRequestRepo = workRequestRepo;
    }

    public synchronized WorkRequest requestProject(String project_id, User developer) throws UnauthorizedUserException {
        if(developer.getRole() == Role.LEAD)
        {
            throw new UnauthorizedUserException("Lead cannot request a project");
        }
        Project project = projectRepo.getProject(project_id);
        WorkRequest request = new WorkRequest(developer, project);
        project.addWorkRequest(request);
        workRequestRepo.addRequest(request);
        System.out.println("Project " + project_id + " with request id " + request.getWorkrequest_id() + " been requested by " + developer.getName() + " " + developer.getUser_id());
        return request;
    }


    public List<WorkRequest> getRequestsForProject(String project_id, User lead) throws UnauthorizedUserException {
        Project project = projectRepo.getProject(project_id);
        if(project.getCreatedUser() != lead)
        {
            throw new UnauthorizedUserException("User cannot access requests of this project");
        }
        return project.getWorkRequests();
    }

    public synchronized void acceptRequest(String project_id, String request_id, User user) throws UnauthorizedUserException, InvalidRequestException {
        if(user.getRole() != Role.LEAD)
        {
            throw new UnauthorizedUserException("developer cann't accept a request");
        }

        Project project = projectRepo.getProject(project_id);
        WorkRequest request = workRequestRepo.getRequest(request_id);

        // approved the request
        project.getRequest(request_id).updateRequestStatus(RequestStatus.APPROVED);
        request.updateRequestStatus(RequestStatus.APPROVED);
        System.out.println("Request " + request_id + " has been successfully approved");
        // mark the project as assigned
        project.setProjectStatus(ProjectStatus.ASSIGNED);
    }

    public void rejectRequest(String project_id, String request_id, User user) throws UnauthorizedUserException, InvalidRequestException {
        if(user.getRole() != Role.LEAD)
        {
            throw new UnauthorizedUserException("developer cann't reject a request");
        }

        Project project = projectRepo.getProject(project_id);
        WorkRequest request = workRequestRepo.getRequest(request_id);

        // approved the request
        project.getRequest(request_id).updateRequestStatus(RequestStatus.REJECTED);
        request.updateRequestStatus(RequestStatus.REJECTED);
    }



}
