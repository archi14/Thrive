package repository;

import exceptions.UserNotFoundException;
import interfaces.UserInterface;
import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepo implements UserInterface {
    Map<String, User> users;
    private static UserRepo instance;
    private UserRepo()
    {
        users = new HashMap<>();
    }

    public static UserRepo getInstance()
    {
        if(instance == null)
        {
            synchronized (ProjectRepo.class)
            {
                if(instance == null)
                {
                    instance =  new UserRepo();
                }
                return instance;
            }
        }
        return instance;
    }
    public void addUser(User user)
    {
        users.put(user.getUser_id(), user);
    }
    public void removeUser(String user_id)
    {
        users.remove(user_id);
    }

    public User getUser(String user_id) throws UserNotFoundException {
        if(users.get(user_id)==null)
        {
            throw new UserNotFoundException("User isn't present in the system");
        }
        return users.get(user_id);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
