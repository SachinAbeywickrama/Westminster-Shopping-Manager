import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

    private static final ArrayList<Product> shoppingCart = new ArrayList<>();

    public static ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    // Calculate the First Time Purchase discount
    static double calculateFirstTimePurchaseDiscount(ArrayList<Product> products) {
        double discount = 0;
        for (Product product : products) {
            if (product.isFirstTimePurchase()) {
                discount += product.getPrice() * 0.10; // 10% discount for each first-time purchase
            }
        }
        return discount;
    }

    // Calculate the total discount for products in the same category
    static double calculateSameCategoryDiscount(ArrayList<Product> products) {
        // Group products by category and count the occurrences
        Map<String, Long> categoryCounts = products.stream().collect(Collectors.groupingBy(Product::getProductType, Collectors.counting()));

        double totalDiscount = 0;

        // Check each category and apply discount if count is 3 or more
        for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            if (entry.getValue() >= 3) {
                totalDiscount += calculateTotal((ArrayList<Product>) products.stream()
                        .filter(product -> product.getProductType().equals(entry.getKey()))
                        .collect(Collectors.toList())) * 0.20;
            }
        }

        return totalDiscount;
    }

    // Calculate the Final Total with discounts
    static double calculateTotalWithDiscounts(ArrayList<Product> products) {
        double total = calculateTotal(products);
        double firstTimePurchaseDiscount = calculateFirstTimePurchaseDiscount(products);
        double sameCategoryDiscount = calculateSameCategoryDiscount(products);
        return total - firstTimePurchaseDiscount - sameCategoryDiscount;
    }

    // Calculate the total price of products in the shopping cart
    static double calculateTotal(ArrayList<Product> products) {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    static void calculateDiscountsAndTotal(ArrayList<Product> shoppingCartProducts, JLabel beforeDiscountLabel, JLabel firstTimePurchaseDiscountLabel, JLabel sameCategoryDiscountLabel, JLabel finalTotalLabel, JFrame shoppingCartFrame) {
        // Calculate discounts and total
        double beforeDiscountTotal = ShoppingCart.calculateTotal(shoppingCartProducts);
        double firstTimePurchaseDiscount = ShoppingCart.calculateFirstTimePurchaseDiscount(shoppingCartProducts);
        double sameCategoryDiscount = ShoppingCart.calculateSameCategoryDiscount(shoppingCartProducts);
        double finalTotal = ShoppingCart.calculateTotalWithDiscounts(shoppingCartProducts);

        // Update labels with the calculated values
        beforeDiscountLabel.setText("Before the Discount total: $" + String.format("%.2f", beforeDiscountTotal));
        firstTimePurchaseDiscountLabel.setText("First Time Purchase discount: -$" + String.format("%.2f", firstTimePurchaseDiscount));
        sameCategoryDiscountLabel.setText("Three items in the same category discount: -$" + String.format("%.2f", sameCategoryDiscount));
        finalTotalLabel.setText("Final Total: $" + String.format("%.2f", finalTotal));
    }
}