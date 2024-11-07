package controller.admin;

import dal.GuestDAO;
import model.Guest;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GuestManagementServlet", urlPatterns = {"/admin/guest-management"})
public class GuestManagementServlet extends HttpServlet {

    private GuestDAO guestDAO;

    @Override
    public void init() throws ServletException {
        guestDAO = new GuestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Guest> guests = guestDAO.getAllGuests();
            request.setAttribute("guests", guests);
            request.getRequestDispatcher("/admin/GuestManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain guests from DB", e);
        }
    }
} 