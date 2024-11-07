package dal;

import model.Guest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO extends DBContext {

    public List<Guest> getAllGuests() throws SQLException {
        List<Guest> guests = new ArrayList<>();
        String query = "SELECT * FROM Guest";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Guest guest = new Guest(
                    rs.getInt("Guest_id"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getString("PhoneNum"),
                    rs.getString("Identity_id")
                );
                guests.add(guest);
            }
        }
        return guests;
    }
} 