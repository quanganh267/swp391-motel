package controller.staff;

import dal.RentContactDAO;
import model.RentContact;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RentContactManagement", urlPatterns = {"/staff/rent-contact"})
public class RentContactServlet extends HttpServlet {

    private RentContactDAO rentContactDAO = new RentContactDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<RentContact> rentContacts = rentContactDAO.getAllRentContacts();
            request.setAttribute("rentContacts", rentContacts);
            request.getRequestDispatcher("/staff/rent-contact.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain rent contacts from DB", e);
        }
    }
}
