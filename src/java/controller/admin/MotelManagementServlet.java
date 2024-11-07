package controller.admin;

import dal.MotelDAO;
import model.Motel;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MotelManagementServlet", urlPatterns = {"/admin/motel-management"})
public class MotelManagementServlet extends HttpServlet {

    private MotelDAO motelDAO;

    @Override
    public void init() throws ServletException {
        motelDAO = new MotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Motel> motels = motelDAO.getAllMotels();
            request.setAttribute("motels", motels);
            request.getRequestDispatcher("/admin/MotelManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain motels from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addMotel(request, response);
        } else if ("edit".equals(action)) {
            editMotel(request, response);
        } else if ("delete".equals(action)) {
            deleteMotel(request, response);
        }
    }

    private void addMotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String motelName = request.getParameter("motelName");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        int numberOfRoom = Integer.parseInt(request.getParameter("numberOfRoom"));
        String imageUrl = request.getParameter("imageUrl");

        Motel motel = new Motel();
        motel.setMotelName(motelName);
        motel.setDescription(description);
        motel.setAddress(address);
        motel.setCity(city);
        motel.setNumberOfRoom(numberOfRoom);
        motel.setImageUrl(imageUrl);

        if (motelDAO.insertMotel(motel)) {
            request.getSession().setAttribute("notification", "Motel added successfully!");
        } else {
            request.getSession().setAttribute("notificationErr", "Failed to add motel.");
        }
        response.sendRedirect("motel-management");
    }

    private void editMotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int motelId = Integer.parseInt(request.getParameter("motelId"));
        String motelName = request.getParameter("motelName");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        int numberOfRoom = Integer.parseInt(request.getParameter("numberOfRoom"));
        String imageUrl = request.getParameter("imageUrl");

        Motel motel = new Motel();
        motel.setMotelId(motelId);
        motel.setMotelName(motelName);
        motel.setDescription(description);
        motel.setAddress(address);
        motel.setCity(city);
        motel.setNumberOfRoom(numberOfRoom);
        motel.setImageUrl(imageUrl);

        if (motelDAO.updateMotel(motel)) {
            request.getSession().setAttribute("notification", "Motel updated successfully!");
        } else {
            request.getSession().setAttribute("notificationErr", "Failed to update motel.");
        }
        response.sendRedirect("motel-management");
    }

    private void deleteMotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int motelId = Integer.parseInt(request.getParameter("motelId"));

        if (motelDAO.deleteMotel(motelId)) {
            request.getSession().setAttribute("notification", "Motel deleted successfully!");
        } else {
            request.getSession().setAttribute("notificationErr", "Failed to delete motel.");
        }
        response.sendRedirect("motel-management");
    }
} 