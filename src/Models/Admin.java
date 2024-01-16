package Models;

import java.util.List;
import java.util.Scanner;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addProductToStore(Product product, Store store) {
        store.addProduct(product);
    }

    public String getUsername() {
        return username;
    }

    public void viewProducts(List<Product> products) {
        products.forEach(product -> System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getQuantity()));
    }

    public boolean checkCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void createNewStore(List<Store> stores, Scanner scanner) {
        System.out.print("Enter the name of the new store: ");
        String newStoreName = scanner.nextLine();

        if (newStoreName != null && !newStoreName.trim().isEmpty()) {
            stores.add(new Store(newStoreName));
            System.out.println("Store '" + newStoreName + "' created successfully.");
        } else {
            System.out.println("Invalid store name. Please provide a non-empty name.");
        }
    }
}
