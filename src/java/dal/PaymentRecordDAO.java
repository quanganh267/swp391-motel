package dal;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.PaymentRecord;

public class PaymentRecordDAO extends DBContext {

    // Retrieve all payment records
    public List<PaymentRecord> getAllPaymentRecords() {
    List<PaymentRecord> paymentRecords = new ArrayList<>();
    String sql = "SELECT * FROM Payment_Record";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            PaymentRecord record = new PaymentRecord();
            record.setRecord_id(rs.getInt("Record_id"));
            record.setDate(rs.getString("Date")); // Make sure this matches your updated model
            record.setRoom_id(rs.getInt("Room_id"));
            record.setTotal_discount(rs.getFloat("Total_Discount"));
            record.setMotel_id(rs.getInt("Motel_id"));
            record.setGuest_id(rs.getInt("Guest_id"));
            record.setContract_id(rs.getInt("Contract_id"));
            paymentRecords.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentRecords;
}

    // Add a new payment record
    public void addPaymentRecord(String dateStr, int roomId, double totalDiscount, int motelId, int guestId, int contractId) {
    String sql = "INSERT INTO Payment_Record (Date, Room_id, Total_Discount, Motel_id, Guest_id, Contract_id) VALUES (?, ?, ?, ?, ?, ?)";
    
    // Parse the date string into a java.sql.Date
    Date date = Date.valueOf(dateStr); // Ensure the date string is in "yyyy-mm-dd" format

    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setDate(1, date); // Set the java.sql.Date
        stm.setInt(2, roomId);
        stm.setBigDecimal(3, BigDecimal.valueOf(totalDiscount));
        stm.setInt(4, motelId);
        stm.setInt(5, guestId);
        stm.setInt(6, contractId);
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Update an existing payment record
    public void updatePaymentRecord(int recordId, String date, int roomId, float totalDiscount, int motelId, int guestId, int contractId) {
    String sql = "UPDATE Payment_Record SET Date = ?, Room_id = ?, Total_Discount = ?, Motel_id = ?, Guest_id = ?, Contract_id = ? WHERE Record_id = ?";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        stm.setDate(1, sqlDate);
        stm.setInt(2, roomId);
        stm.setBigDecimal(3, BigDecimal.valueOf(totalDiscount));
        stm.setInt(4, motelId);
        stm.setInt(5, guestId);
        stm.setInt(6, contractId);
        stm.setInt(7, recordId);
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Delete a payment record by ID
    public void deletePaymentRecord(int recordId) {
        String sql = "DELETE FROM Payment_Record WHERE Record_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, recordId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a payment record by ID
    public PaymentRecord getPaymentRecordById(int recordId) {
        String sql = "SELECT * FROM Payment_Record WHERE Record_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, recordId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Date"));    
                return new PaymentRecord(
                    recordId,
                    date,
                    rs.getInt("Room_id"),
                    rs.getFloat("Total_Discount"),
                    rs.getInt("Motel_id"),
                    rs.getInt("Guest_id"),
                    rs.getInt("Contract_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
//     public List<PaymentRecord> getPaymentRecords(int pageNumber, int pageSize, String sortBy, String order) {
//    List<PaymentRecord> paymentList = new ArrayList<>();
//    
//    try {
//        // Validate và set default values
//        if (sortBy == null || sortBy.trim().isEmpty()) {
//            sortBy = "Record_id";
//        }
//        if (order == null || !order.matches("(?i)ASC|DESC")) {
//            order = "ASC";
//        }
//
//        // Sửa câu SQL để phù hợp với SQL Server
//        String sql = "SELECT * FROM Payment_Record ORDER BY " + sortBy + " " + order + 
//                    " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//                    
//        PreparedStatement st = connection.prepareStatement(sql);
//        st.setInt(1, (pageNumber - 1) * pageSize);
//        st.setInt(2, pageSize);
//        
//        ResultSet rs = st.executeQuery();
//        
//        while (rs.next()) {
//            PaymentRecord record = new PaymentRecord();
//            record.setRecord_id(rs.getInt("Record_id"));
//            
//            // Xử lý date an toàn
//            Date date = rs.getDate("Date");
//            if (date != null) {
//                record.setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
//            } else {
//                record.setDate("");
//            }
//            
//            record.setRoom_id(rs.getInt("Room_id"));
//            record.setTotal_discount(rs.getFloat("Total_Discount"));
//            record.setMotel_id(rs.getInt("Motel_id"));
//            record.setGuest_id(rs.getInt("Guest_id"));
//            record.setContract_id(rs.getInt("Contract_id"));
//            
//            paymentList.add(record);
//        }
//        
//    } catch (SQLException e) {
//        System.err.println("Error in getPaymentRecords: " + e.getMessage());
//        e.printStackTrace();
//    }
//    
//    return paymentList;
//}
    public List<PaymentRecord> getPaymentRecords(int pageNumber, int pageSize, String sortBy, String order) {
    List<PaymentRecord> paymentList = new ArrayList<>();
    
    // Kiểm tra giá trị của sortBy và order để đảm bảo không bị lỗi SQL
    if (!"Record_id".equals(sortBy) && !"Date".equals(sortBy) && !"Room_id".equals(sortBy) && !"Total_Discount".equals(sortBy) && !"Motel_id".equals(sortBy) && !"Guest_id".equals(sortBy) && !"Contract_id".equals(sortBy)) {
        sortBy = "Record_id";  // Mặc định sắp xếp theo Record_id nếu sortBy không hợp lệ
    }
    
    if (!"ASC".equals(order) && !"DESC".equals(order)) {
        order = "ASC";  // Mặc định theo thứ tự tăng dần nếu order không hợp lệ
    }

    String sql = "SELECT * FROM Payment_Record ORDER BY " + sortBy + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Thêm sắp xếp vào câu SQL
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, (pageNumber - 1) * pageSize); // Tính toán offset
        st.setInt(2, pageSize); // Số bản ghi mỗi trang
        ResultSet rs = st.executeQuery();
        
        while (rs.next()) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Date"));
            PaymentRecord record = new PaymentRecord(
                rs.getInt("Record_id"), 
                date, 
                rs.getInt("Room_id"), 
                rs.getFloat("Total_Discount"), 
                rs.getInt("Motel_id"), 
                rs.getInt("Guest_id"), 
                rs.getInt("Contract_id")
            );
            paymentList.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentList;
}

    // Get total number of payment records for pagination
    public int getTotalPaymentRecords() {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM Payment_Record";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
    
    public List<PaymentRecord> searchPaymentRecords(String field, String value, int pageNumber, int pageSize, String sortBy, String order) {
    List<PaymentRecord> paymentList = new ArrayList<>();
    
    // Kiểm tra giá trị của sortBy và order để đảm bảo không bị lỗi SQL
    if (!"Record_id".equals(sortBy) && !"Date".equals(sortBy) && !"Room_id".equals(sortBy) && !"Total_Discount".equals(sortBy) && !"Motel_id".equals(sortBy) && !"Guest_id".equals(sortBy) && !"Contract_id".equals(sortBy)) {
        sortBy = "Record_id";  // Mặc định sắp xếp theo Record_id nếu sortBy không hợp lệ
    }
    
    if (!"ASC".equals(order) && !"DESC".equals(order)) {
        order = "ASC";  // Mặc định theo thứ tự tăng dần nếu order không hợp lệ
    }

    String sql = "SELECT * FROM Payment_Record WHERE " + field + " LIKE ? ORDER BY " + sortBy + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Thêm sắp xếp vào câu SQL
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, "%" + value + "%"); // Tìm kiếm với giá trị giống như trong mệnh đề LIKE
        st.setInt(2, (pageNumber - 1) * pageSize); // Tính toán offset
        st.setInt(3, pageSize); // Số bản ghi mỗi trang
        ResultSet rs = st.executeQuery();
        
        while (rs.next()) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Date"));
            PaymentRecord record = new PaymentRecord(
                rs.getInt("Record_id"),
                date,
                rs.getInt("Room_id"),
                rs.getFloat("Total_Discount"),
                rs.getInt("Motel_id"),
                rs.getInt("Guest_id"),
                rs.getInt("Contract_id")
            );
            paymentList.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentList;
}
    
public int getTotalSearchResults(String field, String value) {
    int totalRecords = 0;
    
    // Kiểm tra giá trị của field và value để tránh lỗi SQL
    String sql = "SELECT COUNT(*) FROM Payment_Record WHERE " + field + " LIKE ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, "%" + value + "%"); // Tìm kiếm với giá trị giống như trong mệnh đề LIKE
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            totalRecords = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return totalRecords;
}


public List<PaymentRecord> getSortedPaymentRecords(String orderBy, String orderType, int pageNumber, int pageSize) {
    List<PaymentRecord> paymentRecords = new ArrayList<>();
    String sql = "SELECT * FROM Payment_Record ORDER BY " + orderBy + " " + orderType + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, (pageNumber - 1) * pageSize);
        st.setInt(2, pageSize);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            PaymentRecord record = new PaymentRecord();
            record.setRecord_id(rs.getInt("Record_id"));
            record.setDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Date")));
            record.setRoom_id(rs.getInt("Room_id"));
            record.setTotal_discount(rs.getFloat("Total_Discount"));
            record.setMotel_id(rs.getInt("Motel_id"));
            record.setGuest_id(rs.getInt("Guest_id"));
            record.setContract_id(rs.getInt("Contract_id"));
            paymentRecords.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentRecords;
}

public void updateTotalDiscount(int recordId, float totalDiscount) {
    String sql = "UPDATE Payment_Record SET Total_Discount = ? WHERE Record_id = ?";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setFloat(1, totalDiscount);
        stm.setInt(2, recordId);
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void updateDate(int recordId, String date) {
    String sql = "UPDATE Payment_Record SET Date = ? WHERE Record_id = ?";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        stm.setDate(1, sqlDate);
        stm.setInt(2, recordId);
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public List<PaymentRecord> searchAndSortPaymentRecords(String field, String value, String orderBy, String orderType, int pageNumber, int pageSize) {
    List<PaymentRecord> paymentRecords = new ArrayList<>();
    String sql = "SELECT * FROM Payment_Record WHERE " + field + " LIKE ? ORDER BY " + orderBy + " " + orderType + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, "%" + value + "%");
        st.setInt(2, (pageNumber - 1) * pageSize);
        st.setInt(3, pageSize);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            PaymentRecord record = new PaymentRecord(
                rs.getInt("Record_id"),
                new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Date")),
                rs.getInt("Room_id"),
                rs.getFloat("Total_Discount"),
                rs.getInt("Motel_id"),
                rs.getInt("Guest_id"),
                rs.getInt("Contract_id")
            );
            paymentRecords.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paymentRecords;
}


    // Main method for testing
//    public static void main(String[] args) {
//        PaymentRecordDAO paymentDAO = new PaymentRecordDAO();
//        ArrayList<PaymentRecord> paymentList = paymentDAO.getAllPaymentRecords();
//        if (!paymentList.isEmpty()) {
//            System.out.println("First payment record date: " + paymentList.get(0).getDate());
//        }
//    }
}