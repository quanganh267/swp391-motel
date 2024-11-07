package controller;

import dal.PostDAO;
import model.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostServlet", urlPatterns = {"/about"})
public class PostServlet extends HttpServlet {

    private PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Post> posts = postDAO.getAllPosts();
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("about.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve posts from DB", e);
        }
    }
} 