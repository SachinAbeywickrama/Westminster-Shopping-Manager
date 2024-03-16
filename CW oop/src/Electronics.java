public class Electronics extends Product {
    private String brand;
    private String warrantyPeriod;

    public Electronics(String productId, String productName, int availableItems, double price, String brand, String warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics(String productId, String productName, double price) {
        super(productId, productName, price);
    }

    public String getBrand() {
        return brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getProductType() {
        return "Electronics";
    }

    @Override
    public String toString() {
        return super.toString() + ',' + brand + ',' + warrantyPeriod;
    }
}
