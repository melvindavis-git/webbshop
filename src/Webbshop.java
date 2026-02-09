import java.sql.SQLException;
import java.util.List;
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

    public void printAllShoes() throws SQLException {
        List<Shoe> shoes = repo.getAllShoes();
        for (Shoe s : shoes) {
            System.out.println(
                    s.getId() + ". " + s.getColor() + " " + s.getBrand() + ", s." + s.getSize()
                    + " " + s.getPrice() + "kr " + "(stock: " + s.getQuantity() + ")"
            );
        }
    }

    private void listShoesAndAddToCart() throws SQLException {
        while (true) {
            printAllShoes();
            System.out.println("('999' to exit) Enter the number of the shoe you want to add to your cart: ");


            try {
                shoeId = Integer.parseInt(scanner.nextLine());
                if (shoeId == 999){
                    System.out.println("Exiting store..");
                    break;
                }
                repo.addToCart(customerId, shoeId);
                System.out.println("Added to cart.");

                while (true) {
                    System.out.print("Do you want to add another item to your cart? (y/n): ");
                    String userChoice = scanner.nextLine().trim();

                    if (userChoice.equalsIgnoreCase("y")) {
                        break;
                    } else if (userChoice.equalsIgnoreCase("n")) {
                        while (true) {
                            System.out.print("Do you wish to pay for you order now or later?: ");
                            String userChoice2 = scanner.nextLine().trim();
                            if (userChoice2.equalsIgnoreCase("now")) {
                                repo.payOrder(customerId);
                                System.out.println("Thanks for shopping at MD's shoe store! \n" +
                                        "An order receipt has been sent to you.");
                                return;
                            } else if (userChoice2.equalsIgnoreCase("later")) {
                                System.out.println("Exiting store..");
                                return;
                            } else {
                                System.out.println("Please enter 'now' or 'later'.");
                            }
                        }

                    } else {
                        System.out.println("Please enter 'y' or 'n'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter only numbers.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}