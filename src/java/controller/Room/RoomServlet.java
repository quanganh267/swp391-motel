/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Room;

import dal.RoomDAO;
import model.Room;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class RoomServlet extends HttpServlet {

    private RoomDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new RoomDAO();  // Khởi tạo RoomDAO để kết nối cơ sở dữ liệu
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Room> rooms = dao.getAllRooms(); // Lấy tất cả phòng
            request.setAttribute("rooms", rooms); // Đặt dữ liệu phòng vào request attribute
            request.getRequestDispatcher("Room.jsp").forward(request, response); // Forward dữ liệu tới trang Room.jsp
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy thông tin Motel ID từ form, nếu không có thì đặt null
            String motelIdStr = request.getParameter("motelId");
            Integer motelId = (motelIdStr != null && !motelIdStr.isEmpty()) ? Integer.parseInt(motelIdStr) : null;

            // Lấy thông tin loại phòng từ form, nếu không có thì đặt null
            String typeName = request.getParameter("typeName");
            if (typeName == null || typeName.trim().isEmpty()) {
                typeName = null;
            }

            // Nếu cả Motel ID và Loại Phòng đều null, điều hướng lại trang Room
            if (motelId == null && typeName == null) {
                response.sendRedirect("Room"); // Điều hướng về trang Room nếu không có tham số tìm kiếm
                return;
            }

            // Tìm kiếm phòng dựa trên Motel ID và Loại Phòng
            List<Room> rooms = dao.searchRooms(motelId, typeName);
            request.setAttribute("rooms", rooms); // Đặt kết quả tìm kiếm vào request attribute
            request.getRequestDispatcher("Room.jsp").forward(request, response); // Forward tới Room.jsp với dữ liệu tìm kiếm
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
