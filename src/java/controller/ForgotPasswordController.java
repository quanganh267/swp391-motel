/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import model.User;
import utility.EmailUtility;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO udao = new UserDAO();
        String email = request.getParameter("email");
        User existedUser = udao.getUserIdByEmail(email);

        if (existedUser == null) {
            session.setAttribute("notificationErr", "This email not exist in our system!");
            response.sendRedirect("forgot-password");
        } else {
            // Generate a 6-digit code
            String code = generateVerificationCode();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);  // Set expiration time to 5 minutes from now

            // Store the code and expiration time in the session
            session.setAttribute("verificationCode", code);
            session.setAttribute("codeExpirationTime", expirationTime);
            session.setAttribute("email", email);  // Store the email in session to use later

            // Prepare the email message
            String subject = "Password Reset Code";
            String message = "Dear " + existedUser.getName() + ", \n\n"
                    + "Your password reset code is: " + code + "\n"
                    + "Please use this code to reset your password within the next 5 minutes.\n\n"
                    + "Thank you.";


            // Send the email
            EmailUtility.sendEmail(email, subject, message);
            session.setAttribute("notification", "We sent an email to reset your password to "+email+" !");
            response.sendRedirect("reset-password");

        }
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);  // Generate a random 6-digit number
        return String.valueOf(code);
    }
}
