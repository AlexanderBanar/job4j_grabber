package html;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            String url = cfg.getProperty("url");
            String login = cfg.getProperty("login");
            String password = cfg.getProperty("password");
            this.cnn = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                this.cnn.prepareStatement(
                        "insert into post(id, name, text, link, created) values (?, ?, ?, ?, ?)"
                                + "on conflict (id) do nothing")) {
            statement.setInt(1, post.getId());
            statement.setString(2, post.getName());
            statement.setString(3, post.getText());
            statement.setString(4, post.getLink());
            statement.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> resultList = new ArrayList<>();
        String query = "select * from post";
        try (Statement stmt = this.cnn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String text = rs.getString("text");
                String link = rs.getString("link");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                resultList.add(new Post(id, name, text, link, created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Post findById(String id) {
        int idInt = Integer.parseInt(id);
        Post resultPost = new Post(idInt, null, null, null, null);
        try (PreparedStatement statement =
                     this.cnn.prepareStatement(
                             "select * from post where id = ?")) {
            statement.setInt(1, idInt);
            ResultSet rs = statement.executeQuery();
            rs.next();
            resultPost.setName(rs.getString(2));
            resultPost.setText(rs.getString(3));
            resultPost.setLink(rs.getString(4));
            resultPost.setCreated(rs.getTimestamp(5).toLocalDateTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultPost;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
