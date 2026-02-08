import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Webbshop {

    private Scanner scanner = new Scanner(System.in);
    private Repository repo;
    private Integer customerId;
    private Integer shoeId;

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

            customerId = repo.login(username, password);
            if (customerId != null) {
                System.out.println("Signed in as " + username);
                break;
            } else {
                System.out.println("Wrong username or password.");
            }
        }
        listShoesAndAddToCart();
    }

    private void listShoesAndAddToCart() throws SQLException {
        ResultSet rs = repo.getAllShoes();
        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + ". " + rs.getString("color") + " " +
                            rs.getString("brand") + ", " + "s." + rs.getInt("size") + " " +
                            rs.getInt("price") + "kr" + " " + "(stock: " +
                            rs.getString("quantity") + ")"
            );
        }


        while (true) {
            System.out.println("Enter the number of the shoe you want to add to your cart: ");
            shoeId = Integer.parseInt(scanner.nextLine());
            repo.addToCart(customerId, shoeId);
            System.out.println("Added to cart.");
            System.out.print("Do you want to add another item to your cart? (y/n): ");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("y")) {
            } else if (userChoice.equalsIgnoreCase("n")) {
                System.out.println("Thanks for shopping at MD's shoe store.");
                break;
            } else {
                System.out.print("Please enter 'y' or 'n'.");
            }
        }
    }


}
