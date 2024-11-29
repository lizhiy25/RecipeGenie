package main.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String FILE_PATH = "users.txt";

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
        return users.containsKey(username) && users.get(username).equals(password);
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

