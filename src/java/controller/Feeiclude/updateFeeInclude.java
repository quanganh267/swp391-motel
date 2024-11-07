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
public class updateFeeInclude extends HttpServlet {
   
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
            out.println("<title>Servlet updateFeeInclude</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateFeeInclude at " + request.getContextPath () + "</h1>");
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
         String id_raw = request.getParameter("id");
        int id;
        FeeIncludeDAO feeIncludeDAO = new FeeIncludeDAO();
        try {            
            id = Integer.parseInt(id_raw);
            FeeInclude feeInclude = feeIncludeDAO.getFeeIncludeById(id);
            request.setAttribute("updateForFeeInclude", feeInclude);
            request.getRequestDispatcher("FeeInclude/updateFeeInclude.jsp").forward(request, response);
        } catch (NumberFormatException e) {            
            e.printStackTrace();
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
        String feeIncludeIdStr = request.getParameter("feeinclude_id");
    String note = request.getParameter("note");
    String countStr = request.getParameter("count");
    String priceStr = request.getParameter("price");

    int feeIncludeId = Integer.parseInt(feeIncludeIdStr);
    int count = 0; // Initialize count
    double price = 0.0; // Initialize price

    List<String> errorMessages = new ArrayList<>();

    // Validate inputs
    if (note == null || note.trim().isEmpty()) {
        errorMessages.add("Note cannot be empty.");
    }

    try {
        count = Integer.parseInt(countStr);
        if (count < 0) {
            errorMessages.add("Count must be a non-negative integer.");
        }
    } catch (NumberFormatException e) {
        errorMessages.add("Count must be a valid integer.");
    }

    try {
        price = Double.parseDouble(priceStr);
        if (price < 0) {
            errorMessages.add("Price must be a non-negative number.");
        }
    } catch (NumberFormatException e) {
        errorMessages.add("Price must be a valid number.");
    }

    // Check if there are errors
    if (!errorMessages.isEmpty()) {
        request.setAttribute("errorMessages", errorMessages);
        request.setAttribute("updateForFeeInclude", new FeeInclude(feeIncludeId, note, count, price)); // Pass back the current values
        request.getRequestDispatcher("updateFeeInclude.jsp").forward(request, response);
    } else {
        // Proceed to update the fee include
        FeeIncludeDAO dao = new FeeIncludeDAO();
        dao.updateFeeInclude(feeIncludeId, note, count, price);
        response.sendRedirect("listFeeInclude");
    }
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
