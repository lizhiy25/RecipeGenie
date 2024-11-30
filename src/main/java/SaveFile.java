import java.util.ArrayList;
import java.util.List;


public class SaveFile {
    private static SaveFile instance;
    private List<String[]> savedRecipes; // Changed to store both name and nutritional info

    private SaveFile() {
        savedRecipes = new ArrayList<>();
    }

    public static SaveFile getInstance() {
        if (instance == null) {
            instance = new SaveFile();
        }
        return instance;
    }

    public boolean isEmpty() {
        return savedRecipes.isEmpty();
    }

    // Get the list of saved recipes (name and nutritional info)
    public List<String[]> getSavedRecipes() {
        return savedRecipes;
    }

    // Add recipe name and nutritional info to the list
    public void addRecipe(String name, String nutritionalInfo) {
        savedRecipes.add(new String[] { name, nutritionalInfo });
    }

    // Remove recipe by name
    public void removeRecipe(String name) {
        savedRecipes.removeIf(recipe -> recipe[0].equals(name));
    }

    // Fetch and add a recipe's nutritional data
    public void fetchAndAddRecipe(String query) {
        try {
            String jsonResponse = NutritionAPI.fetchNutritionData(query);
            String formattedResponse = NutritionAPI.formatNutritionData(jsonResponse);
            addRecipe(query, formattedResponse); // Save the recipe name and its nutritional data
        } catch (Exception e) {
            System.out.println("Failed to fetch data for query: " + query);
            e.printStackTrace();
        }
    }
}