package ca.uottawa.cookhelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 11/30/2016.
 */

public class Recipe implements Serializable {
    private String recipeTitle;
    private String recipeText;
    private String categoryName;
    private String typeName;
    private List<String> ingredients;
    private static final long serialVersionUID = 1L;

    public Recipe(String name,List<String> ingredients, String category, String type, String description)
    {
        this.recipeTitle = name;
        this.categoryName = category;
        this.typeName = type;
        this.recipeText = description;
        this.ingredients = new ArrayList<>(ingredients);
    }
    public Recipe(){

    }

    public void setRecipeTitle(String recipeTitle){
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeTitle() { return recipeTitle; }

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

    public String getCategoryName() {return  categoryName;}

    public void setTypeName(String typeName) {this.typeName = typeName; }

    public String getTypeName() {return  typeName;}

    public void addIngredient(String ingredient) {ingredients.add(ingredient);}

    public String getText() {return recipeText;}

    public void  setText(String text) {this.recipeText = text;}

    public List<String> getIngredients(){ return ingredients;}

    public String toString(){
        return recipeTitle;
    }

}

