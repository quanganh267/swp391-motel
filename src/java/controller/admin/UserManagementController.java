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

@WebServlet(name = "UserManagementController", urlPatterns = {"/admin/user-management"})
public class UserManagementController extends HttpServlet {

    final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null) {
            if (user.getRole().equals(Role.ADMIN)) {

                String pageParam = request.getParameter("page");
                String searchParam = request.getParameter("search");
                String statusParam = request.getParameter("status");
                System.out.println(statusParam);
                int page = 1;
                int pageSize = 6;
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                String status = null;
                if (statusParam != null && !statusParam.isEmpty()) {
                    status = statusParam;
                }
                List<User> users = userDAO.getAllUserWithParam(searchParam, status);
                List<User> pagingUser = userDAO.Paging(users, page, pageSize);
                request.setAttribute("list", pagingUser);
                request.setAttribute("title", "User managemnt");
                request.setAttribute("totalPages", users.size() % pageSize == 0 ? (users.size() / pageSize) : (users.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("user-list.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");

        if (currentUser != null) {
            if (currentUser.getRole().equals(Role.ADMIN)) {
                String action = request.getParameter("action");
                String userIdStr = request.getParameter("userId");

                if (action != null && userIdStr != null) {
                    int userId = Integer.parseInt(userIdStr);
                    User targetUser = userDAO.getUserById(userId); // Fetch the target user by ID

                    if (targetUser != null) {
                        // Check if the target user is an admin
                        if (targetUser.getRole().equals(Role.ADMIN)) {
                            session.setAttribute("notificationErr", "You cannot block or unblock an admin!");
                        } else {
                            boolean success = false;

                            // Perform block or unblock based on action
                            if (action.equalsIgnoreCase("BLOCK")) {
                                success = userDAO.updateUserStatus(userId, "BLOCKED");
                            } else if (action.equalsIgnoreCase("UNBLOCK")) {
                                success = userDAO.updateUserStatus(userId, "ACTIVE");
                            }

                            if (success) {
                                session.setAttribute("notification", "User status updated successfully!");
                            } else {
                                session.setAttribute("notificationErr", "Failed to update user status!");
                            }
                        }
                    } else {
                        session.setAttribute("notificationErr", "User not found!");
                    }

                    response.sendRedirect("user-management");
                }
            } else {
                session.setAttribute("notificationErr", "Access Denied!");
                response.sendRedirect("../login");
            }
        } else {
            session.setAttribute("notificationErr", "Please login first!");
            response.sendRedirect("../login");
        }
    }
}
