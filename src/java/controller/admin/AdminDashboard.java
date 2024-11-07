/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;
import model.enums.Role;

@WebServlet(name = "AdminDashboard", urlPatterns = {"/admin/dashboard"})
public class AdminDashboard extends HttpServlet {

    final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null) {
            if (user.getRole().equals(Role.ADMIN)) {

                
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            } else {
                session.setAttribute("notificationErr", "Access Denined! ");
                response.sendRedirect("../login");
            }
        } else {
            session.setAttribute("notificationErr", "Please login first! ");
            response.sendRedirect("../login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
