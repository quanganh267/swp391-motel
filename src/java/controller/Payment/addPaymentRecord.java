/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Payment;

import dal.PaymentRecordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.PaymentRecord;

/**
 *
 * @author djxjs
 */
@WebServlet(name = "addPaymentRecord", urlPatterns = {"/payment-record/add"})
public class addPaymentRecord extends HttpServlet {
   
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
            out.println("<title>Servlet addPaymentRecord</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addPaymentRecord at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("/Payment/addPaymentRecord.jsp").forward(request, response);
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
        String dateStr = request.getParameter("Recorddate");
    int roomId = Integer.parseInt(request.getParameter("Recordroom_id"));
    double totalDiscount = Double.parseDouble(request.getParameter("Recordtotal_discount"));
    int motelId = Integer.parseInt(request.getParameter("Recordmotel_id"));
    int guestId = Integer.parseInt(request.getParameter("Recordguest_id"));
    int contractId = Integer.parseInt(request.getParameter("Recordcontract_id"));

    PaymentRecordDAO paymentRecordDAO = new PaymentRecordDAO();
    
    try {
        paymentRecordDAO.addPaymentRecord(dateStr, roomId, totalDiscount, motelId, guestId, contractId);
        request.setAttribute("success", "Payment record added successfully!");
    } catch (Exception e) {
        request.setAttribute("error", "Error adding payment record: " + e.getMessage());
    }
    
    response.sendRedirect(request.getContextPath() + "/payment-records");
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
