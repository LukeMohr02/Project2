package com.MohrShaji.controller;

import com.MohrShaji.model.Reimbursement;
import com.MohrShaji.application.ReimbursementManager;
import com.google.gson.GsonBuilder;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ReimbursementController {

    ReimbursementManager rm;

    public ReimbursementController() {

    }

    public ReimbursementController(ReimbursementManager rm) {
        this.rm = rm;
    }

    public void getAllReimbursements(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        List<Reimbursement> reimbursements = rm.listReimbursements();

        response.getWriter().println("List of all reimbursements:\n\n");

        for (Reimbursement r : reimbursements) {

            try {
                r.prepForGson();
            } catch (NullPointerException e) {
                continue;
            }

            response.getWriter().println(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(r));
        }
    }

    public void createReimbursement(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException {
        response.setContentType("application/json");

        String amount = request.getParameter("amount");
        String submitted = request.getParameter("submitted");
        String resolved = request.getParameter("resolved");
        String description = request.getParameter("description");
        String author = request.getParameter("author");
        String resolver = request.getParameter("resolver");
        String statusId = request.getParameter("status_id");
        String typeId = request.getParameter("type_id");

        float amountFloat = 0;
        Timestamp submittedTS = Timestamp.valueOf(LocalDateTime.now());
        Timestamp resolvedTS = Timestamp.valueOf(LocalDateTime.now());
        int authorInt = 0;
        int resolverInt = 0;
        int statusIdInt = 0;
        int typeIdInt = 0;

        if (amount != null && !amount.equals("")) {
            try {
                amountFloat = Float.parseFloat(amount);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'amount', please enter a float.");
                return;
            }
        }

        if (submitted != null && !submitted.equals("")) {
            try {
                submittedTS = Timestamp.valueOf(submitted);
            } catch (IllegalArgumentException e) {
                response.getWriter().println("Invalid 'submitted' timestamp, please enter a valid timestamp string.");
                return;
            }
        } else {
            submittedTS = Timestamp.valueOf(LocalDateTime.now());
        }

        if (resolved != null && !resolved.equals("")) {
            try {
                resolvedTS = Timestamp.valueOf(resolved);
            } catch (IllegalArgumentException e) {
                response.getWriter().println("Invalid 'resolved' timestamp, please enter a valid timestamp string.");
                return;
            }
        } else {
            resolvedTS = Timestamp.valueOf(LocalDateTime.now());
        }

        if (author != null) {
            try {
                authorInt = Integer.parseInt(author);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'author', please enter an integer.");
                return;
            }
        }

        if (resolver != null) {
            try {
                resolverInt = Integer.parseInt(resolver);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'resolver', please enter an integer.");
                return;
            }
        }

        if (statusId != null) {
            try {
                statusIdInt = Integer.parseInt(statusId);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'status_id', please enter an integer.");
                return;
            }
        }

        if (typeId != null) {
            try {
                typeIdInt = Integer.parseInt(typeId);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid 'type_id', please enter an integer.");
                return;
            }
        }

        try {
            response.getWriter().println("Created new reimbursement:\n\n" +
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(
                rm.createReimbursement(amountFloat, submittedTS, resolvedTS, description, authorInt, resolverInt, statusIdInt, typeIdInt)));
        } catch (PersistenceException e) {
            response.getWriter().println("'author' and/or 'resolver' values do not point to valid user IDs.");
        }
    }

    public void updateReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        String amount = request.getParameter("amount");
        String resolver = request.getParameter("resolver");
        int idInt;
        float amountFloat;
        int resolverInt;

        if (id == null) {
            response.getWriter().write("Please enter an 'id' as an integer.");
            return;
        }

        if (amount == null) {
            response.getWriter().write("Please enter an 'amount' as a float.");
            return;
        }

        if (resolver == null) {
            response.getWriter().write("Please enter a 'resolver' as an integer.");
            return;
        }

        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid 'id' input, please enter an integer.\n");
            return;
        }

        try {
            amountFloat = Float.parseFloat(amount);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid 'amount' input, please enter a float.\n");
            return;
        }

        try {
            resolverInt = Integer.parseInt(resolver);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid 'resolver' input, please enter an integer.\n");
            return;
        }

        try {
            Reimbursement r = rm.updateReimbursement(idInt, amountFloat, resolverInt);
            r.prepForGson();

            response.getWriter().println("Updated reimbursement:\n\n" +
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(r));
        } catch (NumberFormatException | NullPointerException e) {
            response.getWriter().write("No reimbursement with 'id' "+idInt+" in the database.");
        } catch (PersistenceException e) {
            response.getWriter().println("No resolver with 'id' "+resolverInt+" in the database.");
        }
    }

    public void deleteReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        int idInt = 0;

        try {
            idInt = Integer.parseInt(id);
            Reimbursement r = rm.deleteReimbursement(idInt);
            r.prepForGson();

            try {
                response.getWriter().println("Deleted reimbursement:\n\n" +
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(r));
            } catch(IllegalArgumentException e) {
                response.getWriter().println("Invalid 'id', please enter a valid integer id");
            }

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid 'id', please enter a valid integer id");
        } catch (NullPointerException | IllegalArgumentException e) {
            response.getWriter().write("No reimbursement with 'id' "+idInt+" in the database.");
        }
    }

    public void getReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        String userId = request.getParameter("user_id");

        int idInt = 0;
        int userIdInt = 0;

        if (id != null) {

            try {
                idInt = Integer.parseInt(id);
                Reimbursement r = rm.getById(idInt);
                r.prepForGson();

                response.getWriter().println("Found reimbursement:\n\n" +
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(r));
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid 'id' input, please enter an integer.\n");
            } catch (NullPointerException e) {
                response.getWriter().write("No reimbursement with 'id' "+idInt+" in the database.");
            }

        } else if (userId != null) {

            try {
                userIdInt = Integer.parseInt(userId);
                List<Reimbursement> reimbursements = rm.getByUserId(userIdInt);

                if (reimbursements != null && reimbursements.size() > 0) {
                    response.getWriter().println("Found reimbursements:\n\n");

                    for (Reimbursement r : reimbursements) {
                        r.prepForGson();

                        response.getWriter().println(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(r));
                    }
                } else {
                    response.getWriter().println("No reimbursements found for 'user_id' "+userIdInt);
                }

            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid 'user_id' input, please enter an integer.\n");
            } catch (NullPointerException e) {
                response.getWriter().write("No user with 'user_id' "+userIdInt+" in the database.");
            }

        } else {
            response.getWriter().write("Please specify either an 'id' or a 'user_id'.");
        }
    }
}
