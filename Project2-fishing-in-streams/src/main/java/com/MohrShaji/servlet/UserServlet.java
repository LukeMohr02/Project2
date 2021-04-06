package com.MohrShaji.servlet;

import com.MohrShaji.application.ReimbursementManager;
import com.MohrShaji.application.UserManager;
import com.MohrShaji.controller.ReimbursementController;
import com.MohrShaji.controller.UserController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserController uc = new UserController(new UserManager());
        String urlEnd = request.getRequestURI().replaceFirst(".*/user/", "");

        switch(urlEnd) {
            case "create":
                uc.createUser(request, response);
                break;
            case "delete":
                uc.deleteUser(request, response);
                break;
            case "get":
                uc.getUser(request, response);
                break;
            case "list":
                uc.getAllUsers(response);
                break;
            case "update":
                uc.updateUser(request, response);
                break;
            default:
                break;
        }
    }
}
