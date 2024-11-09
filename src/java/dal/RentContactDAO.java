package dal;

import model.RentContact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RentContactDAO extends DBContext {

    // Retrieve all rent contacts
    public List<RentContact> getAllRentContacts() {
        List<RentContact> rentContacts = new ArrayList<>();
        String sql = "SELECT * FROM Rent_Contact";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
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
            Logger.getLogger(RentContactDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rentContacts;
    }

    // Add a new rent contact
    public void addRentContact(RentContact rentContact) {
        String sql = "INSERT INTO Rent_Contact (Guest_name, Room_id, Start_date, End_date, Phone_number, Email, Address, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
            Logger.getLogger(RentContactDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}