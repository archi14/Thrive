import enums.ProjectCategory;
import enums.ProjectStatus;
import enums.Role;
import exceptions.InvalidProjectStatus;
import exceptions.InvalidRequestException;
import exceptions.UnauthorizedUserException;
import exceptions.UserNotFoundException;
import models.Project;
import models.User;
import repository.ProjectRepo;
import repository.UserRepo;
import repository.WorkRequestRepo;
import services.ProjectService;
import services.UserService;
import services.WorkRequestService;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws UserNotFoundException, UnauthorizedUserException, InvalidProjectStatus {
        UserRepo userRepo = UserRepo.getInstance();
        ProjectRepo projectRepo = ProjectRepo.getInstance();
        WorkRequestRepo workRequestRepo = WorkRequestRepo.getInstance();

        UserService userService = new UserService(userRepo);
        ProjectService projectService = new ProjectService(projectRepo);
        WorkRequestService workRequestService = new WorkRequestService(workRequestRepo, projectRepo);

        Scanner scanner = new Scanner(System.in);

        // Register users
        while(true)
        {
            String input = scanner.nextLine();
            input = input.trim();
            String [] inp = input.split(" ");
            switch(inp[0])
            {
                case "register_developer" -> {
                    User user = userService.registerUser(inp[1], Role.DEVELOPER);
                    System.out.println(user.toString());
                    break;
                }
                case "register_lead" -> {
                    User user = userService.registerUser(inp[1], Role.LEAD);
                    System.out.println(user.toString());
                    break;
                }
                case "register_project" -> {
                    // we are expecting input as (title, description, category , user_id)
                    User user = userRepo.getUser(inp[4]);
                    String cat = inp[3].toUpperCase();
                    ProjectCategory category = ProjectCategory.valueOf(cat);
                    try{
                        Project project = projectService.createProject(inp[1], inp[2], category ,user);
                        System.out.println(project.toString());
                    } catch (UnauthorizedUserException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "get_available_projects"->{
                    projectService.getAvailableProjects();
                }
                case "request_project" -> {
                    try{
                        // we expect the input to be in format (project_id, user_id)
                        User user = userRepo.getUser(inp[2]);
                        workRequestService.requestProject(inp[1], user);
                    } catch (UserNotFoundException | UnauthorizedUserException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "accept_request" -> {
                    try {
                        // we expect the input to be in form (project_id, request_id, user_id)
                        User user = userRepo.getUser(inp[3]);
                        workRequestService.acceptRequest(inp[1], inp[2], user);
                    } catch (UnauthorizedUserException | UserNotFoundException | InvalidRequestException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case "get_developer_details"->{
                    userService.getUserDetails(inp[1]);
                }

                case "get_project_details"-> {
                    projectService.getProjectDetails(inp[1]);
                }
                case "cancel_project"->{
                    User user = userRepo.getUser(inp[2]);
                    projectService.cancelProject(inp[1], user);
                }
                case "complete_project"->{
                    User user = userRepo.getUser(inp[2]);
                    projectService.completeProject(inp[1],user);
                }
                case "set_project_rating"->{
                    // we expect input to be in format project_id, user_id, rating
                    User user = userRepo.getUser(inp[2]);
                    projectService.setRatingProject(inp[1], user, Double.valueOf(inp[3]));
                }
                case "remove_expired_projects"->{
                    projectService.cancelExpiredProjects();
                }
                case "get_project"->{
                    String s = inp[1];
                    ProjectStatus projectStatus = ProjectStatus.valueOf(s.toUpperCase());
                    List<Project> projectList = projectService.getProject(projectStatus);
                    for(int i=0;i<projectList.size();i++)
                    {
                        System.out.println(projectList.get(i).toString());
                    }
                }
            }
        }


//        User archita = userService.registerUser("Archita", Role.DEVELOPER);
//        User bhawana = userService.registerUser("bhawana", Role.LEAD);

//        try{
//
//            // creating the project
//            Project project = projectService.createProject("pm","pm", ProjectCategory.BACKEND, bhawana);
//            projectService.getAvailableProjects();
//            WorkRequest request =
//            workRequestService.acceptRequest(project.getProject_id(), request.getWorkrequest_id(), bhawana);
//
//            userService.getUserDetails(archita.getUser_id());
//
//            projectService.getProjectDetails(project.getProject_id());
//
//            projectService.cancelProject(project.getProject_id(), bhawana);
//            projectService.completeProject(project.getProject_id(), archita);
//        }catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }






    }
}