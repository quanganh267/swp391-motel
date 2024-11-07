/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import com.sun.jdi.connect.spi.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Fee_Include;
import model.Fee_Include_Line;

/**
 *
 * @author djxjs
 */
public class FeeIncludeLineDAO extends DBContext{
    public ArrayList<Fee_Include> getFeeIncludesByPaymentLineId(int paymentLineId) {
    ArrayList<Fee_Include> feeIncludes = new ArrayList<>();
    String sql = "SELECT fi.feeinclude_id, fi.note, fi.count, fi.price, fi.Unit "
               + "FROM Fee_Include fi "
               + "JOIN Fee_Include_Line fil ON fi.feeinclude_id = fil.FeeInclude_id "
               + "WHERE fil.PaymentLine_id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, paymentLineId); // Thiết lập tham số cho PaymentLine_id
        ResultSet rs = pstmt.executeQuery(); // Thực hiện truy vấn

        // Duyệt qua các kết quả trong ResultSet
        while (rs.next()) {
            Fee_Include feeInclude = new Fee_Include(); // Khởi tạo đối tượng Fee_Include
            feeInclude.setFeeinclude_id(rs.getInt("feeinclude_id")); // Lấy giá trị từ ResultSet
            feeInclude.setNote(rs.getString("note"));
            feeInclude.setCount(rs.getInt("count"));
            feeInclude.setPrice(rs.getDouble("price"));
            feeInclude.setUnit(rs.getString("Unit"));
            feeIncludes.add(feeInclude); // Thêm vào danh sách
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi
    }

    return feeIncludes; // Trả về danh sách các đối tượng Fee_Include
}
    
    public void updateFeeIncludesForPaymentLine(int paymentLineId, List<String> selectedFeeIncludeIds) {
        try {
            // Xóa tất cả các dòng có PaymentLine_id
            String deleteSql = "DELETE FROM Fee_Include_Line WHERE PaymentLine_id = ?";
            try (PreparedStatement deletePstmt = connection.prepareStatement(deleteSql)) {
                deletePstmt.setInt(1, paymentLineId);
                deletePstmt.executeUpdate();
            }

            // Nếu không có ID nào được chọn, thoát khỏi phương thức
            if (selectedFeeIncludeIds == null || selectedFeeIncludeIds.isEmpty()) {
                return;
            }

            // Thêm lại các dòng đã chọn vào Fee_Include_Line
            String insertSql = "INSERT INTO Fee_Include_Line (PaymentLine_id, FeeInclude_id) VALUES (?, ?)";
            try (PreparedStatement insertPstmt = connection.prepareStatement(insertSql)) {
                for (String feeIncludeId : selectedFeeIncludeIds) {
                    insertPstmt.setInt(1, paymentLineId);
                    insertPstmt.setInt(2, Integer.parseInt(feeIncludeId));
                    insertPstmt.addBatch();
                }
                insertPstmt.executeBatch(); // Thực hiện cập nhật
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi
        }
    }
    
    public boolean isFeeIncludeInLine(int paymentLineId, int feeIncludeId) {
    String sql = "SELECT COUNT(*) FROM Fee_Include_Line WHERE PaymentLine_id = ? AND FeeInclude_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, paymentLineId);
        pstmt.setInt(2, feeIncludeId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
    public ArrayList<Fee_Include> getAllFeeIncludes() {
    ArrayList<Fee_Include> feeIncludes = new ArrayList<>();
    String sql = "SELECT fi.* FROM Fee_Include fi";

    try{PreparedStatement pstmt = connection.prepareStatement(sql); // Sử dụng PreparedStatement
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Fee_Include feeInclude = new Fee_Include();
            feeInclude.setFeeinclude_id(rs.getInt("feeinclude_id"));
            feeInclude.setNote(rs.getString("note"));
            feeInclude.setCount(rs.getInt("count"));
            feeInclude.setPrice(rs.getDouble("price"));
            feeInclude.setUnit(rs.getString("unit"));
            feeIncludes.add(feeInclude);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Xử lý ngoại lệ
    }
    return feeIncludes;
}
}
