package com.MohrShaji.controller;

import com.MohrShaji.application.UserManager;
import com.MohrShaji.model.User;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class UserController {

    UserManager um;

    public UserController() {

    }

    public UserController(UserManager um) {
        this.um = um;
    }

    public void getAllUsers(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        List<User> users = um.listUsers();
        Comparator<User> byUserId = Comparator.comparing(User::getUser_id);
        users.sort(byUserId);
        response.getWriter().println("List of all users:\n\n");

        for (User u : users) {

            try {
                u.prepForGson();
            } catch (NullPointerException e) {
                continue;
            }

            response.getWriter().println(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(u));
        }
    }

    public void createUser(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException {
        response.setContentType("application/json");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String roleId = request.getParameter("role_id");

        int roleIdInt = 0;

        if (roleId != null) {
            try {
                roleIdInt = Integer.parseInt(roleId);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'role_id', please enter an integer.");
                return;
            }
        }

        User user = um.createUser(username, password, firstName, lastName, email, roleIdInt);
        user.prepForGson();

        response.getWriter().println("Created new user:\n\n" +
            new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user));
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String userId = request.getParameter("id");
        String username = request.getParameter("username");
        int userIdInt;

        try {
            userIdInt = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid 'id' input, please enter an integer.\n");
            return;
        }

        if (username == null) {
            response.getWriter().write("Please enter a 'username'.");
            return;
        }

        try {
            User user = um.updateUser(userIdInt, username);
            user.prepForGson();

            response.getWriter().println("Updated user:\n\n" +
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user));
        } catch (NullPointerException | IllegalArgumentException e) {
            response.getWriter().write("No user with 'id' "+userIdInt+" in the database.");
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String userId = request.getParameter("id");
        int userIdInt = 0;

        try {
            userIdInt = Integer.parseInt(userId);
            User user = um.deleteUser(userIdInt);
            user.prepForGson();

            try {
                response.getWriter().println("Deleted user:\n\n" +
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user));
            } catch(IllegalArgumentException e) {
                response.getWriter().println("Invalid 'id', please enter a valid integer id");
            }

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid 'id', please enter a valid integer id");
            return;
        } catch (NullPointerException | IllegalArgumentException e) {
            response.getWriter().write("No user with 'id' "+userIdInt+" in the database.");
        }

    }

    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String userId = request.getParameter("id");
        String username = request.getParameter("username");
        int userIdInt = 0;

        if (userId != null) {

            try {
                userIdInt = Integer.parseInt(userId);
                User user = um.getByUserId(userIdInt);
                user.prepForGson();

                response.getWriter().println("Found user:\n\n" +
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user));

            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid 'id' input, please enter an integer.\n");
            } catch (NullPointerException e) {
                response.getWriter().write("No user with 'id' "+userIdInt+" in the database.");
            }

        } else if (username != null) {
                User user = um.getByUsername(username);
                user.prepForGson();

            response.getWriter().println("Found user:\n\n" +
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user));

        } else {
            response.getWriter().write("Please specify either an 'id' or a 'username'.");
        }
    }
}
