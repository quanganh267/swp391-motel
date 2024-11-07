package controller.admin;

import controller.admin.*;
import dal.FeedbackDAO;
import model.Feedback;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FeedbackManagementServlet", urlPatterns = {"/admin/feedback-management"})
public class FeedbackManagementServlet extends HttpServlet {

    private FeedbackDAO feedbackDAO = new FeedbackDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Feedback> feedbackList = null;
        try {
            feedbackList = feedbackDAO.getAllFeedback();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("feedback-management.jsp").forward(request, response);
    }
} 