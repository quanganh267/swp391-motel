/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.PaymentLine;

/**
 *
 * @author djxjs
 */
public class PaymentLineDAO extends DBContext{
    
   // Retrieve all payment lines associated with a specific record ID
    public ArrayList<PaymentLine> getPaymentLinesByRecordId(int recordId) {
        ArrayList<PaymentLine> paymentLines = new ArrayList<>();
        String sql = "SELECT * FROM Payment_Line WHERE Record_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, recordId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PaymentLine line = new PaymentLine(
                    rs.getInt("PaymentLine_id"),   // Maps to PaymentLine_id
                    rs.getString("Status"),         // Maps to Status
                    rs.getDouble("Price_per_month"), // Maps to Price_per_month
                    rs.getInt("Available_Guest"),   // Maps to Available_Guest
                    rs.getString("Description"),     // Maps to Description
                    rs.getInt("Record_id"),         // Maps to Record_id
                    rs.getString("Billing_Period"), // Maps to Billing_Period
                    rs.getDouble("Total_Payment")    // Maps to Total_Payment
                );
                paymentLines.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentLines;
    }

    // Add a new payment line
    public void addPaymentLine(String status, double pricePerMonth, int availableGuest, String description, int recordId, String billingPeriod, double totalPayment) {
        String sql = "INSERT INTO Payment_Line (Status, Price_per_month, Available_Guest, Description, Record_id, Billing_Period, Total_Payment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            stm.setDouble(2, pricePerMonth);
            stm.setInt(3, availableGuest);
            stm.setString(4, description);
            stm.setInt(5, recordId);
            stm.setString(6, billingPeriod);
            stm.setDouble(7, totalPayment);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing payment line
    public void updatePaymentLine(int paymentLineId, String status, double pricePerMonth, int availableGuest, String description, int recordId, String billingPeriod, double totalPayment) {
        String sql = "UPDATE Payment_Line SET Status = ?, Price_per_month = ?, Available_Guest = ?, Description = ?, Record_id = ?, Billing_Period = ?, Total_Payment = ? WHERE PaymentLine_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            stm.setDouble(2, pricePerMonth);
            stm.setInt(3, availableGuest);
            stm.setString(4, description);
            stm.setInt(5, recordId);
            stm.setString(6, billingPeriod);
            stm.setDouble(7, totalPayment);
            stm.setInt(8, paymentLineId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a payment line by ID
    public void deletePaymentLine(int paymentLineId) {
        String sql = "DELETE FROM Payment_Line WHERE PaymentLine_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, paymentLineId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a payment line by ID
    public PaymentLine getPaymentLineById(int paymentLineId) {
        String sql = "SELECT * FROM Payment_Line WHERE PaymentLine_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, paymentLineId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new PaymentLine(
                    paymentLineId,
                    rs.getString("Status"),
                    rs.getDouble("Price_per_month"),
                    rs.getInt("Available_Guest"),
                    rs.getString("Description"),
                    rs.getInt("Record_id"),
                    rs.getString("Billing_Period"),
                    rs.getDouble("Total_Payment")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getPaymentLineCountByRecordId(int recordId) {
    String sql = "SELECT COUNT(*) FROM Payment_Line WHERE Record_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, recordId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

// Updated method to retrieve payment lines with pagination
public ArrayList<PaymentLine> getPaymentLinesByRecordId(int recordId, int offset, int limit) {
    ArrayList<PaymentLine> paymentLines = new ArrayList<>();
    String sql = "SELECT * FROM Payment_Line WHERE Record_id = ? LIMIT ? OFFSET ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, recordId);
        st.setInt(2, limit);
        st.setInt(3, offset);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            PaymentLine line = new PaymentLine(
                rs.getInt("PaymentLine_id"),
                rs.getString("Status"),
                rs.getDouble("Price_per_month"),
                rs.getInt("Available_Guest"),
                rs.getString("Description"),
                rs.getInt("Record_id"),
                rs.getString("Billing_Period"),
                rs.getDouble("Total_Payment")
            );
            paymentLines.add(line);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentLines;
}
    
    // Main method for testing
    public static void main(String[] args) {
        PaymentLineDAO paymentLineDAO = new PaymentLineDAO();
        // Example usage
        ArrayList<PaymentLine> lines = paymentLineDAO.getPaymentLinesByRecordId(1); // Replace with a valid Record_id
        for (PaymentLine line : lines) {
            System.out.println("Payment Line ID: " + line.getPaymentLine_id());
        }
    }
}
