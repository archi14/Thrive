package interfaces;

import models.WorkRequest;

public interface WorkRequestInterface {
    void addRequest(WorkRequest request);
    void removeRequest(String request_id);
    WorkRequest getRequest(String request_id);
}
