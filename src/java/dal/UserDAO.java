package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.enums.Role;
import model.enums.Status;

public class UserDAO extends DBContext {

    public boolean emailExists(String email) {
        String sql = "SELECT * FROM USERS WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users where role = 'USER'";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("User_id"));
                user.setName(rs.getString("User_Name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("Phone_Num"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean phoneExists(String phone) {
        String sql = "SELECT * FROM USERS WHERE Phone_Num = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // Return true if the email exists
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public User getUserIdByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("User_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("User_Name"));
                user.setPhone(rs.getString("Phone_Num"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;  // Return false if not found
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM USERS WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("User_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("User_Name"));
                user.setPhone(rs.getString("Phone_Num"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;  // Return false if not found
    }

    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE USERS SET password = ? WHERE email = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String sql = """
                    SELECT * FROM USERS WHERE  email = ? AND password = ?
                     """;
        try {
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("User_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("User_Name"));
                user.setPhone(rs.getString("Phone_Num"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO USERS (User_Name, email, password, Phone_Num, role, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole().name().toUpperCase());
            ps.setString(6, user.getStatus().name().toUpperCase());
            
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;  // Return true if the insertion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean activateUserByEmail(String email) {
        String sql = "UPDATE USERS SET status = ?  WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, Status.ACTIVE.name());
            ps.setString(2, email);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUserWithParam(String searchParam, String status) {
        List<User> users = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        UserDAO userDao = new UserDAO();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM USERS  WHERE 1 = 1");
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND  User_Name LIKE ? ");
                list.add("%" + searchParam + "%");
            }
            if (status != null) {
                query.append(" AND  status = ? ");
                list.add(status);
            }
            
            query.append("""
                ORDER BY User_id DESC
                """);
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("User_id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("User_Name"));
                    user.setPhone(rs.getString("Phone_Num"));
                    user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                    user.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUserStatus(int userId, String status) {
        String sql = "UPDATE USERS SET status = ? WHERE User_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> Paging(List<User> users, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, users.size());
        
        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }
        
        return users.subList(fromIndex, toIndex);
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else {
                ps.setString(i++, (String) arg);
            }
        }
    }
}
