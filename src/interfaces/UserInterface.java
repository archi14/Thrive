package interfaces;

import models.User;

import java.util.Map;

public interface UserInterface {
    void addUser(User user);
    void removeUser(String user_id);
    Map<String, User> getUsers();
}

