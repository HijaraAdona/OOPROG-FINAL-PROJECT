public class Product {
    private String productNumber;
    private String productName;
    private String description;
    private int quantity;
    private double price;

    public Product(String productNumber, String productName, String description, int quantity, double price) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product Number: " + productNumber + "\n" +
                "Product Name: " + productName + "\n" +
                "Description: " + description + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price: $" + price + "\n";
    }
}
