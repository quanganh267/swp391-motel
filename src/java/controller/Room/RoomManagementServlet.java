/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Room;

import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;

/**
 *
 * @author anhdq
 */
@WebServlet(name="RoomManagementServlet", urlPatterns={"/admin/room-management"})
public class RoomManagementServlet extends HttpServlet {
   
    private RoomDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new RoomDAO(); // Initialize the RoomDAO object
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Room> rooms = dao.getAllRooms(); // Lấy tất cả phòng
            request.setAttribute("rooms", rooms); // Đặt dữ liệu phòng vào request attribute
            request.getRequestDispatcher("/admin/RoomManagement.jsp").forward(request, response); // Forward dữ liệu tới trang RoomManagement.jsp
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("roomId"));
                int motelId = Integer.parseInt(request.getParameter("motelId"));
                int typeId = Integer.parseInt(request.getParameter("typeId"));
                String imageUrl = request.getParameter("imageUrl");
                String motelName = request.getParameter("motelName");
                String typeName = request.getParameter("typeName");
                String status = request.getParameter("status");
                double price = Double.parseDouble(request.getParameter("Price"));

                Room room = new Room(roomId, motelId, typeId, imageUrl, motelName, typeName, status, price);
                dao.addRoom(room);
                response.sendRedirect(request.getContextPath() + "/admin/room-management");

            } else if ("edit".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("roomId"));
                String imageUrl = request.getParameter("imageUrl");
                int typeId = Integer.parseInt(request.getParameter("typeId"));
                String status = request.getParameter("status");
                double price = Double.parseDouble(request.getParameter("price"));

                Room room = new Room(roomId, 0, typeId, imageUrl, null, null, status, price);
                dao.EditRoom(room);
                response.sendRedirect(request.getContextPath() + "/admin/room-management");

            } else if ("changeStatus".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("roomId"));
                String newStatus = request.getParameter("newStatus");
                dao.changeRoomStatus(roomId, newStatus);
                response.sendRedirect(request.getContextPath() + "/admin/room-management");

            } else if ("delete".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("roomId"));
                dao.deleteRoom(roomId);
                response.sendRedirect(request.getContextPath() + "/admin/room-management");
            }
        } catch (SQLException e) {
          
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
        }
    }

    @Override
    public String getServletInfo() {
        return "RoomManagementServlet handles room management actions";
    }
}