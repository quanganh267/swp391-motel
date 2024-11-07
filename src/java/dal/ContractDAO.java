package dal;

import model.Contract;
import model.Motel;
import model.Room;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO extends DBContext {

    private final MotelDAO motelDAO = new MotelDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final UserDAO userDAO = new UserDAO();

    // Retrieve all contracts
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM Contract";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                contracts.add(mapContract(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    // Insert a new contract
    public boolean insertContract(Contract contract) {
        String sql = "INSERT INTO Contract (Room_id, User_ID, motel_id, Total_Price, CreateAt, Payment_method, Payment_Status, Payment_Date, TypeOfContract) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        List<Object> params = List.of(
                contract.getRoom().getRoomId(),
                contract.getUser().getId(),
                contract.getMotel().getMotelId(),
                contract.getTotalPrice(),
                new java.sql.Date(contract.getCreateAt().getTime()),
                contract.getPaymentMethod(),
                contract.getPaymentStatus(),
                new java.sql.Date(contract.getPaymentDate().getTime()),
                contract.getTypeOfContract()
        );
        return executeUpdate(sql, params);
    }

    // Update an existing contract
    public boolean updateContract(Contract contract) {
        String sql = "UPDATE Contract SET Room_id = ?, User_ID = ?, motel_id = ?, Total_Price = ?, CreateAt = ?, Payment_method = ?, Payment_Status = ?, Payment_Date = ?, TypeOfContract = ? WHERE Contract_id = ?";
        List<Object> params = List.of(
                contract.getRoom().getRoomId(),
                contract.getUser().getId(),
                contract.getMotel().getMotelId(),
                contract.getTotalPrice(),
                new java.sql.Date(contract.getCreateAt().getTime()),
                contract.getPaymentMethod(),
                contract.getPaymentStatus(),
                new java.sql.Date(contract.getPaymentDate().getTime()),
                contract.getTypeOfContract(),
                contract.getId()
        );
        return executeUpdate(sql, params);
    }

    // Delete a contract
    public boolean deleteContract(int contractId) {
        String sql = "DELETE FROM Contract WHERE Contract_id = ?";
        return executeUpdate(sql, List.of(contractId));
    }

    // Retrieve a contract by ID
    public Contract getContractById(int contractId) {
        String sql = "SELECT * FROM Contract WHERE Contract_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contractId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapContract(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Pagination for contracts
    public List<Contract> getPagedContracts(List<Contract> contracts, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, contracts.size());
        return contracts.subList(fromIndex, toIndex);
    }

    // Check contract uniqueness
    public boolean isContractUnique(int roomId, int userId, int motelId) {
        String sql = "SELECT COUNT(*) FROM Contract WHERE Room_id = ? AND User_ID = ? AND Motel_id = ?";
        List<Object> params = List.of(roomId, userId, motelId);
        try (PreparedStatement ps = prepareStatement(sql, params);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper methods
    private Contract mapContract(ResultSet rs) throws SQLException {
        Contract contract = new Contract();
        contract.setId(rs.getInt("Contract_id"));
        contract.setRoom(roomDAO.getRoomById(rs.getInt("Room_id")));
        contract.setUser(userDAO.getUserById(rs.getInt("User_ID")));
        contract.setMotel(motelDAO.getAllMotelById(rs.getInt("motel_id")));
        contract.setTotalPrice(rs.getDouble("Total_Price"));
        contract.setCreateAt(rs.getDate("CreateAt"));
        contract.setPaymentMethod(rs.getString("Payment_method"));
        contract.setPaymentStatus(rs.getString("Payment_Status"));
        contract.setPaymentDate(rs.getDate("Payment_Date"));
        contract.setTypeOfContract(rs.getString("TypeOfContract"));
        return contract;
    }

    private boolean executeUpdate(String sql, List<Object> params) {
        try (PreparedStatement ps = prepareStatement(sql, params)) {
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private PreparedStatement prepareStatement(String sql, List<Object> params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        return ps;
    }
}
