package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;
import model.User;
import model.enums.Role;
import model.enums.Status;
import utility.EmailUtility;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();

        // Retrieve form inputs
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("re_password");
        String phone = request.getParameter("phone");

        // Check if email already exists
        if (userDAO.emailExists(email)) {
            session.setAttribute("notificationErr", "Email already exists. Please use a different email.");
            request.setAttribute("fullname", fullname);
            request.setAttribute("phone", phone);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("re_password", confirmPassword);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check if phone number already exists
        if (userDAO.phoneExists(phone)) {
            session.setAttribute("notificationErr", "Phone number already exists. Please use a different phone number.");
            request.setAttribute("fullname", fullname);
            request.setAttribute("phone", phone);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("re_password", confirmPassword);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Create a new User object
        User newUser = new User();
        newUser.setName(fullname);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setRole(Role.USER);  // Default role is USER
        newUser.setStatus(Status.INACTIVE);  // Default status is INACTIVE

        // Generate a token for activation
        String token = generateActivationToken();

        // Insert user into the database
        boolean success = userDAO.insertUser(newUser);
        if (success) {
            session.setAttribute("activationToken", token);  // Save token in session
            session.setAttribute("email", email);  // Save token in session
            // Prepare and send an email with an activation link
            String activationLink = getBaseUrl(request) + "/activate?token=" + token;
            String subject = "Activate your account";
            String message = "Hello " + fullname + ",\n\n"
                    + "Your account has been created successfully. Please click the following link to activate your account:\n"
                    + activationLink;

            EmailUtility.sendEmail(email, subject, message);

            // Registration successful, redirect to login with success message
            session.setAttribute("notification", "Registration successful! Please check your email to activate your account.");
            response.sendRedirect("login");
        } else {
            // Registration failed, forward back to the register page
            session.setAttribute("notificationErr", "Registration failed, please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Helper method to generate a secure activation token
    private String generateActivationToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[24];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    // Helper method to get the base URL for email activation link
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();              // http or https
        String serverName = request.getServerName();      // localhost or domain name
        int serverPort = request.getServerPort();         // 8080, 443, etc.
        String contextPath = request.getContextPath();    // /your-app-name

        if (serverPort == 80 || serverPort == 443) {
            return scheme + "://" + serverName + contextPath;
        } else {
            return scheme + "://" + serverName + ":" + serverPort + contextPath;
        }
    }
}
