public interface ShoppingManager {

    void addProduct();

    void deleteProduct(String productId);

    void printList(String category);

    void saveToFile();

    void readFromFile();
}
