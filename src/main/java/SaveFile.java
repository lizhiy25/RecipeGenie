import java.util.ArrayList;
import java.util.List;

public class SaveFile {
    // Save file class for people that want to add their recipe to a saved file and access them later

    private List<String> recipeUrls;

    public SaveFile() {
        recipeUrls = new ArrayList<>();
    }

    public boolean isEmpty() {
        return recipeUrls.isEmpty();
    }
    // This is to list all the recipes you have
    public List<String> getRecipeUrl() {
        return recipeUrls;
    }

    // Add what recipes you want to the list
    public void addRecipeUrl(String url) {
        if (!recipeUrls.contains(url)) {
            recipeUrls.add(url);
        }
    }

    // Remove unwanted recipes from your list
    public void removeRecipeUrl(String url) {
        recipeUrls.remove(url);
    }

    public void fetchAndAddRecipe(String query) {
        try {
            // Call the static method from the NutritionAPI class
            String response = NutritionAPI.fetchNutritionData(query);

            // Simulate generating a recipe URL from the query
            String recipeUrl = "https://example.com/recipe?query=" + query.replace(" ", "+");

            // Add the URL to the list
            addRecipeUrl(recipeUrl);

            // Print the fetched data
            System.out.println("Fetched data: " + response);
        } catch (Exception e) {
            System.out.println("Failed to fetch data for query: " + query);
            e.printStackTrace();
        }
    }
}


