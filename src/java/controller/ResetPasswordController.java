package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ResetPasswordController", urlPatterns = {"/reset-password"})
public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO udao = new UserDAO();

        String email = (String) session.getAttribute("email");
        String sessionCode = (String) session.getAttribute("verificationCode");
        LocalDateTime expirationTime = (LocalDateTime) session.getAttribute("codeExpirationTime");

        String inputCode = request.getParameter("code");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (LocalDateTime.now().isAfter(expirationTime)) {
            session.setAttribute("notificationErr", "Verification code has expired. Please request a new code.");
            response.sendRedirect("forgot-password");
            return;
        }
        // Verify the code
        if (!inputCode.equals(sessionCode)) {
            session.setAttribute("notificationErr", "Invalid verification code.");
            response.sendRedirect("reset-password");
            return;
        }

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("notificationErr", "Passwords do not match.");
            response.sendRedirect("reset-password");
            return;
        }

        // Update the password in the database
        boolean success = udao.updatePassword(email, newPassword);
        if (success) {
            session.setAttribute("notification", "Password successfully updated. You can now log in.");
            session.removeAttribute("verificationCode");
            session.removeAttribute("email");
            session.removeAttribute("codeExpirationTime");

            response.sendRedirect("login");
        } else {
            session.setAttribute("notificationErr", "Failed to reset password. Please try again.");
            response.sendRedirect("reset-password");
        }
    }
}
