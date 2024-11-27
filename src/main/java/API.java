import java.io.IOException;
import java.net.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutritionAPI {
    public static void main(String[] args) {
        // API endpoint and query
        String query = "1lb brisket and fries";
        String apiUrl = "https://api.api-ninjas.com/v1/nutrition?query=" + query;

        String apiKey = "RTsk4zAYtxwguq9NUOkpAQ==CIDpDJQmd3F2AJWc\n";

        try {
            // Create the URL object
            URL url = new URL(apiUrl);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", apiKey);

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and print the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}