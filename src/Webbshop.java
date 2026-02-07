import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Webbshop {

    private Scanner scanner = new Scanner(System.in);
    private Repository repo;

    public Webbshop(Repository repo) {
        this.repo = repo;
    }

    public void start() throws SQLException {

        System.out.println("Welcome to MD's shoe webbshop.");

        while (true) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();


            if (repo.login(username, password)) {
                System.out.println("Signed in as " + username);
                break;
            } else {
                System.out.println("Wrong username or password.");
            }
        }
        listAllShoes();
    }

    private void listAllShoes() throws SQLException {
        ResultSet rs = repo.getAllShoes();
        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + ". " + rs.getString("color") + " " +
                            rs.getString("brand") + ", " + "s." + rs.getInt("size") + " " +
                            rs.getInt("price") + "kr"
            );
        }

        System.out.println("Enter the number of the shoe you want to add to your cart: ");
        int shoeChoice = scanner.nextInt();

    }


}
