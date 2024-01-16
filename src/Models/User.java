package Models;

import java.util.List;

public class User {
    private String username;
    private String password;
    private double money;

    public User(String username, String password, double money) {
        this.username = username;
        this.password = password;
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double amount) {
        money += amount;
    }

    public boolean checkCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public boolean buyProduct(Product product, int quantity) {
        double totalCost = product.getPrice() * quantity;

        if (money >= totalCost && product.getQuantity() >= quantity) {
            money -= totalCost;
            product.decreaseQuantity(quantity);
            System.out.println("User " + username + " purchased " + quantity + " units of " + product.getName());
            return true;
        } else {
            System.out.println("Insufficient funds or insufficient stock for the purchase.");
            return false;
        }
    }

    public void viewStores(List<Store> stores) {
        stores.forEach(store -> System.out.println(store.getName()));
    }

    public void viewProducts(List<Product> products) {
        products.forEach(product -> System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getQuantity()));
    }
}
