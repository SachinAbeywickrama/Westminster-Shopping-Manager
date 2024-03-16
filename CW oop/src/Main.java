import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Constants
    static final int MAX_PRODUCTS = 50;
    // Data structures
    static ArrayList<Product> products = new ArrayList<>();
    static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    static User customer = new User("sachin", "12345"); // Set a username and password for the manager
    static User manager = new User("sachin", "12345");

    public static void main(String[] args) {
        Scanner scanner;
        int choice1;
        do {
            System.out.println();
            System.out.println("""
                    ---------------
                      # Welcome #
                    ---------------
                    1. Manager
                    2. Customer
                    0. Exit
                    ---------------
                    """);
            System.out.print("Enter your choice (1,2 or 0) : ");

            scanner = new Scanner(System.in);
            try {
                choice1 = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                choice1 = -1;
            }
            switch (choice1) {
                case 1:
                    westminsterShoppingManager.readFromFile();
                    // manager login
                    System.out.println();
                    System.out.println("""
                    ------------------------------
                    ----     Sachin Abey      ----
                    ------------------------------
                     # Welcome To Manager Login #
                    ------------------------------
                    """);
                    System.out.print("Enter username: ");
                    String enteredUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String enteredPassword = scanner.nextLine();

                    if (authenticate(manager, enteredUsername, enteredPassword)) {
                        System.out.println("Manager logged in successfully.");
                        runConsoleMenu();
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    choice1 = 0;
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String enteredCustomerName = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String enteredCustomerPassword = scanner.nextLine();

                    if (authenticate(customer, enteredCustomerName, enteredCustomerPassword)) {
                        System.out.println("Manager logged in successfully.");
                        // Customer login
                        SwingUtilities.invokeLater(() -> {
                            GUI frame = new GUI();
                            frame.setVisible(true);
                        });
                        break;
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting Console Menu. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        } while (choice1 != 0);
        scanner.close();
    }

    // Authenticates the user based on entered credentials
    private static boolean authenticate(User user, String enteredUsername, String enteredPassword) {
        return user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword);
    }

    // Runs the console menu for the manager
    public static void runConsoleMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice2;

        do {
            System.out.println();
            System.out.println("""
                    ------------------------------
                    ----     Sachin Abey      ----
                    ------------------------------
                    ----     Console Menu     ----
                    ------------------------------
                    1. Add a new product
                    2. Delete a product
                    3. Print the list of products
                    4. Save to a file
                    0. Exit
                    ------------------------------
                    """);
            System.out.print("Enter your choice (1,2,3,4 or 0) : ");

            try {
                choice2 = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                choice2 = -1;
            }

            switch (choice2) {
                case 1:
                    if (products.size() < MAX_PRODUCTS) {
                        westminsterShoppingManager.addProduct();
                    }else {
                        System.out.println("Cannot add more products. Maximum limit reached.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the product ID to delete: ");
                    String productIdToDelete = scanner.nextLine();
                    westminsterShoppingManager.deleteProduct(productIdToDelete);
                    break;

                case 3:
                    System.out.print("Enter the product type to print (electronics or clothing): ");
                    String desiredProductType = scanner.next().toLowerCase();
                    westminsterShoppingManager.printList(desiredProductType);
                    break;

                case 4:
                    westminsterShoppingManager.saveToFile();
                    break;

                case 0:
                    System.out.println("Exiting Console Menu. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }

        } while (choice2 != 0);

        scanner.close();
    }

}