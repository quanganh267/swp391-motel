/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
import java.util.ArrayList;
import java.util.List;
import model.FeeInclude;
import model.Fee_Include;
import model.Fee_Include_Line;

/**
 *
 * @author djxjs
 */
public class FeeIncludeDAO extends DBContext {
//    public ArrayList<Fee_Include> getFeeIncludesByPaymentLineId(int paymentLineId) {
//        ArrayList<Fee_Include> feeIncludes = new ArrayList<>();
//        String sql = "SELECT fi.* FROM Fee_Include fi "
//                   + "JOIN Fee_Include_Line fil ON fi.feeinclude_id = fil.FeeInclude_id "
//                   + "WHERE fil.PaymentLine_id = ?";
//
//        try 
//        {
//            PreparedStatement pstmt = connection.prepareStatement(sql); // Sử dụng PreparedStatement
//        ResultSet rs = pstmt.executeQuery(); // Thực hiện truy vấn
//            while (rs.next()) {
//                Fee_Include feeInclude = new Fee_Include();
//                feeInclude.setFeeinclude_id(rs.getInt("feeinclude_id"));
//                feeInclude.setNote(rs.getString("note"));
//                feeInclude.setCount(rs.getInt("count"));
//                feeInclude.setPrice(rs.getDouble("price"));
//                feeInclude.setUnit(rs.getString("Unit"));
//                feeIncludes.add(feeInclude);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp trong sản phẩm
//        }
//        return feeIncludes;
//    }
//    
//    public ArrayList<Fee_Include> getAllFeeIncludes() {
//    ArrayList<Fee_Include> feeIncludes = new ArrayList<>();
//    String sql = "SELECT fi.* FROM Fee_Include fi";
//
//    try{PreparedStatement pstmt = connection.prepareStatement(sql); // Sử dụng PreparedStatement
//        ResultSet rs = pstmt.executeQuery();
//        
//        while (rs.next()) {
//            Fee_Include feeInclude = new Fee_Include();
//            feeInclude.setFeeinclude_id(rs.getInt("feeinclude_id"));
//            feeInclude.setNote(rs.getString("note"));
//            feeInclude.setCount(rs.getInt("count"));
//            feeInclude.setPrice(rs.getDouble("price"));
//            feeInclude.setUnit(rs.getString("unit"));
//            feeIncludes.add(feeInclude);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace(); // Xử lý ngoại lệ
//    }
//    return feeIncludes;
//}
    
    public ArrayList<FeeInclude> getAllFeeInclude() {
    ArrayList<FeeInclude> feeIncludeList = new ArrayList<>();
    String sql = "SELECT * FROM FeeInclude";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            FeeInclude feeInclude = new FeeInclude(
                rs.getInt("feeinclude_id"),   // Maps to feeinclude_id
                rs.getString("note"),         // Maps to note
                rs.getInt("count"),           // Maps to count
                rs.getDouble("price")         // Maps to price
            );
            feeIncludeList.add(feeInclude);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return feeIncludeList;
}
    
    public void addFeeInclude(String note, int count, double price) {
    String sql = "INSERT INTO FeeInclude (note, count, price) VALUES (?, ?, ?)";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, note);     // Sets the note (description of the fee)
        stm.setInt(2, count);       // Sets the count (quantity of the fee)
        stm.setDouble(3, price);    // Sets the price (amount of the fee)
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void deleteFeeInclude(int feeinclude_id) {
    String sql = "DELETE FROM FeeInclude WHERE feeinclude_id = ?";    
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, feeinclude_id);   // Sets feeinclude_id for deletion
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public List<FeeInclude> searchFeeInclude(String keyword) {
        List<FeeInclude> feeIncludeList = new ArrayList<>();
        String sql = "SELECT * FROM FeeInclude WHERE note LIKE ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            st.setString(1, searchKeyword);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                FeeInclude feeInclude = new FeeInclude(
                    rs.getInt("feeinclude_id"), // Maps to feeinclude_id
                    rs.getString("note"),        // Maps to note
                    rs.getInt("count"),          // Maps to count
                    rs.getDouble("price")        // Maps to price
                );
                feeIncludeList.add(feeInclude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeIncludeList;
    }
    
   public void updateFeeInclude(int feeinclude_id, String note, int count, double price) {
        String sql = "UPDATE FeeInclude SET note = ?, count = ?, price = ? WHERE feeinclude_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, note);
            st.setInt(2, count);
            st.setDouble(3, price);
            st.setInt(4, feeinclude_id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public FeeInclude getFeeIncludeById(int feeinclude_id) {
    String sql = "SELECT * FROM FeeInclude WHERE feeinclude_id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, feeinclude_id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new FeeInclude(
                feeinclude_id,              // feeinclude_id from parameter
                rs.getString("note"),       // Retrieve note from result set
                rs.getInt("count"),         // Retrieve count from result set
                rs.getDouble("price")       // Retrieve price from result set
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Return null if no matching record is found
}
}
