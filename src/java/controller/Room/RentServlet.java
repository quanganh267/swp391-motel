package controller.Room;

import dal.RentContactDAO;
import dal.RoomDAO;
import model.RentContact;
import model.Room;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet(name = "Rent", urlPatterns = { "/Rent" })
public class RentServlet extends HttpServlet {

    private RentContactDAO dao = new RentContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomId = request.getParameter("roomID");
        if (roomId != null) {
            request.setAttribute("roomID", roomId);
        }
        RoomDAO roomDAO = new RoomDAO();
        try {
            List<Room> rooms = roomDAO.getAllRooms();
            request.setAttribute("rooms", rooms);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving rent contacts", e);
        }
        request.getRequestDispatcher("/Rent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String guestName = request.getParameter("guest_name");
        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");

        int roomId;

        try (PrintWriter out = response.getWriter()) {
            if (isNullOrEmpty(guestName) || isNullOrEmpty(phoneNumber) || isNullOrEmpty(address)) {
                out.println(createErrorMessage("All fields must be filled out."));
                return;
            }

            try {
                roomId = Integer.parseInt(request.getParameter("room_id"));
            } catch (NumberFormatException e) {
                out.println(createErrorMessage("Room ID must be a valid number."));
                return;
            }

            LocalDate startDate;
            LocalDate endDate;
            try {
                startDate = LocalDate.parse(startDateStr);
                endDate = LocalDate.parse(endDateStr);
            } catch (DateTimeParseException e) {
                out.println(createErrorMessage("Invalid date format. Please use YYYY-MM-DD."));
                return;
            }

            if (endDate.isBefore(startDate)) {
                out.println(createErrorMessage("End date must be after the start date."));
                return;
            }

            try {
                RentContact rentContact = new RentContact();
                rentContact.setGuestName(guestName);
                rentContact.setRoomId(roomId);
                rentContact.setStartDate(java.sql.Date.valueOf(startDate));
                rentContact.setEndDate(java.sql.Date.valueOf(endDate));
                rentContact.setPhoneNumber(phoneNumber);
                rentContact.setAddress(address);

                dao.addRentContact(rentContact);
                request.getSession().setAttribute("status", "1");
                response.sendRedirect("Rent");
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private String createErrorMessage(String message) {
        return "<html><body>" +
                "<h1>Error renting room</h1>" +
                "<p>" + message + "</p>" +
                "</body></html>";
    }

    @Override
    public String getServletInfo() {
        return "Servlet for processing room rentals";
    }
}
