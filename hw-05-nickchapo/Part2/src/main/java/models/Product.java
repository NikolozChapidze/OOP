package models;

public class Product {
    private String productid;
    private String name;
    private String imageFile;
    private double price;

    public Product(String productid, String name, String imageFile, double price) {
        this.productid = productid;
        this.name = name;
        this.imageFile = imageFile;
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
