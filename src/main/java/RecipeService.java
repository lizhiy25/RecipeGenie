import java.util.List;

public class RecipeService {
    private final Repository.RecipeRepository repository;

    public RecipeService(Repository.RecipeRepository repository) {
        this.repository = repository;
    }

    public boolean isRepositoryEmpty() {
        return repository.isEmpty();
    }

    public List<Recipe> getAllRecipes() {
        return repository.getRecipes();
    }

    public void saveRecipe(String name, String nutritionalInfo) {
        repository.addRecipe(new Recipe(name, nutritionalInfo));
    }

    public void deleteRecipe(String name) {
        repository.removeRecipe(name);
    }

    public void fetchAndSaveRecipe(String query) {
        ((SaveFile) repository).fetchAndAddRecipe(query);
    }
}