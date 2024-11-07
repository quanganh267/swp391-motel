package controller.Payment;

import dal.PaymentRecordDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PaymentRecord;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "listPaymentRecord", urlPatterns = {"/payment-records"})
public class listPaymentRecord extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            PaymentRecordDAO paymentRecordDAO = new PaymentRecordDAO();
            
            // Parse page number safely
            int pageNumber = 1;
            String pageStr = request.getParameter("paymentRecordPage");
            if (pageStr != null && !pageStr.isEmpty()) {
                try {
                    pageNumber = Integer.parseInt(pageStr);
                    if (pageNumber < 1) pageNumber = 1;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid page number: " + pageStr);
                }
            }
            
            int pageSize = 5;
            
            String searchBy = request.getParameter("searchByPaymentRecord");
            String searchValue = request.getParameter("searchValuePaymentRecord");
            String sortBy = request.getParameter("sortByPaymentRecord");
            String order = request.getParameter("orderPaymentRecord");

            if (searchValue != null) {
                searchValue = searchValue.replaceAll("\\s+", "");
            }
            
            List<PaymentRecord> paymentRecordList;
            int totalRecords;

            if (searchBy == null || searchValue == null || searchValue.trim().isEmpty()) {
                paymentRecordList = paymentRecordDAO.getPaymentRecords(pageNumber, pageSize, sortBy, order);
                totalRecords = paymentRecordDAO.getTotalPaymentRecords();
            } else {
                paymentRecordList = paymentRecordDAO.searchPaymentRecords(searchBy, searchValue, pageNumber, pageSize, sortBy, order);
                totalRecords = paymentRecordDAO.getTotalSearchResults(searchBy, searchValue);
            }

            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // Set attributes for JSP
            request.setAttribute("paymentRecordListOfRecords", paymentRecordList);
            request.setAttribute("paymentRecordTotalPages", totalPages);
            request.setAttribute("paymentRecordCurrentPage", pageNumber);
            request.setAttribute("searchByPaymentRecord", searchBy);
            request.setAttribute("searchValuePaymentRecord", searchValue);
            request.setAttribute("sortByPaymentRecord", sortBy);
            request.setAttribute("orderPaymentRecord", order);

            // Forward to JSP
            request.getRequestDispatcher("/Payment/listPaymentRecord.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in listPaymentRecord servlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request");
            request.getRequestDispatcher("/Payment/listPaymentRecord.jsp").forward(request, response);
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
