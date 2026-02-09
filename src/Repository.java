import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
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

    public ResultSet getAllShoes() throws SQLException {
        Statement s = c.createStatement();
        return s.executeQuery("select * from shoe where quantity > 0");
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