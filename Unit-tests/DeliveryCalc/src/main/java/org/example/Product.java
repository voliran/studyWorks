package org.example;

public class Product {
    private double price;
    private String title;
    private boolean isFragile;
    private String dimensions;

    public Product(int price, String title, boolean isFragile, String dimensions) {
        this.price = price;
        this.title = title;
        this.isFragile = isFragile;
        this.dimensions = dimensions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}
