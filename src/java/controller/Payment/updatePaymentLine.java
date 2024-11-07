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
import model.PaymentLine;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author djxjs
 */
@WebServlet(name = "updatePaymentLine", urlPatterns = {"/payment-line/update"})
public class updatePaymentLine extends HttpServlet {
   
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
            out.println("<title>Servlet updatePaymentLine</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePaymentLine at " + request.getContextPath () + "</h1>");
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
         // Lấy PaymentLineUpdateId từ tham số yêu cầu
    int paymentLineId = Integer.parseInt(request.getParameter("PaymentLineUpdateId"));
    
    // Tạo đối tượng DAO và lấy thông tin dòng thanh toán dựa trên ID
    PaymentLineDAO paymentLineDAO = new PaymentLineDAO();
    PaymentLine paymentLine = paymentLineDAO.getPaymentLineById(paymentLineId);
    
    // Đưa thông tin dòng thanh toán vào đối tượng yêu cầu để truyền đến trang JSP
    request.setAttribute("paymentLine", paymentLine);
    
    // Chuyển hướng đến trang JSP cập nhật dòng thanh toán
    request.getRequestDispatcher("/Payment/updatePaymentLine.jsp").forward(request, response);
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
     int paymentLineId = Integer.parseInt(request.getParameter("paymentLineId"));
        String status = request.getParameter("status");
        double pricePerMonth = Double.parseDouble(request.getParameter("price_per_month"));
        int availableGuest = Integer.parseInt(request.getParameter("available_guest"));
        String description = request.getParameter("description");
        String billingPeriod = request.getParameter("billing_period");
        double totalPayment = Double.parseDouble(request.getParameter("total_payment"));
        int recordId = Integer.parseInt(request.getParameter("paymentLineRecordId"));

        // Update the payment line in the database
        PaymentLineDAO paymentLineDAO = new PaymentLineDAO();
        paymentLineDAO.updatePaymentLine(paymentLineId, status, pricePerMonth, availableGuest, description, recordId, billingPeriod, totalPayment);

        // Redirect back to the payment lines list
        response.sendRedirect(request.getContextPath() + "/payment-lines?PaymentRecord_Id=" + recordId);
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
