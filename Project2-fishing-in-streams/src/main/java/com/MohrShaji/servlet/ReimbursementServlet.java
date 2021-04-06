package com.MohrShaji.servlet;

import com.MohrShaji.controller.ReimbursementController;
import com.MohrShaji.application.ReimbursementManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/reimbursement/*")
public class ReimbursementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ReimbursementController rc = new ReimbursementController(new ReimbursementManager());
        String urlEnd = request.getRequestURI().replaceFirst(".*/reimbursement/", "");

        switch(urlEnd) {
            case "create":
                rc.createReimbursement(request, response);
                break;
            case "delete":
                rc.deleteReimbursement(request, response);
                break;
            case "get":
                rc.getReimbursement(request, response);
                break;
            case "list":
                rc.getAllReimbursements(response);
                break;
            case "update":
                rc.updateReimbursement(request, response);
                break;
            default:
                break;
        }
    }
}
