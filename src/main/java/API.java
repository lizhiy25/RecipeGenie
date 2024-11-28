import java.io.IOException;
import java.net.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NutritionAPI {

    public static String fetchNutritionData(String query) throws Exception {
        String apiUrl = "https://api.api-ninjas.com/v1/nutrition?query=" + query;
        String apiKey = "RTsk4zAYtxwguq9NUOkpAQ==CIDpDJQmd3F2AJWc";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new Exception("Error: " + responseCode);
        }
    }
}