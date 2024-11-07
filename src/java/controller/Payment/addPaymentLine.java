/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Payment;

import dal.PaymentLineDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.PaymentLine;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author djxjs
 */
@WebServlet(name = "addPaymentLine", urlPatterns = {"/payment-line/add"})
public class addPaymentLine extends HttpServlet {

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
            out.println("<title>Servlet addPaymentLine</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addPaymentLine at " + request.getContextPath() + "</h1>");
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
        String lineRecordId = request.getParameter("PaymentRecord_Id");
    if (lineRecordId != null && !lineRecordId.isEmpty()) {
        request.setAttribute("recordIdForLine", lineRecordId); // Pass it to the JSP
    } else {
        request.setAttribute("recordIdForLine", ""); // Set to empty if not available
    }
    request.getRequestDispatcher("/Payment/addPaymentLine.jsp").forward(request, response);
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

         // Check if the record_id parameter is present
    String recordIdParam = request.getParameter("record_id");
    if (recordIdParam == null || recordIdParam.isEmpty()) {
        // Log an error or handle it appropriately
        System.err.println("Error: record_id is missing in the request.");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing record ID.");
        return; // Early return to avoid further processing
    }

    int record_id = Integer.parseInt(recordIdParam);
        
        String status = request.getParameter("Linestatus");
        double price_per_month = Double.parseDouble(request.getParameter("Lineprice_per_month"));
        int available_guest = Integer.parseInt(request.getParameter("Lineavailable_guest"));
        String description = request.getParameter("Linedescription");
//        int record_id = Integer.parseInt(request.getParameter("record_id"));
        String billing_period = request.getParameter("Linebilling_period");
        double total_payment = Double.parseDouble(request.getParameter("Linetotal_payment"));

        PaymentLineDAO paymentLineDAO = new PaymentLineDAO();
        paymentLineDAO.addPaymentLine(status, price_per_month, available_guest, description, record_id, billing_period, total_payment);

        // Redirect back to the payment line list or wherever appropriate
        response.sendRedirect(request.getContextPath() + "/payment-lines?PaymentRecord_Id=" + record_id);
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
