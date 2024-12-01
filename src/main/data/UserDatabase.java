package main.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String FILE_PATH = "users.txt";
    private static String currentUsername = null;

    public static boolean registerUser(String username, String password) {
        // Check if the username exists
        Map<String, String> users = loadUsers();

        // If the username exists, return False
        if (users.containsKey(username)) {
            return false;
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean authenticateUser(String username, String password) {
        Map<String, String> users = loadUsers();
        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUsername = username;
            return true;
        }
        return false;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getPassword(String username) {
        Map<String, String> users = loadUsers();
        return users.getOrDefault(username, null);
    }

    private static boolean saveUsers(Map<String, String> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updatePassword(String username, String newPassword) {
        Map<String, String> users = loadUsers();

        if (users.containsKey(username)) {
            users.put(username, newPassword);
            return saveUsers(users);
        }
        return false;
    }

    private static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        users.put(parts[0], parts[1]); // Format: "Username:Password"
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}

