package dal;

import model.QandA;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QandADAO extends DBContext {

    // Retrieve all QandA entries
    public List<QandA> getAllQandA() {
        List<QandA> qAndAList = new ArrayList<>();
        String query = "SELECT * FROM Q_and_A";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                QandA qAndA = new QandA();
                qAndA.setQaId(rs.getInt("QA_id"));
                qAndA.setGuestId(rs.getInt("Guest_id"));
                qAndA.setGuestName(rs.getString("Guest_name"));
                qAndA.setEmail(rs.getString("Email"));
                qAndA.setQuestion(rs.getString("Question"));
                qAndA.setAnswer(rs.getString("Answer"));
                qAndA.setCreatedAt(rs.getDate("Created_at"));
                qAndAList.add(qAndA);
            }
        } catch (SQLException e) {
            Logger.getLogger(QandADAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return qAndAList;
    }

    // Add a new QandA entry
    public void addQandA(QandA qAndA) {
        String query = "INSERT INTO Q_and_A (Guest_id, Guest_name, Email, Question, Answer) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, qAndA.getGuestId());
            ps.setString(2, qAndA.getGuestName());
            ps.setString(3, qAndA.getEmail());
            ps.setString(4, qAndA.getQuestion());
            ps.setString(5, qAndA.getAnswer());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QandADAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Retrieve a QandA entry by ID
    public QandA getQandAById(int qaId) {
        String query = "SELECT * FROM Q_and_A WHERE QA_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, qaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                QandA qAndA = new QandA();
                qAndA.setQaId(rs.getInt("QA_id"));
                qAndA.setGuestId(rs.getInt("Guest_id"));
                qAndA.setGuestName(rs.getString("Guest_name"));
                qAndA.setEmail(rs.getString("Email"));
                qAndA.setQuestion(rs.getString("Question"));
                qAndA.setAnswer(rs.getString("Answer"));
                qAndA.setCreatedAt(rs.getDate("Created_at"));
                return qAndA;
            }
        } catch (SQLException e) {
            Logger.getLogger(QandADAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // Update an existing QandA entry
    public void updateQandA(QandA qAndA) {
        String query = "UPDATE Q_and_A SET Answer = ? WHERE QA_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, qAndA.getAnswer());
            ps.setInt(2, qAndA.getQaId());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QandADAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
} 