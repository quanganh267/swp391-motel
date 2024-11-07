/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import static model.enums.Role.USER;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        //Check Email and Password in database:
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmailAndPassword(email, password);System.out.println(email +" "+password);
        if (user == null) {
            session.setAttribute("notificationErr", "The e-mail address and/or password you specified are not correct.");
            response.sendRedirect("login");
        } else if (user.getStatus().equals(Status.INACTIVE)) {
            session.setAttribute("notificationErr", "Please check your email address to active your account! ");
            response.sendRedirect("login");
        } else if (user.getStatus().equals(Status.BLOCKED)) {
            session.setAttribute("notificationErr", "Sorry! You had been blocked! ");
            response.sendRedirect("login");
        } else {
            session.setAttribute("account", user);
            switch (user.getRole()) {
                case USER ->
                    response.sendRedirect("home");
                case STAFF ->
                    response.sendRedirect("staff/dashboard");
                case ADMIN ->
                    response.sendRedirect("admin/dashboard");
                default ->
                    response.sendRedirect("home");
            }
        }

    }
}
