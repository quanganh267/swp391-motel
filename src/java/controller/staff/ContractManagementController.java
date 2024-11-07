package controller.staff;

import dal.ContractDAO;
import dal.MotelDAO;
import dal.RoomDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Contract;
import model.Motel;
import model.Room;
import model.User;
import model.enums.Role;

@WebServlet(name = "ContractManagementController", urlPatterns = {"/staff/contract-management"})
public class ContractManagementController extends HttpServlet {

    private final ContractDAO contractDAO = new ContractDAO();
    private final MotelDAO motelDAO = new MotelDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user != null && user.getRole().equals(Role.STAFF)) {
            List<Room> rooms = null;
            try {
                rooms = roomDAO.getAllRooms();
            } catch (SQLException ex) {
                Logger.getLogger(ContractManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<User> users = userDAO.getAllUsers();
            List<Contract> contracts = contractDAO.getAllContracts();

            request.setAttribute("rooms", rooms);
            request.setAttribute("users", users);
            request.setAttribute("contracts", contracts);

            request.getRequestDispatcher("contract-management.jsp").forward(request, response);
        } else {
            session.setAttribute("notificationErr", "Access Denied!");
            response.sendRedirect("../login");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");

        if (currentUser != null && currentUser.getRole().equals(Role.STAFF)) {
            String action = request.getParameter("action");

            if ("add".equals(action)) {
                handleAddContract(request, session);
            } else if ("edit".equals(action)) {
                handleEditContract(request, session);
            } else if ("delete".equals(action)) {
                handleDeleteContract(request, session);
            }

            response.sendRedirect("contract-management");
        } else {
            session.setAttribute("notificationErr", "Please login first!");
            response.sendRedirect("../login");
        }
    }

  private void handleAddContract(HttpServletRequest request, HttpSession session) {
    try {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int motelId = roomDAO.getRoomById(roomId).getMotelId(); // Directly use the int value
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        String paymentMethod = request.getParameter("paymentMethod");
        String paymentStatus = request.getParameter("paymentStatus");
        String paymentDate = request.getParameter("paymentDate");
        String type = request.getParameter("type");

        // Check if the contract is unique (user-room-motel combination)
        if (!contractDAO.isContractUnique(roomId, userId, motelId)) {
            session.setAttribute("notificationErr", "A contract already exists for the selected user, room, and motel.");
            return;
        }

        // Continue to add the contract if unique
        Room room = roomDAO.getRoomById(roomId);
        User user = userDAO.getUserById(userId);
        Motel motel = motelDAO.getAllMotelById(motelId);
        Contract contract = new Contract();
        contract.setMotel(motel);
        contract.setRoom(room);
        contract.setUser(user);
        contract.setTotalPrice(totalPrice);
        contract.setPaymentMethod(paymentMethod);
        contract.setPaymentStatus(paymentStatus);
        contract.setTypeOfContract(type);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = format.parse(paymentDate);
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        contract.setPaymentDate(sqlDate);

        contractDAO.insertContract(contract);
        session.setAttribute("notification", "Contract added successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("notificationErr", "Failed to add contract.");
    }
}

private void handleEditContract(HttpServletRequest request, HttpSession session) {
    try {
        int contractId = Integer.parseInt(request.getParameter("contractId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int motelId = roomDAO.getRoomById(roomId).getMotelId(); // Directly use the int value
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        String paymentMethod = request.getParameter("paymentMethod");
        String paymentStatus = request.getParameter("paymentStatus");
        String paymentDate = request.getParameter("paymentDate");
        String type = request.getParameter("type");

        // Check if the contract is unique (user-room-motel combination), except for the current contract being edited
        if (!contractDAO.isContractUnique(roomId, userId, motelId) && contractDAO.getContractById(contractId).getUser().getId() != userId) {
            session.setAttribute("notificationErr", "A contract already exists for the selected user, room, and motel.");
            return;
        }

        // Continue to update the contract if unique
        Room room = roomDAO.getRoomById(roomId);
        User user = userDAO.getUserById(userId);

        Contract contract = contractDAO.getContractById(contractId);
        contract.setRoom(room);
        contract.setUser(user);
        contract.setTotalPrice(totalPrice);
        contract.setPaymentMethod(paymentMethod);
        contract.setPaymentStatus(paymentStatus);
        contract.setTypeOfContract(type);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = format.parse(paymentDate);
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        contract.setPaymentDate(sqlDate);

        contractDAO.updateContract(contract);
        session.setAttribute("notification", "Contract updated successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("notificationErr", "Failed to update contract.");
    }
}

    private void handleDeleteContract(HttpServletRequest request, HttpSession session) {
        int contractId = Integer.parseInt(request.getParameter("contractId"));
        contractDAO.deleteContract(contractId);
        session.setAttribute("notification", "Contract deleted successfully!");
    }
}
