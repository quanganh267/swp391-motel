package controller;

import dal.FeedbackDAO;
import model.Feedback;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "FeedbackServlet", urlPatterns = {"/FeedbackServlet"})
public class FeedbackServlet extends HttpServlet {

    private FeedbackDAO feedbackDAO = new FeedbackDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String feedbackText = request.getParameter("feedbackText");
        int rating = Integer.parseInt(request.getParameter("rating"));

        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setCreatedAt(new Date());

        try {
            feedbackDAO.addFeedback(feedback);
            response.sendRedirect("Feedback.jsp?feedbackSubmitted=true");
        } catch (SQLException e) {
            throw new ServletException("Error adding feedback", e);
        }
    }
} 