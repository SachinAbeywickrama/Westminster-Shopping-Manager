public abstract class  Product {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;
    private String productType;
    private boolean firstTimePurchase;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public Product(String productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public boolean isFirstTimePurchase() {
        return firstTimePurchase;
    }

    public void setFirstTimePurchase(boolean firstTimePurchase) {
        this.firstTimePurchase = firstTimePurchase;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public double getPrice() {
        return price;
    }

    public String getProductType(){
        return productType;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductType(String productType){
        this.productType = productType;
    }


    @Override
    public String toString() {
        return getProductType() + ',' + productId + ',' + productName + ',' + availableItems  + ',' + price;
    }
}
