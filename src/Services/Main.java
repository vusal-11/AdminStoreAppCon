import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Models.Admin;
import Models.Product;
import Models.Store;
import Models.User;
import Services.LoginService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();
        Object loggedInUser = null;
        List<Store> stores = List.of(new Store("Main Store"));

        while (true) {
            System.out.println("1. Register Admin");
            System.out.println("2. Register User");
            System.out.println("3. Log In");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerAdmin(scanner, loginService);
                    break;

                case 2:
                    registerUser(scanner, loginService);
                    break;

                case 3:
                    loggedInUser = login(scanner, loginService);

                    if (loggedInUser instanceof Admin) {
                        performAdminOperations((Admin) loggedInUser, stores, scanner);
                    } else if (loggedInUser instanceof User) {
                        performUserOperations((User) loggedInUser, stores, scanner);
                    }
                    break;

                case 4:
                    System.out.println("Exiting the application.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (loggedInUser instanceof Admin) {
                performAdminOperations((Admin) loggedInUser, stores, scanner);
            } else if (loggedInUser instanceof User) {
                performUserOperations((User) loggedInUser, stores, scanner);
            }
        }
    }

    private static void registerAdmin(Scanner scanner, LoginService loginService) {
        System.out.print("Enter username for admin: ");
        String adminUsername = scanner.nextLine();

        System.out.print("Enter password for admin: ");
        String adminPassword = scanner.nextLine();

        loginService.registerAdmin(adminUsername, adminPassword);
    }

    private static void registerUser(Scanner scanner, LoginService loginService) {
        System.out.print("Enter username for user: ");
        String userUsername = scanner.nextLine();

        System.out.print("Enter password for user: ");
        String userPassword = scanner.nextLine();

        loginService.registerUser(userUsername, userPassword);
    }

    private static Object login(Scanner scanner, LoginService loginService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return loginService.login(username, password);
    }

    private static void performAdminOperations(Admin admin, List<Store> stores, Scanner scanner) {
        while (true) {
            System.out.println("\nAdministrator, choose an action:");
            System.out.println("1. Show products in the store");
            System.out.println("2. Increase product quantity");
            System.out.println("3. Decrease product quantity");
            System.out.println("4. Add a new product");
            System.out.println("5. Create a new store");
            System.out.println("5. Exit");

            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    showProductsInStores(stores);
                    break;

                case 2:
                    increaseOrDecreaseProductQuantity(scanner, stores, true);
                    break;

                case 3:
                    increaseOrDecreaseProductQuantity(scanner, stores, false);
                    break;

                case 4:
                    addNewProduct(scanner, admin, stores);
                    break;

                case 5:
                    createNewStore(stores, scanner);
                    break;

                case 6:
                    System.out.println("Exiting admin account.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }



    private static void showProductsInStores(List<Store> stores) {
        stores.forEach(store -> {
            System.out.println("Products in " + store.getName() + ":");
            store.getProducts().forEach(System.out::println);
        });
    }

    private static void increaseOrDecreaseProductQuantity(Scanner scanner, List<Store> stores, boolean increase) {
        showProductsInStores(stores);

        System.out.print("Enter the name of the product: ");
        String productName = scanner.nextLine();

        System.out.print("Enter the store name: ");
        String storeName = scanner.nextLine();

        stores.stream()
                .filter(store -> store.getName().equals(storeName))
                .findFirst()
                .ifPresentOrElse(
                        store -> {
                            Product selectedProduct = store.getProducts().stream()
                                    .filter(product -> product.getName().equals(productName))
                                    .findFirst()
                                    .orElse(null);

                            if (selectedProduct != null) {
                                if (increase) {
                                    increaseProductQuantity(scanner, selectedProduct);
                                } else {
                                    decreaseProductQuantity(scanner, selectedProduct);
                                }
                            } else {
                                System.out.println("Product not found in the specified store.");
                            }
                        },
                        () -> System.out.println("Store not found.")
                );

        scanner.nextLine();
    }

    private static void createNewStore(List<Store> stores, Scanner scanner) {
        System.out.print("Enter the name of the new store: ");
        String newStoreName = scanner.nextLine();

        if (newStoreName != null && !newStoreName.trim().isEmpty()) {
            Store newStore = new Store(newStoreName);
            stores.add(newStore);
            System.out.println("Store '" + newStoreName + "' created successfully.");
        } else {
            System.out.println("Invalid store name. Please provide a non-empty name.");
        }
    }

    private static void increaseProductQuantity(Scanner scanner, Product selectedProduct) {
        System.out.print("Enter the quantity to increase: ");
        int increaseAmount = scanner.nextInt();
        selectedProduct.increaseQuantity(increaseAmount);
    }

    private static void decreaseProductQuantity(Scanner scanner, Product selectedProduct) {
        if (selectedProduct != null) {
            if (selectedProduct.getQuantity() > 0) {
                System.out.print("Enter the quantity to decrease: ");
                int decreaseAmount = scanner.nextInt();

                if (decreaseAmount > 0 && selectedProduct.getQuantity() >= decreaseAmount) {
                    selectedProduct.decreaseQuantity(decreaseAmount);
                    System.out.println("Quantity decreased successfully.");
                } else {
                    System.out.println("Invalid quantity or not enough stock.");
                }
            } else {
                System.out.println("No products available for decrease.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void addNewProduct(Scanner scanner, Admin admin, List<Store> stores) {
        showStores(stores);

        System.out.print("Enter the name of the store to add a new product: ");
        String storeName = scanner.nextLine();

        stores.stream()
                .filter(store -> store.getName().equals(storeName))
                .findFirst()
                .ifPresentOrElse(
                        store -> {
                            System.out.print("Enter the name of the new product: ");
                            String newProductName = scanner.nextLine();

                            System.out.print("Enter the price of the new product: ");
                            double newProductPrice = scanner.nextDouble();

                            System.out.print("Enter the quantity of the new product: ");
                            int newProductQuantity = scanner.nextInt();
                            scanner.nextLine();

                            Product newProduct = new Product(newProductName, newProductPrice, newProductQuantity);
                            admin.addProductToStore(newProduct, store);
                        },
                        () -> System.out.println("Store not found.")
                );
    }

    private static void showStores(List<Store> stores) {
        System.out.println("Available Stores:");
        stores.forEach(store-> {
            System.out.println(store.getName());
        });
    }


    private static void performUserOperations(User user, List<Store> stores, Scanner scanner) {
        while (true) {
            System.out.println("\nUser, choose an action:");
            System.out.println("1. Show products in the store");
            System.out.println("2. Buy a product");
            System.out.println("3. Show available stores");
            System.out.println("4. Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    showProductsInStores(stores);
                    break;

                case 2:
                    buyProduct(user, stores, scanner);
                    break;

                case 3:
                    user.viewStores(stores);
                    break;

                case 4:
                    System.out.println("Exiting user account.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void buyProduct(User user, List<Store> stores, Scanner scanner) {
        showProductsInStores(stores);

        System.out.print("Enter the name of the product: ");
        String productName = scanner.nextLine();

        System.out.print("Enter the store name: ");
        String storeName = scanner.nextLine();

        stores.stream()
                .filter(store -> store.getName().equals(storeName))
                .findFirst()
                .ifPresentOrElse(
                        store -> {
                            Product selectedProduct = store.getProducts().stream()
                                    .filter(product -> product.getName().equals(productName))
                                    .findFirst()
                                    .orElse(null);

                            if (selectedProduct != null) {
                                System.out.print("Enter the quantity to purchase: ");
                                int quantity = scanner.nextInt();
                                user.buyProduct(selectedProduct, quantity);
                            } else {
                                System.out.println("Product not found in the specified store.");
                            }
                        },
                        () -> System.out.println("Store not found.")
                );
    }
}
