package dal;

import model.RoomType;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomTypeDAO extends DBContext {

    // Retrieve a room type by ID
    public RoomType getRoomTypeByID(int typeId) {
        String sql = "SELECT * FROM Room_Type WHERE type_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, typeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RoomType roomType = new RoomType();
                roomType.setId(rs.getInt("type_id"));
                roomType.setName(rs.getString("name"));
                roomType.setDescription(rs.getString("description"));
                roomType.setMax_user(rs.getInt("Max_Guest"));
                return roomType;
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomTypeDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // Main method for testing
    public static void main(String[] args) {
        RoomTypeDAO roomDao = new RoomTypeDAO();
        System.out.println(roomDao.getRoomTypeByID(1));
    }
}
