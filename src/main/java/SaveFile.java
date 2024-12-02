import java.util.ArrayList;
import java.util.List;


public class SaveFile implements Repository.RecipeRepository {
    private static SaveFile instance;
    private final List<Recipe> savedRecipes;

    private SaveFile() {
        savedRecipes = new ArrayList<>();
    }

    public static Repository.RecipeRepository getInstance() {
        if (instance == null) {
            instance = new SaveFile();
        }
        return instance;
    }

    @Override
    public boolean isEmpty() {
        return savedRecipes.isEmpty();
    }

    @Override
    public List<Recipe> getRecipes() {
        return new ArrayList<>(savedRecipes);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        boolean exists = savedRecipes.stream()
                .anyMatch(savedRecipe -> savedRecipe.getName().equals(recipe.getName()));

        if (!exists) {
            savedRecipes.add(recipe);
        }
    }

    @Override
    public void removeRecipe(String name) {
        savedRecipes.removeIf(recipe -> recipe.getName().equals(name));
    }

    public void fetchAndAddRecipe(String query) {
        try {
            String jsonResponse = NutritionAPI.fetchNutritionData(query);
            String formattedResponse = NutritionAPI.formatNutritionData(jsonResponse);
            addRecipe(new Recipe(query, formattedResponse));
        } catch (Exception e) {
            System.out.println("Failed to fetch data for query: " + query);
            e.printStackTrace();
        }
    }
}