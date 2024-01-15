package Models;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private String name;
    private List<Product> products;

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Store(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void editProduct(Product product) {
        // Logic for editing a product in the store
        System.out.println("Product " + product.getName() + " in store " + name + " has been edited.");
    }
}
