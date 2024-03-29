package Models;

import java.util.List;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        } else {
            System.out.println("Insufficient quantity of the product.");
        }
    }

    public void viewProducts(List<Product> products) {
        products.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Price: %.2f, Quantity: %d", name, price, quantity);
    }
}
