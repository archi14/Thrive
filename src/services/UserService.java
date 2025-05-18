package services;

import enums.Role;
import exceptions.UserNotFoundException;
import models.User;
import repository.UserRepo;

import java.util.Map;

public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public synchronized User registerUser(String name, Role role)
    {
        User user = new User(name, role);
        userRepo.addUser(user);
        return user;
    }


    public void getUserDetails(String user_id)
    {
       try{
           User user = userRepo.getUser(user_id);
           System.out.println(user.toString());
       }catch (UserNotFoundException e)
       {
           System.out.println(user_id + e.getMessage());
       }
    }

    public void getAllUserDetails()
    {
        for(Map.Entry<String, User> entry: userRepo.getUsers().entrySet())
        {
            getUserDetails(entry.getKey());
        }
    }
}
