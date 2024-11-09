package dal;

import model.Room;
import model.Categories;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Motel;
import model.RoomType;

public class RoomDAO extends DBContext {

    final MotelDAO motelDAO = new MotelDAO();

    // Retrieve all rooms with their associated room types, motel names, and images
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT Room.Room_id, Room.Motel_id, Room.Type_id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl, Room.Status AS status, Room.Price AS price "
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("Room_id"),
                        rs.getInt("Motel_id"),
                        rs.getInt("Type_id"),
                        rs.getString("imageUrl"),
                        rs.getString("motelName"),
                        rs.getString("roomTypeName"),
                        rs.getString("status"),
                        rs.getDouble("price")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rooms;
    }

    // Search rooms by motel ID and type name
    public List<Room> searchRooms(Integer motelId, String typeName) {
        List<Room> rooms = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT Room.Room_id, Room.Motel_id, Room.Type_id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl, Room.Status AS status, Room.Price AS price "
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id "
                + "WHERE 1=1"
        );

        if (motelId != null) {
            query.append(" AND Room.Motel_id = ?");
        }
        if (typeName != null && !typeName.isEmpty()) {
            query.append(" AND Room_Type.Name LIKE ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (motelId != null) {
                ps.setInt(index++, motelId);
            }
            if (typeName != null && !typeName.isEmpty()) {
                ps.setString(index++, "%" + typeName + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                            rs.getInt("Room_id"),
                            rs.getInt("Motel_id"),
                            rs.getInt("Type_id"),
                            rs.getString("imageUrl"),
                            rs.getString("motelName"),
                            rs.getString("roomTypeName"),
                            rs.getString("status"),
                            rs.getDouble("price")
                    );
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rooms;
    }

    // Fetch room details by ID
    public Room getRoomById(int roomId) {
        String query = "SELECT Room.Room_id, Room.Motel_id, Room.Type_Id, Room_Type.Name AS roomTypeName, "
                + "Motels.Motel_name AS motelName, Room.Image_Url AS imageUrl , Room.Status AS status , Room.Price AS price "
                + "FROM Room "
                + "JOIN Room_Type ON Room.Type_Id = Room_Type.Type_id "
                + "JOIN Motels ON Room.Motel_id = Motels.Motel_id "
                + "WHERE Room.Room_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getInt("Room_id"),
                            rs.getInt("Motel_id"),
                            rs.getInt("Type_id"),
                            rs.getString("imageUrl"),
                            rs.getString("motelName"),
                            rs.getString("roomTypeName"),
                            rs.getString("status"),
                            rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // Delete a room by ID
    public void deleteRoom(int roomId) {
        String query = "DELETE FROM Room WHERE Room_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Edit room details
    public void editRoom(Room room) {
        String query = "UPDATE Room SET Type_Id = ?, Image_Url = ?, Status = ?, Price = ? WHERE Room_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, room.gettypeId());
            ps.setString(2, room.getImageUrl());
            ps.setString(3, room.getStatus());
            ps.setDouble(4, room.getPrice());
            ps.setInt(5, room.getRoomId());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Change room status
    public void changeRoomStatus(int roomId, String newStatus) {
        String query = "UPDATE Room SET status = ? WHERE Room_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newStatus);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Add a new room
    public void addRoom(Room room) {
        String sql = "INSERT INTO Room (Room_id, Motel_id, Type_Id, Image_Url, Status, Price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room.getRoomId());
            statement.setInt(2, room.getMotelId());
            statement.setInt(3, room.gettypeId());
            statement.setString(4, room.getImageUrl());
            statement.setString(5, room.getStatus());
            statement.setDouble(6, room.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Retrieve categories by type ID
    public List<Categories> getCategoriesByTypeId(int typeId) {
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
        } catch (SQLException e) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return categories;
    }
}
