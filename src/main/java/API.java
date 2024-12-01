//package main.java;
import java.io.IOException;
import java.net.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


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

    public static String formatNutritionData(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        StringBuilder formattedResponse = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            formattedResponse.append("Name: ").append(item.getString("name")).append("\n")
                    .append("Sodium: ").append(item.getDouble("sodium_mg")).append("mg\n")
                    .append("Sugar: ").append(item.getDouble("sugar_g")).append("g\n")
                    .append("Cholesterol: ").append(item.getDouble("cholesterol_mg")).append("mg\n")
                    .append("Potassium: ").append(item.getDouble("potassium_mg")).append("mg\n")
                    .append("Fiber: ").append(item.getDouble("fiber_g")).append("g\n")
                    .append("--------------------------------------\n");
        }
        return formattedResponse.toString();
    }
}
