package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ActivateController", urlPatterns = {"/activate"})
public class ActivateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();

        // Retrieve the activation token from the request
        String token = request.getParameter("token");
        System.out.println(token);
        // Retrieve the token stored in the session during registration
        String sessionToken = (String) session.getAttribute("activationToken");
        System.out.println(sessionToken);

        if (token == null || sessionToken == null || !token.equals(sessionToken)) {
            // Invalid or missing token
            session.setAttribute("notificationErr", "Invalid or expired activation token.");
            response.sendRedirect("login");
        } else {
            String email = (String) session.getAttribute("email");
            userDAO.activateUserByEmail(email);
            session.setAttribute("notification", "Your account has been successfully activated.");
            // Clear the token from the session after activation
            session.removeAttribute("activationToken");
            session.removeAttribute("email");
            response.sendRedirect("login");
        }
    }
}
