import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Repository {

    public void repo() throws IOException {


        Properties p = new Properties();
        p.load(new FileInputStream("src/settings.properties"));

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connStr"),
                p.getProperty("name"),
                p.getProperty("password"));

             Statement statement = c.createStatement();
             ResultSet rs = statement.executeQuery("select * from customer")

        ) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}