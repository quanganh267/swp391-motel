package dal;

import model.QandA;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QandADAO extends DBContext {

    public List<QandA> getAllQandA() throws SQLException {
        List<QandA> qAndAList = new ArrayList<>();
        String query = "SELECT * FROM Q_and_A";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
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
        }
        return qAndAList;
    }

    public void addQandA(QandA qAndA) throws SQLException {
        String query = "INSERT INTO Q_and_A (Guest_id, Guest_name, Email, Question, Answer) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, qAndA.getGuestId());
            ps.setString(2, qAndA.getGuestName());
            ps.setString(3, qAndA.getEmail());
            ps.setString(4, qAndA.getQuestion());
            ps.setString(5, qAndA.getAnswer());
            ps.executeUpdate();
        }
    }

    public QandA getQandAById(int qaId) throws SQLException {
        String query = "SELECT * FROM Q_and_A WHERE QA_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, qaId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        }
        return null;
    }

    public void updateQandA(QandA qAndA) throws SQLException {
        String query = "UPDATE Q_and_A SET Answer = ? WHERE QA_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, qAndA.getAnswer());
            ps.setInt(2, qAndA.getQaId());
            ps.executeUpdate();
        }
    }
} 