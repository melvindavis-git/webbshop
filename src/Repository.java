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

    public boolean login(String username, String password) throws SQLException {
        String sql =
                "select * from customer where name = ? and password = ?";

        try (PreparedStatement pStatement = c.prepareStatement(sql)){
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            ResultSet rs = pStatement.executeQuery();
            return rs.next();
        }

    }

    public ResultSet getAllShoes() throws SQLException {
        Statement s = c.createStatement();
        return s.executeQuery("select * from shoe where quantity > 0");
    }

}