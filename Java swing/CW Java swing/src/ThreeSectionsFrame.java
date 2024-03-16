import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThreeSectionsFrame extends JFrame {

    public ThreeSectionsFrame() {
        setTitle("Three Sections Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);

        // Create panels for top, bottom, and center sections
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // Set layouts for panels
        topPanel.setLayout(new BorderLayout()); // Use BorderLayout for top panel
        bottomPanel.setLayout(new FlowLayout());
        centerPanel.setLayout(new BorderLayout());

        // Set preferred size for the top panel
        topPanel.setPreferredSize(new Dimension(600, 100));

        // Add content to top, bottom, and center panels

        // Left section in top panel
        JPanel leftTopPanel = new JPanel();
        leftTopPanel.add(new JLabel("Select Products Categories"));
        topPanel.add(leftTopPanel, BorderLayout.WEST);

        // Center section in top panel
        JPanel centerTopPanel = new JPanel();
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        centerTopPanel.add(categoryComboBox);
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            System.out.println("Selected category: " + selectedCategory);
            // Perform actions based on the selected category
        });
        topPanel.add(centerTopPanel, BorderLayout.CENTER);

        // Right section in top panel
        JPanel rightTopPanel = new JPanel();
        JButton shoppingCartButton = new JButton("Shopping Cart");
        rightTopPanel.add(shoppingCartButton);
        shoppingCartButton.addActionListener(e -> System.out.println("Shopping Cart button clicked."));
        topPanel.add(rightTopPanel, BorderLayout.EAST);

        // Create table with columns and data
        String[] columnNames = {"ProductID", "Name", "Category", "Price", "Info"};
        Object[][] data = {
                {"1", "Product1", "electronics", 10.0, "Info1"},
                {"2", "Product2", "clothing", 20.0, "Info2"},
                {"3", "Product3", "electronics", 30.0, "Info3"}
        };

        JTable table = new JTable(new DefaultTableModel(data, columnNames));

        // Divide the center panel into left, center, and right sections
        JPanel leftPanel = new JPanel();
        JPanel centerTablePanel = new JPanel();
        JPanel rightPanel = new JPanel();

        centerTablePanel.add(new JScrollPane(table));

        // Set layouts for the divided sections
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(centerTablePanel, BorderLayout.CENTER);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThreeSectionsFrame frame = new ThreeSectionsFrame();
            frame.setVisible(true);
        });
    }
}
