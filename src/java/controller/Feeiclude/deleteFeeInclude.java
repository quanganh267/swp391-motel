/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Feeiclude;

import dal.FeeIncludeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author djxjs
 */
public class deleteFeeInclude extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet deleteFeeInclude</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteFeeInclude at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String feeinclude_id = request.getParameter("id");
    int id;
    try {
        id = Integer.parseInt(feeinclude_id);
        FeeIncludeDAO feeIncludeDAO = new FeeIncludeDAO(); // Khởi tạo đối tượng FeeIncludeDAO
        feeIncludeDAO.deleteFeeInclude(id); // Gọi phương thức xóa
        response.sendRedirect("listFeeInclude"); // Chuyển hướng đến trang danh sách sau khi xóa
    } catch (NumberFormatException e) {
        // Xử lý trường hợp không thể chuyển đổi sang số nguyên
        e.printStackTrace(); // In ra lỗi nếu có
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Fee Include ID"); // Trả về lỗi 400 nếu ID không hợp lệ
    }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
