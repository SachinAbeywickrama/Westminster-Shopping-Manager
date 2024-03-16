import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public WestminsterShoppingManager() {
    }

    Scanner scanner = new Scanner(System.in);

    @Override
    public void addProduct() {
        // Generates a unique product ID
        String productId = generateProductId();

        // Get common product attributes
        String productName;
        do {
            System.out.print("Enter product name: ");
            productName = scanner.next();
        } while (productName.isEmpty());

        int availableItems;
        do {
            System.out.print("Enter available items: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid available items.");
                scanner.next();
            }
            availableItems = scanner.nextInt();
        } while (availableItems < 0);


        double price;
        do {
            System.out.print("Enter price: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid Price.");
                scanner.next();
            }
            price = scanner.nextDouble();
        } while (price < 0);

        // Get product-specific attributes based on the product type
        String productType;
        do {
            System.out.print("Enter Product Type (electronics or clothing): ");
            productType = scanner.next().toLowerCase();
        } while (!productType.equals("electronics") && !productType.equals("clothing"));

        if (productType.equals("electronics")) {
            // Electronics-specific attributes
            System.out.print("Enter brand: ");
            String brand = scanner.next();

            System.out.print("Enter warranty period: ");
            String warrantyPeriod = scanner.next();

            // Create and add an Electronics product to the list
            Product product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
            Main.products.add(product);
            System.out.println();
            System.out.println("Electronics Product added successfully.");
            System.out.println("The Product in the " + product.getProductType() + " with Product ID - " + product.getProductId() + " and product name " + product.getProductName() + " added successfully.");
        } else {
            // Clothing-specific attributes
            System.out.print("Enter size: ");
            String size = scanner.next();

            System.out.print("Enter color: ");
            String color = scanner.next();

            // Create and add a Clothing product to the list
            Product product = new Clothing(productId, productName, availableItems, price, size, color);
            Main.products.add(product);
            System.out.println();
            System.out.println("Clothing Product added successfully.");
            System.out.println("The Product in the " + product.getProductType() + " with Product ID - " + product.getProductId() + " and product name " + product.getProductName() + " added successfully.");
        }
    }

    // Counter for generating unique product IDs
    private static int productIdCounter = 100;  // Initial counter value

    // Generates a unique product ID
    public static String generateProductId() {
        String newProductId;
        // Increment the counter and use it in the product ID
        newProductId = "A" + productIdCounter++;
        return newProductId;
    }
  
    @Override
    public void deleteProduct(String productId) {
        Product productToDelete;
        for (Product product : Main.products) {
            if (product.getProductId().equals(productId)) {
                productToDelete = product;
                Main.products.remove(product);
                System.out.println();
                System.out.println("The Product in the " + productToDelete.getProductType() + " with Product ID - " + productToDelete.getProductId() + " and product name " + productToDelete.getProductName() + " deleted successfully.");
                System.out.println("Total number of products left: " + Main.products.size());
                return;
            }
        }
        System.out.println("Product not found with the given ID.");
    }

    boolean found = false;
    @Override
    public void printList(String category) {
        List<Product> sortedProducts = new ArrayList<>(Main.products);

        // Sort the products based on product ID
        sortedProducts.sort(Comparator.comparing(Product::getProductId));

        for (Product product : sortedProducts) {
            if (Objects.equals(category, "electronics") && product instanceof Electronics) {
                // Print Electronics product details
                System.out.println("Electronics Product - ");
                System.out.println("Product ID: " + product.getProductId() + " - Product Name: " + product.getProductName() + " - Available Items: " + product.getAvailableItems() + " - Price: " + product.getPrice() + " - Product Brand: " + ((Electronics) product).getBrand() + " - Warranty Period: " + ((Electronics) product).getWarrantyPeriod());
                found = true;
            } else if (Objects.equals(category, "clothing") && product instanceof Clothing) {
                // Print Clothing product details
                System.out.println("Clothing Product - ");
                System.out.println("Product ID: " + product.getProductId() + " - Product Name: " + product.getProductName() + " - Available Items: " + product.getAvailableItems() + " - Price: " + product.getPrice() + " - Product Size: " + ((Clothing) product).getSize() + " - Product Color: " + ((Clothing) product).getColor());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items to print for the selected category: " + category);
        }
    }

    @Override
    public void saveToFile() {
        try (FileWriter fileWrite = new FileWriter("courseWork.txt")) {
            for (Product product : Main.products) {
                // Write product details to the file
                fileWrite.write(product.toString());
                fileWrite.write("\n");
            }
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error saving product list to file: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        try (BufferedReader fileRead = new BufferedReader(new FileReader("courseWork.txt"))) {
            String line;
            Main.products.clear();
            while ((line = fileRead.readLine()) != null) {
                // Create a Product object from each line in the file
                Product product = ProductCreation(line);
                if (product != null) {
                    Main.products.add(product);
                    productIdCounter++;
                }
            }
            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading product list from file: " + e.getMessage());
        }
    }

    // Creates a Product object from a string representation
    private Product ProductCreation(String lineByLine) {
        try {
            String[] values = lineByLine.split(",");

            if (values[0].startsWith("Electronics")) {
                return new Electronics(values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), values[5], values[6]);
            } else if (values[0].startsWith("Clothing")) {
                return new Clothing(values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), values[5], values[6]);
            }else{
                return null;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing product from string: " + e.getMessage());
            return null;
        }
    }

}


