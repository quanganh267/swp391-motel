package controller.admin;

import controller.admin.*;
import dal.QandADAO;
import model.QandA;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "QandAManagementServlet", urlPatterns = {"/admin/qanda-management"})
public class QandAManagementServlet extends HttpServlet {

    private QandADAO qandADAO = new QandADAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<QandA> qAndAList = qandADAO.getAllQandA();
            request.setAttribute("qAndAList", qAndAList);
            request.getRequestDispatcher("/admin/qanda-management.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve Q&A from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int qaId = Integer.parseInt(request.getParameter("qaId"));
            String answer = request.getParameter("answer");

            QandA qAndA = qandADAO.getQandAById(qaId);
            qAndA.setAnswer(answer);
            qandADAO.updateQandA(qAndA);

            response.sendRedirect("/admin/qanda-management");
        } catch (SQLException e) {
            throw new ServletException("Error updating Q&A", e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid Q&A ID format", e);
        }
    }
} 