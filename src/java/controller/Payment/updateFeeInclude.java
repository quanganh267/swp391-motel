/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Payment;

import dal.FeeIncludeLineDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Fee_Include;

/**
 *
 * @author djxjs
 */
@WebServlet(name = "updateFeeInclude", urlPatterns = {"/fee-include/update"})
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
       // Truy xuất PaymentLine_Id từ request
        int paymentLineId = Integer.parseInt(request.getParameter("PaymentLine_Id"));
        
        // Lấy danh sách các Fee Include đã có
        FeeIncludeLineDAO feeIncludeLineDAO = new FeeIncludeLineDAO();
        List<Fee_Include> feeIncludes = feeIncludeLineDAO.getFeeIncludesByPaymentLineId(paymentLineId);
        
        // Lấy tất cả các Fee Include để hiển thị
        List<Fee_Include> allFeeIncludes = feeIncludeLineDAO.getAllFeeIncludes();
        
        // Gửi dữ liệu đến JSP
        request.setAttribute("FeeIncludepaymentLineId", paymentLineId);
        request.setAttribute("FeeIncludefeeIncludes", feeIncludes);
        request.setAttribute("FeeIncludeAll", allFeeIncludes);
        
        request.getRequestDispatcher("/Payment/updateFeeInclude.jsp").forward(request, response);
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
         try {
            String paymentLineIdStr = request.getParameter("PaymentLine_Id");
            if (paymentLineIdStr == null || paymentLineIdStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment Line ID is required.");
                return;
            }

            int paymentLineId = Integer.parseInt(paymentLineIdStr);
            String[] selectedFeeIncludeIds = request.getParameterValues("selectedFeeIncludeIds");

            List<String> selectedIdsList = selectedFeeIncludeIds != null ? Arrays.asList(selectedFeeIncludeIds) : new ArrayList<>();
            
            FeeIncludeLineDAO feeIncludeLineDAO = new FeeIncludeLineDAO();
            feeIncludeLineDAO.updateFeeIncludesForPaymentLine(paymentLineId, selectedIdsList);
            
            response.sendRedirect(request.getContextPath() + "/fee-includes?FeeIncludepaymentLineId=" + paymentLineId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Payment Line ID.");
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
