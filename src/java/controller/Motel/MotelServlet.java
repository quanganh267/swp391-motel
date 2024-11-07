/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Motel;

import dal.MotelDAO;
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
import model.Motel;
import model.Room;


/**
 *
 * @author DLCT
 */
public class MotelServlet extends HttpServlet {
  private MotelDAO dao;

   @Override
    public void init() throws ServletException {
        dao = new MotelDAO();  // Khởi tạo RoomDAO để kết nối cơ sở dữ liệu
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MotelController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MotelController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  // ... existing code ...

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String motelIdStr = request.getParameter("motelID");
    if (motelIdStr != null) {
        try {
            int motelId = Integer.parseInt(motelIdStr);
            Motel motel = dao.getMotelById(motelId);
            RoomDAO roomDAO = new RoomDAO();
            List<Room> rooms = roomDAO.searchRooms(motelId, null);
            request.setAttribute("motel", motel);
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("motelRooms.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    } else {
        // Existing code to display all motels
        try {
            List<Motel> motels = dao.getAllMotels();
            request.setAttribute("motels", motels);
            request.getRequestDispatcher("motel.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}



    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String city = request.getParameter("city");
    
    try {
        List<Motel> motels = dao.searchMotels(city);
        request.setAttribute("motels", motels);
        request.getRequestDispatcher("motel.jsp").forward(request, response);
    } catch (SQLException e) {
        throw new ServletException(e);
    }
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
