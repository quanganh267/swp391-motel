package controller;

import dal.QandADAO;
import model.QandA;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "QandAServlet", urlPatterns = {"/qanda"})
public class QandAServlet extends HttpServlet {

    private QandADAO qandADAO = new QandADAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<QandA> qAndAList = qandADAO.getAllQandA();
            request.setAttribute("qAndAList", qAndAList);
            request.getRequestDispatcher("qanda.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve Q&A from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int guestId = Integer.parseInt(request.getParameter("guestId"));
            String guestName = request.getParameter("guestName");
            String email = request.getParameter("email");
            String question = request.getParameter("question");

            QandA qAndA = new QandA();
            qAndA.setGuestId(guestId);
            qAndA.setGuestName(guestName);
            qAndA.setEmail(email);
            qAndA.setQuestion(question);

            qandADAO.addQandA(qAndA);
            response.sendRedirect("qanda");
        } catch (SQLException e) {
            throw new ServletException("Error adding Q&A", e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid guest ID format", e);
        }
    }
} 