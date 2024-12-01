public class Recipe {
    private final String name;
    private final String nutritionalInfo;

    public Recipe(String name, String nutritionalInfo) {
        this.name = name;
        this.nutritionalInfo = nutritionalInfo;
    }

    public String getName() {
        return name;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }
}
