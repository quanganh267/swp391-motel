package controller.Room;

import dal.RoomDAO;
import model.Room;
import model.Categories;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet(name="SingleRoomServlet", urlPatterns={"/SingleRoom"})
public class SingleRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RoomDAO dao = new RoomDAO();
            int roomId = Integer.parseInt(request.getParameter("roomID"));
            Room room = dao.getRoomById(roomId);

            if (room != null) {
                // Fetch categories for the room type
                List<Categories> categories = dao.getCategoriesByTypeId(room.gettypeId());
                room.setCategories(categories);

                request.setAttribute("rooms", Collections.singletonList(room));
                request.getRequestDispatcher("Room_Single.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Room not found!");
                request.getRequestDispatcher("Room.jsp").forward(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
