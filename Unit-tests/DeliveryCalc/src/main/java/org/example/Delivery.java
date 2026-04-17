package org.example;

import java.util.List;

public class Delivery {
    private double distance;
    private List<Product> products;
    private double totalDeliveryCoast;
    private double totalProductsCoast;
    private Workload workload;

    public Delivery(double distance, List<Product> products, Workload workload) {
        this.distance = distance;
        this.products = products;
        this.workload = workload;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTotalDeliveryCoast(double totalDeliveryCoast) {
        this.totalDeliveryCoast = totalDeliveryCoast;
    }

    public double getTotalProductsCoast() {
        return totalProductsCoast;
    }

    public void setTotalProductsCoast(double totalProductsCoast) {
        this.totalProductsCoast = totalProductsCoast;
    }

    public double calculateProductsTotal() {
        return totalProductsCoast = products.stream().mapToDouble(Product::getPrice).sum();
    }

    public double deliveryCoast() throws FragileDistanceExceededException {
        double coast = 0;

        for (Product product: products) {
            if (product.isFragile() && distance > 30) throw new FragileDistanceExceededException();

            if (product.getDimensions().equals("large")) {
                coast += 200;
            } else {
                coast += 100;
            }

            if (product.isFragile()) coast += 300;
        }

        if (distance > 30) coast += 300;
        if (distance <= 30 && distance >= 10) coast += 200;
        if (distance < 10 && distance >= 2) coast += 100;
        if (distance < 2) coast += 50;

        coast *= workload.getCoefficient();

        if (coast < 400) {
            System.out.println("Доставка от 400 рублей, добавьте ещё товаров в корзину");
            return 0;
        }

        return totalDeliveryCoast = coast;

    }
}
