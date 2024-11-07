package dal;

import model.Room;
import model.Categories;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Motel;
import model.RoomType;

public class RoomDAO extends DBContext {

    final MotelDAO motelDAO = new MotelDAO();

   // Retrieve all rooms with their associated room types, motel names, and images
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT Room.Room_id, Room.Motel_id, Room.Type_id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl, Room.Status AS status, Room.Price AS price " // Thêm cột price
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            Room room = new Room(
                    rs.getInt("Room_id"),
                    rs.getInt("Motel_id"),
                    rs.getInt("Type_id"),
                    rs.getString("imageUrl"),
                    rs.getString("motelName"),
                    rs.getString("roomTypeName"),
                    rs.getString("status"),
                    rs.getDouble("price") // Lấy giá trị price
            );
            rooms.add(room);
            System.out.println(room);
        }
        return rooms;
    }

// Thay đổi trong phương thức searchRooms  
    public List<Room> searchRooms(Integer motelId, String typeName) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT Room.Room_id, Room.Motel_id, Room.Type_id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl, Room.Status AS status, Room.Price AS price " // Lấy trạng thái từ bảng Room  
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id "
                + "WHERE 1=1"
        );

        // Thêm các điều kiện tìm kiếm nếu có  
        if (motelId != null) {
            query.append(" AND Room.Motel_id = ?");
        }
        if (typeName != null && !typeName.isEmpty()) {
            query.append(" AND Room_Type.Name LIKE ?");
        }

        PreparedStatement ps = connection.prepareStatement(query.toString());

        int index = 1;
        if (motelId != null) {
            ps.setInt(index++, motelId);
        }
        if (typeName != null && !typeName.isEmpty()) {
            ps.setString(index++, "%" + typeName + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Room room = new Room(
                    rs.getInt("Room_id"),
                    rs.getInt("Motel_id"),
                    rs.getInt("Type_id"),
                    rs.getString("imageUrl"),
                    rs.getString("motelName"),
                    rs.getString("roomTypeName"),
                    rs.getString("status"),
                    rs.getDouble("price") // Lấy giá trị price
            );
            rooms.add(room);
        }

        return rooms;
    }

    // Bạn có thể bổ sung các phương thức khác như getRoomById nếu cần  
    // Fetch room details by ID
    public Room getRoomById(int roomId) throws SQLException {
        String query = "SELECT Room.Room_id, Room.Motel_id, Room.Type_Id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl , Room.Status AS status , Room.Price AS price "
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_Id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id "
                + "WHERE Room.Room_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, roomId); // Sử dụng int cho roomId  
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Room(
                    rs.getInt("Room_id"),
                    rs.getInt("Motel_id"),
                    rs.getInt("Type_id"),
                    rs.getString("imageUrl"),
                    rs.getString("motelName"),
                    rs.getString("roomTypeName"),
                    rs.getString("status"),
                    rs.getDouble("price") // Lấy giá trị price
            );
        }

        return null;
    }

    public void deleteRoom(int roomId) throws SQLException {
        String query = "DELETE FROM Room WHERE Room_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, roomId);
        ps.executeUpdate();
    }

    public void EditRoom(Room room) throws SQLException {
    String query = "UPDATE Room SET Type_Id = ?, Image_Url = ?, Status = ?, Price = ? WHERE Room_Id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, room.gettypeId());
        ps.setString(2, room.getImageUrl());
        ps.setString(3, room.getStatus());
        ps.setDouble(4, room.getPrice());
        ps.setInt(5, room.getRoomId());
        ps.executeUpdate();
    }
}


    public void changeRoomStatus(int roomId, String newStatus) throws SQLException {
        String query = "UPDATE Room SET status = ? WHERE Room_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, newStatus);
        ps.setInt(2, roomId);
        ps.executeUpdate();
    }

   public void addRoom(Room room) throws SQLException {
    String sql = "INSERT INTO Room (Room_id, Motel_id, Type_Id, Image_Url, Status, Price) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, room.getRoomId());
        statement.setInt(2, room.getMotelId());
        statement.setInt(3, room.gettypeId());
        statement.setString(4, room.getImageUrl());
        statement.setString(5, room.getStatus());
        statement.setDouble(6, room.getPrice());
        statement.executeUpdate();
    }
}


    // Đảm bảo đóng kết nối sau khi sử dụng  
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public List<Categories> getCategoriesByTypeId(int typeId) throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String query = "SELECT Category_id, Type_id, Category_Name, Description FROM Categories WHERE Type_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, typeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categories category = new Categories(
                        rs.getInt("Category_id"),
                        rs.getInt("Type_id"),
                        rs.getString("Category_Name"),
                        rs.getString("Description")
                    );
                    categories.add(category);
                }
            }
        }
        return categories;
    }
}
