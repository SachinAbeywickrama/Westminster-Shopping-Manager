import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GUI extends JFrame {
    private static JTable table;
    private static DefaultTableModel tableModel;
    private final JLabel selectedProductDetailsLabel;
    private final JTextArea selectedProductDetailsArea;

    public GUI() {
        setTitle("Westminster Shopping Centre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 750);

        // Create panels for top, bottom, and center sections
        JPanel Panel_1 = new JPanel();
        JPanel Panel_2 = new JPanel();
        JPanel Panel_3 = new JPanel();

        // Set layouts for panels
        Panel_1.setLayout(new BorderLayout());
        Panel_2.setLayout(new BorderLayout());
        Panel_3.setLayout(new BorderLayout());

        // Set preferred size for the Panel_1
        Panel_1.setPreferredSize(new Dimension(700, 100));

        // Add content to top, bottom, and center panels

        // Left section in top panel
        JPanel Panel_1_Left = new JPanel();
        Panel_1_Left.add(new JLabel("Select Products Categories"));
        Panel_1.add(Panel_1_Left, BorderLayout.WEST);

        // Center section in top panel
        JPanel Panel_1_Center = new JPanel();
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        Panel_1_Center.add(categoryComboBox);
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            System.out.println("Selected category: " + selectedCategory);
            // Perform actions based on the selected category
            filterTableByCategory(selectedCategory);
        });
        Panel_1.add(Panel_1_Center, BorderLayout.CENTER);

        JPanel Panel_1_Right = new JPanel();
        JButton shoppingCartButton = new JButton("Shopping Cart");
        Panel_1_Right.add(shoppingCartButton);
        shoppingCartButton.addActionListener(e -> openShoppingCartWindow());
        Panel_1.add(Panel_1_Right, BorderLayout.EAST);


        // Create table with columns and data
        String[] columnNames = {"ProductID", "Name", "Category", "Price", "Info"};
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);

        // Divide the center panel into left, center, and right sections
        JPanel Panel_2_Left = new JPanel();
        JPanel Panel_2_CenterTable = new JPanel();
        JPanel Panel_2_Right = new JPanel();

        Panel_2_CenterTable.add(new JScrollPane(table));
        Panel_2_CenterTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);


        // Set layouts for the divided sections
        Panel_2.setLayout(new BorderLayout());
        Panel_2.add(Panel_2_Left, BorderLayout.WEST);
        Panel_2.add(Panel_2_CenterTable, BorderLayout.CENTER);
        Panel_2.add(separator, BorderLayout.SOUTH); // Add the separator below the table
        Panel_2.add(Panel_2_Right, BorderLayout.EAST);

        // Create label for selected product details
        selectedProductDetailsLabel = new JLabel("Selected Product - Details: ");
        Panel_3.add(selectedProductDetailsLabel, BorderLayout.NORTH);

        // Create text area for selected product details
        selectedProductDetailsArea = new JTextArea();
        selectedProductDetailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(selectedProductDetailsArea);
        detailsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Panel_3.add(detailsScrollPane, BorderLayout.CENTER);

        // Create a panel for the "Add to Shopping Cart" button
        JPanel Panel_4_addToCart = new JPanel();
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        Panel_4_addToCart.add(addToCartButton);
        addToCartButton.addActionListener(e -> addToShoppingCart()); // Add ActionListener to the button
        Panel_3.add(Panel_4_addToCart, BorderLayout.SOUTH);

        // Add panels to the frame
        add(Panel_1, BorderLayout.NORTH);
        add(Panel_2, BorderLayout.CENTER);
        add(Panel_3, BorderLayout.SOUTH);

        // Add ListSelectionListener to the table to track row selection
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    // Retrieve details of the selected product
                    String productId = (String) tableModel.getValueAt(selectedRow, 0);
                    String productName = (String) tableModel.getValueAt(selectedRow, 1);
                    String category = (String) tableModel.getValueAt(selectedRow, 2);
                    double price = (double) tableModel.getValueAt(selectedRow, 3);
                    String info = (String) tableModel.getValueAt(selectedRow, 4);
                    int availableItems = Main.products.get(selectedRow).getAvailableItems();

                    // Create a bold font
                    Font boldFont = new Font(selectedProductDetailsLabel.getFont().getName(), Font.BOLD, selectedProductDetailsLabel.getFont().getSize());
                    // Set the bold font to the label
                    selectedProductDetailsLabel.setFont(boldFont);

                    // Update the label with the selected product details, including available items
                    selectedProductDetailsLabel.setText("Selected Product - Details :");
                    updateSelectedProductDetails(productId, productName, category, price, info, availableItems);

                    selectedProductDetailsArea.setFont(new Font(selectedProductDetailsArea.getFont().getName(), Font.PLAIN, 14));
                }
            }
        });

        // Automatically fill the table with data from WestminsterShoppingManager
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.readFromFile();  // Load data from file
        updateTable();  // Update the table with the loaded data
    }

    private void updateSelectedProductDetails(String productId, String productName, String category, double price, String info, int availableItems) {
        // Update the text area with the selected product details
        selectedProductDetailsArea.setText(String.format("Product Id: %s\nCategory: %s\nName: %s\nPrice: %.2f\nAvailable Items: %d\nInfo: %s",
                productId, category, productName, price, availableItems, info));
    }


    private void filterTableByCategory(String selectedCategory) {
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Add rows to the table based on the selected category
        for (Product product : Main.products) {
            if ("All".equals(selectedCategory) ||
                    ("Electronics".equals(selectedCategory) && product instanceof Electronics) ||
                    ("Clothing".equals(selectedCategory) && product instanceof Clothing)) {

                if (product instanceof Electronics) {
                    tableModel.addRow(new Object[]{
                            product.getProductId(),
                            product.getProductName(),
                            "Electronics",
                            product.getPrice(),
                            ((Electronics) product).getBrand() + " - " + ((Electronics) product).getWarrantyPeriod()
                    });
                } else if (product instanceof Clothing) {
                    tableModel.addRow(new Object[]{
                            product.getProductId(),
                            product.getProductName(),
                            "Clothing",
                            product.getPrice(),
                            ((Clothing) product).getSize() + " - " + ((Clothing) product).getColor()
                    });
                }
            }
        }
    }

    private void updateTable() {
        Main.products.sort(Comparator.comparing(Product::getProductId));
        // Clear existing data in the table
        tableModel.setRowCount(0);
        // Add rows to the table based on the sorted data
        for (Product product : Main.products) {
            if (product instanceof Electronics) {
                tableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        "Electronics",
                        product.getPrice(),
                        ((Electronics) product).getBrand() + " - " + ((Electronics) product).getWarrantyPeriod()
                });
            } else if (product instanceof Clothing) {
                tableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        "Clothing",
                        product.getPrice(),
                        ((Clothing) product).getSize() + " - " + ((Clothing) product).getColor()
                });
            }
        }
    }


    // Method to handle adding the selected product to the shopping cart
    private void addToShoppingCart() {
        // Get the selected row
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // Retrieve details of the selected product
            String productId = (String) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            double price = (double) tableModel.getValueAt(selectedRow, 3);

            // Determine the type of product (Electronics or Clothing)
            String category = (String) tableModel.getValueAt(selectedRow, 2);

            // Create a new Product object with the selected details
            Product selectedProduct;
            if ("Electronics".equals(category)) {
                // If the category is Electronics, create an instance of Electronics
                selectedProduct = new Electronics(productId, productName, price);
            } else if ("Clothing".equals(category)) {
                // If the category is Clothing, create an instance of Clothing
                selectedProduct = new Clothing(productId, productName, price);
            } else {
                // Handle other cases or throw an exception
                selectedProduct = null;
            }

            if (selectedProduct != null) {
                // Add the selected product to the shopping cart
                ShoppingCart.getShoppingCart().add(selectedProduct);

                // Print details to the console
                System.out.println("Product added to shopping cart:");
                System.out.println("Product ID: " + selectedProduct.getProductId());
                System.out.println("Product Name: " + selectedProduct.getProductName());
                System.out.println("Price: $" + selectedProduct.getPrice());
            } else {
                // Handle the case where the category is unknown
                System.out.println("Unknown category: " + category);
            }
        } else {
              JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart.", "No Product Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openShoppingCartWindow() {
        // Create a new JFrame for the Shopping Cart
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(500, 500);

        // Create a DefaultTableModel for the table
        DefaultTableModel cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("Product ID");
        cartTableModel.addColumn("Product Name");
        cartTableModel.addColumn("Price");
        cartTableModel.addColumn("Quantity");

        // Create a JTable with the DefaultTableModel
        JTable cartTable = new JTable(cartTableModel);

        // Load products from the ShoppingCart class and fill the table
        ArrayList<Product> shoppingCartProducts = ShoppingCart.getShoppingCart();
        for (Product product : shoppingCartProducts) {
            // Check if the product is already in the table
            boolean productExists = false;
            int existingRow = -1;
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String tableProductId = (String) cartTableModel.getValueAt(i, 0);
                if (tableProductId.equals(product.getProductId())) {
                    // Product already exists, update the quantity
                    existingRow = i;
                    productExists = true;
                    break;
                }
            }
            if (productExists) {
                // Increment quantity if the product already exists in the table
                int currentQuantity = (int) cartTableModel.getValueAt(existingRow, 3);
                cartTableModel.setValueAt(currentQuantity + 1, existingRow, 3);
            } else {
                // If the product is not in the table, add it as a new entry
                cartTableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        product.getPrice(),
                        1 // Initial quantity is 1
                });
            }
        }

        // Create a JScrollPane for the table
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Create a panel for the upper section with the table
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Create a panel for the lower section with the total and remove button
        JPanel lowerPanel = new JPanel(new GridLayout(6, 1)); // Increase the number of rows for the new button
        lowerPanel.setVisible(true);

        // Create a Label for the Before the Discount total
        JLabel beforeDiscountLabel = new JLabel("Before the Discount total: $" + String.format("%.2f", ShoppingCart.calculateTotal(shoppingCartProducts)));
        lowerPanel.add(beforeDiscountLabel);

        // Create a Label for the Show the First Time Purchase discount
        double firstTimePurchaseDiscount = ShoppingCart.calculateFirstTimePurchaseDiscount(shoppingCartProducts);
        JLabel firstTimePurchaseDiscountLabel = new JLabel("First Time Purchase discount: -$" + String.format("%.2f", firstTimePurchaseDiscount));
        lowerPanel.add(firstTimePurchaseDiscountLabel);

        // Create a Label for the Three items in the same category discount
        double sameCategoryDiscount = ShoppingCart.calculateSameCategoryDiscount(shoppingCartProducts);
        JLabel sameCategoryDiscountLabel = new JLabel("Three items in the same category discount: -$" + String.format("%.2f", sameCategoryDiscount));
        lowerPanel.add(sameCategoryDiscountLabel);

        // Create a Label for the Final Total
        double finalTotal = ShoppingCart.calculateTotalWithDiscounts(shoppingCartProducts);
        JLabel finalTotalLabel = new JLabel("Final Total: $" + String.format("%.2f", finalTotal));
        lowerPanel.add(finalTotalLabel);

        // Create a panel for the buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));

        // Create a Remove button
        JButton removeButton = new JButton("Remove Selected Product");
        removeButton.addActionListener(e -> ShoppingCart.calculateDiscountsAndTotal(shoppingCartProducts, beforeDiscountLabel, firstTimePurchaseDiscountLabel, sameCategoryDiscountLabel, finalTotalLabel, shoppingCartFrame));
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                String removedProductId = (String) cartTableModel.getValueAt(selectedRow, 0);
                // Remove the product from the shopping cart
                ShoppingCart.getShoppingCart().removeIf(product -> product.getProductId().equals(removedProductId));
                // Update the table
                cartTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(shoppingCartFrame, "Please select a product to remove.", "No Product Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonsPanel.add(removeButton);

        // Add the buttons panel to the lower panel
        lowerPanel.add(buttonsPanel);

        // Create a JSplitPane to divide the frame into upper and lower sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, lowerPanel);
        splitPane.setResizeWeight(0.8); // Set the initial size of the upper section

        // Set lowerPanel as the lower component of JSplitPane
        splitPane.setBottomComponent(lowerPanel);

        // Add the JSplitPane to the frame
        shoppingCartFrame.add(splitPane);

        // Set the frame to be visible
        shoppingCartFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();
            frame.setVisible(true);
        });
    }
}
