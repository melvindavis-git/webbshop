import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    private Connection c;

    public Repository() throws IOException, SQLException {

        Properties p = new Properties();
        p.load(new FileInputStream("src/settings.properties"));

        c = DriverManager.getConnection(
                p.getProperty("connStr"),
                p.getProperty("name"),
                p.getProperty("password"));

    }

    public Integer login(String username, String password) throws SQLException {
        String sql = "select * from customer where name = ? and password = ?";

        try (PreparedStatement pStatement = c.prepareStatement(sql)) {
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            ResultSet rs = pStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        }

    }

    public List<Shoe> getAllShoes() throws SQLException {
        String sql = "select * from shoe";
        List<Shoe> shoes = new ArrayList<>();

        try (Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Shoe shoe = new Shoe(
                        rs.getInt("id"), rs.getString("brand"), rs.getString("color"),
                        rs.getInt("size"), rs.getInt("price"), rs.getInt("quantity"));
                shoes.add(shoe);
            }
        }
        return shoes;
    }

    public void addToCart(int c_id, int s_id) throws SQLException {
        String sql = "call AddToCart(?, ?)";

        try (CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, c_id);
            cs.setInt(2, s_id);
            cs.execute();
        }
    }

    public void payOrder(int c_id) throws SQLException {
        String sql = "call payOrder(?)";

        try (CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, c_id);
            cs.execute();
        }
    }

}