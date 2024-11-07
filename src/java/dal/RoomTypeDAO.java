package dal;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO extends DBContext {

    // Retrieve all rooms for a specific motel
    public RoomType getRoomTypeByID(int motelId) {
        String sql = "SELECT * FROM Room_Type WHERE type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, motelId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RoomType roomType = new RoomType();
                    roomType.setId(rs.getInt("type_id"));
                    roomType.setName(rs.getString("name"));
                    roomType.setDescription(rs.getString("description"));
                    roomType.setMax_user(rs.getInt("Max_Guest"));

                    return roomType;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RoomTypeDAO roomDao = new RoomTypeDAO();
        System.out.println(roomDao.getRoomTypeByID(1));
    }
}
