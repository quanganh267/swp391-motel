package dal;

import model.RentContact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class RentContactDAO extends DBContext {

    public List<RentContact> getAllRentContacts() throws SQLException {
        List<RentContact> rentContacts = new ArrayList<>();
        String sql = "SELECT * FROM Rent_Contact";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                RentContact rentContact = new RentContact();
                rentContact.setRentContractId(rs.getInt("RentContract_id"));
                rentContact.setGuestName(rs.getString("Guest_name"));
                rentContact.setRoomId(rs.getInt("Room_id"));
                rentContact.setStartDate(rs.getDate("Start_date"));
                rentContact.setEndDate(rs.getDate("End_date"));
                rentContact.setPhoneNumber(rs.getString("Phone_number"));
                rentContact.setEmail(rs.getString("Email"));
                rentContact.setAddress(rs.getString("Address"));
                rentContact.setStatus(rs.getString("Status"));
                rentContacts.add(rentContact);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching rent contacts", e);
        }
        return rentContacts;
    }

    private java.util.Date convertToDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void addRentContact(RentContact rentContact) throws SQLException {
        String sql = "INSERT INTO Rent_Contact (Guest_name, Room_id, Start_date, End_date, Phone_number, Email, Address, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, rentContact.getGuestName());
            statement.setInt(2, rentContact.getRoomId());
            statement.setDate(3, new java.sql.Date(rentContact.getStartDate().getTime()));
            statement.setDate(4, new java.sql.Date(rentContact.getEndDate().getTime()));
            statement.setString(5, rentContact.getPhoneNumber());
            statement.setString(6, rentContact.getEmail());
            statement.setString(7, rentContact.getAddress());
            statement.setString(8, rentContact.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding rent contact", e);
        }
    }
}