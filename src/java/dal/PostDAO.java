package dal;

import model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDAO extends DBContext {

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Post";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("Post_id"));
                post.setTitle(rs.getString("Title"));
                post.setContent(rs.getString("Content"));
                post.setCreatedAt(rs.getDate("Created_at"));
                post.setUpdatedAt(rs.getDate("Updated_at"));
                post.setCategory(rs.getString("Category"));
                post.setStaffId(rs.getInt("Staff_id"));
                posts.add(post);
            }
        } catch (SQLException e) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return posts;
    }
} 