package dal;

import model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends DBContext {

    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT Feedback_id, User_id, Feedback_Text, Rating, Created_at FROM Feedback";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            Feedback feedback = new Feedback(
                rs.getInt("Feedback_id"),
                rs.getInt("User_id"),
                rs.getString("Feedback_Text"),
                rs.getInt("Rating"),
                rs.getDate("Created_at")
            );
            feedbackList.add(feedback);
        }
        return feedbackList;
    }

    public void addFeedback(Feedback feedback) throws SQLException {
        String query = "INSERT INTO Feedback (User_id, Feedback_Text, Rating, Created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, feedback.getUserId());
            ps.setString(2, feedback.getFeedbackText());
            ps.setInt(3, feedback.getRating());
            ps.setDate(4, new java.sql.Date(feedback.getCreatedAt().getTime()));
            ps.executeUpdate();
        }
    }
} 