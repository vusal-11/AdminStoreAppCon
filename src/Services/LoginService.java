package Services;

import Models.Admin;
import Models.User;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private Map<String, String> adminCredentials;
    private Map<String, String> userCredentials;

    public LoginService() {
        adminCredentials = new HashMap<>();
        userCredentials = new HashMap<>();
    }

    public void registerAdmin(String username, String password) {
        adminCredentials.put(username, password);
        System.out.println("Administrator " + username + " registered.");
    }

    public void registerUser(String username, String password) {
        userCredentials.put(username, password);
        System.out.println("User " + username + " registered.");
    }

    public Object login(String username, String password) {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
            System.out.println("Login successful (Admin).");
            return new Admin(username, password);
        } else if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            System.out.println("Login successful (User).");
            return new User(username, password, 0.0);
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }
}
