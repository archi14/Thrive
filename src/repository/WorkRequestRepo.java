package repository;

import interfaces.WorkRequestInterface;
import models.WorkRequest;

import java.util.HashMap;
import java.util.Map;

public class WorkRequestRepo implements WorkRequestInterface {
    Map<String, WorkRequest> workRequests;
    private static WorkRequestRepo instance;
    private WorkRequestRepo()
    {
        workRequests = new HashMap<>();
    }

    public static WorkRequestRepo getInstance()
    {
        if(instance == null)
        {
            synchronized (ProjectRepo.class)
            {
                if(instance == null)
                {
                    instance =  new WorkRequestRepo();
                }
                return instance;
            }
        }
        return instance;
    }
    public void addRequest(WorkRequest request)
    {
        workRequests.put(request.getWorkrequest_id(), request);
    }

    public void removeRequest(String request_id)
    {
        workRequests.remove(request_id);
    }

    public WorkRequest getRequest(String request_id)
    {
        return workRequests.get(request_id);
    }

}

