package models;

import enums.RequestStatus;

import java.util.UUID;

public class WorkRequest {
    String workrequest_id;
    User developer;
    Project project;
    RequestStatus requestStatus;

    public WorkRequest(User developer, Project project) {
        this.workrequest_id = UUID.randomUUID().toString();
        this.developer = developer;
        this.project = project;
        this.requestStatus = RequestStatus.PENDING;
    }

    public void updateRequestStatus(RequestStatus requestStatus)
    {
        this.requestStatus = requestStatus;
    }

    public String getWorkrequest_id() {
        return workrequest_id;
    }

    public User getDeveloper() {
        return developer;
    }

    public Project getProject() {
        return project;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
}
