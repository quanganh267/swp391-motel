/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Feeiclude;

import model.FeeInclude;
import dal.FeeIncludeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author djxjs
 */
public class searchFeeInclude extends HttpServlet {
   
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
            out.println("<title>Servlet searchFeeInclude</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet searchFeeInclude at " + request.getContextPath () + "</h1>");
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
        String keyword = request.getParameter("keyword");

        FeeIncludeDAO dao = new FeeIncludeDAO();
        List<FeeInclude> listOfFeeInclude;

        if (keyword == null || keyword.isEmpty()) {
            listOfFeeInclude = dao.getAllFeeInclude(); // Lấy tất cả nếu không có từ khóa
        } else {
            listOfFeeInclude = dao.searchFeeInclude(keyword); // Tìm kiếm theo từ khóa
        }

        // Trả kết quả về cho AJAX dưới dạng HTML
        PrintWriter out = response.getWriter();
        for (FeeInclude feeInclude : listOfFeeInclude) {
            out.println("<tr>");
            out.println("<td>" + feeInclude.getFeeinclude_id() + "</td>");
            out.println("<td>" + feeInclude.getNote() + "</td>");
            out.println("<td>" + feeInclude.getCount() + "</td>");
            out.println("<td>" + feeInclude.getPrice() + "</td>");
            out.println("<td>");
            out.println("<a href='updateFeeInclude?id=" + feeInclude.getFeeinclude_id() + "'>Update</a>");
            out.println("<a href='#' onclick=\"doDelete('" + feeInclude.getFeeinclude_id() + "')\">Delete</a>");
            out.println("</td>");
            out.println("</tr>");
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
