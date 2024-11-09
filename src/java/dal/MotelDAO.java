package dal;

import model.Motel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotelDAO extends DBContext {

    // Insert a new Motel with automatic ID increment
    public boolean insertMotel(Motel motel) {
        String getMaxIdSql = "SELECT MAX(Motel_id) FROM Motels";
        String insertSql = "INSERT INTO Motels (Motel_id, Motel_name, description, address, city, NumberOfRoom, Image_Url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement psGetMaxId = connection.prepareStatement(getMaxIdSql);
            ResultSet rs = psGetMaxId.executeQuery();
            int nextId = 1;  // Default value if there are no records yet
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }

            PreparedStatement psInsert = connection.prepareStatement(insertSql);
            psInsert.setInt(1, nextId);
            psInsert.setString(2, motel.getMotelName());
            psInsert.setString(3, motel.getDescription());
            psInsert.setString(4, motel.getAddress());
            psInsert.setString(5, motel.getCity());
            psInsert.setInt(6, motel.getNumberOfRoom());
            psInsert.setString(7, motel.getImageUrl());

            return psInsert.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Update an existing Motel
    public boolean updateMotel(Motel motel) {
        String sql = "UPDATE Motels SET Motel_name = ?, description = ?, address = ?, city = ?, NumberOfRoom = ?, Image_Url = ? WHERE Motel_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, motel.getMotelName());
            ps.setString(2, motel.getDescription());
            ps.setString(3, motel.getAddress());
            ps.setString(4, motel.getCity());
            ps.setInt(5, motel.getNumberOfRoom());
            ps.setString(6, motel.getImageUrl());
            ps.setInt(7, motel.getMotelId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Delete a Motel by ID
    public boolean deleteMotel(int motelId) {
        String sql = "DELETE FROM Motels WHERE Motel_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, motelId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Retrieve a single Motel by ID
    public Motel getMotelById(int motelId) {
        String sql = "SELECT * FROM Motels WHERE Motel_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, motelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Motel motel = new Motel();
                motel.setMotelId(rs.getInt("Motel_id"));
                motel.setMotelName(rs.getString("Motel_name"));
                motel.setDescription(rs.getString("description"));
                motel.setAddress(rs.getString("address"));
                motel.setCity(rs.getString("city"));
                motel.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                motel.setImageUrl(rs.getString("Image_Url"));
                return motel;
            }
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // Retrieve all motels or search by name
    public List<Motel> getAllMotels(String searchTerm) {
        List<Motel> motels = new ArrayList<>();
        String sql;
        
        if (searchTerm == null || searchTerm.isEmpty()) {
            sql = "SELECT * FROM Motels";
        } else {
            sql = "SELECT * FROM Motels WHERE Motel_name LIKE ?";
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (searchTerm != null && !searchTerm.isEmpty()) {
                ps.setString(1, "%" + searchTerm + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Motel motel = new Motel();
                motel.setMotelId(rs.getInt("Motel_id"));
                motel.setMotelName(rs.getString("Motel_name"));
                motel.setDescription(rs.getString("description"));
                motel.setAddress(rs.getString("address"));
                motel.setCity(rs.getString("city"));
                motel.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                motels.add(motel);
            }
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return motels;
    }

    // Retrieve all motels with their associated details
    public List<Motel> getAllMotels() throws SQLException {
        List<Motel> motels = new ArrayList<>();
        String query = "SELECT Motel_id, Motel_name, description, address, city, NumberOfRoom, Image_Url FROM Motels";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Motel motel = new Motel();
                motel.setMotelId(rs.getInt("Motel_id"));
                motel.setMotelName(rs.getString("Motel_name"));
                motel.setDescription(rs.getString("description"));
                motel.setAddress(rs.getString("address"));
                motel.setCity(rs.getString("city"));
                motel.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                motel.setImageUrl(rs.getString("Image_Url"));
                motels.add(motel);
                System.out.println(motel);
            }
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
        
        return motels;
    }

    public List<Motel> searchMotels(String city) throws SQLException {
        List<Motel> motels = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Motels WHERE 1=1");
        
        if (city != null && !city.isEmpty()) {
            sql.append(" AND city = ?");
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            if (city != null && !city.isEmpty()) {
                ps.setString(1, city);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Motel motel = new Motel();
                motel.setMotelId(rs.getInt("Motel_id"));
                motel.setMotelName(rs.getString("Motel_name"));
                motel.setDescription(rs.getString("description"));
                motel.setAddress(rs.getString("address"));
                motel.setCity(rs.getString("city"));
                motel.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                motels.add(motel);
            }
        } catch (SQLException e) {
            Logger.getLogger(MotelDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
        
        return motels;
    }
}
