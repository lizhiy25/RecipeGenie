//package main.java;
import java.io.IOException;
import java.net.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.management.Query;


class NutritionAPI {

    public static String fetchNutritionData(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String apiUrl = "https://api.api-ninjas.com/v1/nutrition?query=" + encodedQuery;
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

class RecipeAPI{

    public static String fetchRecipeData(String query) throws Exception {
        String apiUrl = "https://api.api-ninjas.com/v1/recipe?query=" + query;
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

    public static String formatRecipeData(String jsonResponse) {
        StringBuilder formattedResponse = new StringBuilder();
        try {
            if (jsonResponse.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonResponse);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    formattedResponse.append("title: ").append(item.getString("title")).append("\n")
                            .append("ingredients: ").append(item.getString("ingredients")).append("\n")
                            .append("servings: ").append(item.getString("servings")).append("\n")
                            .append("instructions: ").append(item.getString("instructions")).append("\n")
                            .append("--------------------------------------\n");
                }
            } else if (jsonResponse.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                formattedResponse.append("Error or single recipe received:\n")
                        .append(jsonObject.toString(4)); // Pretty-print the JSON object
            } else {
                formattedResponse.append("Unexpected response format:\n").append(jsonResponse);
            }
        } catch (JSONException e) {
            formattedResponse.append("Error parsing response: ").append(e.getMessage());
        }
        return formattedResponse.toString();
    }


}