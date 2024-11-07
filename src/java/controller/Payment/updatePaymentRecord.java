/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Payment;

import dal.PaymentRecordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import model.PaymentRecord;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author djxjs
 */
@WebServlet(name = "updatePaymentRecord", urlPatterns = {"/payment-record/update"})
public class updatePaymentRecord extends HttpServlet {

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
            out.println("<title>Servlet updatePaymentRecord</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePaymentRecord at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String updatePaymentRecordId = request.getParameter("updatePaymentRecordId");
    if (updatePaymentRecordId != null) {
        PaymentRecordDAO paymentRecordDAO = new PaymentRecordDAO();
        PaymentRecord paymentRecord = paymentRecordDAO.getPaymentRecordById(Integer.parseInt(updatePaymentRecordId));
        request.setAttribute("paymentRecord", paymentRecord);
        request.getRequestDispatcher("/Payment/updatePaymentRecord.jsp").forward(request, response);
        return;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        int recordId = Integer.parseInt(request.getParameter("record_id"));
        String date = request.getParameter("date");
        int roomId = Integer.parseInt(request.getParameter("room_id"));
        float totalDiscount = Float.parseFloat(request.getParameter("total_discount"));
        int motelId = Integer.parseInt(request.getParameter("motel_id"));
        int guestId = Integer.parseInt(request.getParameter("guest_id"));
        int contractId = Integer.parseInt(request.getParameter("contract_id"));

        // Update payment record
        PaymentRecordDAO paymentRecordDAO = new PaymentRecordDAO();
        paymentRecordDAO.updatePaymentRecord(recordId, date, roomId, totalDiscount, motelId, guestId, contractId);

        // Redirect back to the payment record list
        response.sendRedirect(request.getContextPath() + "/payment-records");
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
