/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Motel;
import model.Room;
import dal.MotelDAO;
import dal.RoomDAO;

/**
 *
 * @author ADMIN
 */
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAOs
        MotelDAO motelDAO = new MotelDAO();
        RoomDAO roomDAO = new RoomDAO();

        try {
            // Fetch motels and rooms
            List<Motel> motels = motelDAO.getAllMotels();
            List<Room> rooms = roomDAO.getAllRooms();

            // Shuffle and select a subset
            Collections.shuffle(motels);
            Collections.shuffle(rooms);

            // Define the number of items to display
            int numberOfMotelsToShow = 3; // Adjust as needed
            int numberOfRoomsToShow = 3;  // Adjust as needed

            // Get sublists
            List<Motel> featuredMotels = motels.subList(0, Math.min(numberOfMotelsToShow, motels.size()));
            List<Room> featuredRooms = rooms.subList(0, Math.min(numberOfRoomsToShow, rooms.size()));

            // Set attributes for JSP
            request.setAttribute("featuredMotels", featuredMotels);
            request.setAttribute("featuredRooms", featuredRooms);
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, set an error message attribute to display on the JSP
            request.setAttribute("errorMessage", "An error occurred while fetching data.");
        }

        // Forward to home.jsp
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests if needed
    }

   

}
