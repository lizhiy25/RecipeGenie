import java.util.List;

public class Repository {

    public interface RecipeRepository {
        boolean isEmpty();
        List<Recipe> getRecipes();
        void addRecipe(Recipe recipe);
        void removeRecipe(String name);
        void fetchAndAddRecipe(String s);
    }
}
