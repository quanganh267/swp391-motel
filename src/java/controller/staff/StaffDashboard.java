/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.enums.Role;

@WebServlet(name = "StaffDashboard", urlPatterns = {"/staff/dashboard"})
public class StaffDashboard extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null) {
            if (user.getRole().equals(Role.STAFF)) {

                
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
