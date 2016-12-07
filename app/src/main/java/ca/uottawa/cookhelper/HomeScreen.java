package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    public static IngredientDataSource ingredientDB;
    public static RecipeDataSource recipeDB;
    private RecentDataSource recentDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        setContentView(R.layout.activity_home_screen);

        ingredientDB = new IngredientDataSource(this);
        recipeDB = new RecipeDataSource(this);
        recentDB = new RecentDataSource(this);
        ingredientDB.open();
        recipeDB.open();
        recentDB.open();
        try {
            addDefaultFoods();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
    }
    private void addDefaultFoods() throws IOException{
        ingredientDB = new IngredientDataSource(this);
        recipeDB = new RecipeDataSource(this);
        recentDB = new RecentDataSource(this);
        ingredientDB.open();
        recipeDB.open();
        recentDB.open();

        List<String> pancakeIngredients = new ArrayList<>();
        pancakeIngredients.add("Flour");
        pancakeIngredients.add("Baking Powder");
        pancakeIngredients.add("Salt");
        pancakeIngredients.add("White Sugar");
        pancakeIngredients.add("Milk");
        pancakeIngredients.add("Egg");
        pancakeIngredients.add("Melted Butter");
        Recipe pancakes = new Recipe("Pancakes",pancakeIngredients,"Breakfast","Canadian",
                "1. In a large bowl, sift together the flour, " +
                "baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; " +
                "mix until smooth. \n2. Heat a lightly oiled griddle or frying pan over medium high heat. " +
                "Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. " +
                "Brown on both sides and serve hot." );
        recipeDB.addToDB(pancakes);



    }
    public void toSearchScreenPage(View view){
        Intent myIntent = new Intent(this, Search.class);
        startActivity(myIntent);
    }
    public void toRecentResults(View view){
        Intent myIntent = new Intent(this, RecentResults.class);
        startActivity(myIntent);
    }
    public void toSettingsScreen(View view){
        Intent myIntent = new Intent(this, Settings.class);
        startActivity(myIntent);
    }
    public void toIngredientsScreen(View view){
        Intent myIntent = new Intent(this, Pantry.class);
        startActivity(myIntent);
    }
    public void toAllRecipes(View view){
        Intent myIntent = new Intent(this, RecipeList.class);
        startActivity(myIntent);
    }
    public void toEgg(View view){
        Intent myIntent = new Intent(this, Egg.class);
        startActivity(myIntent);
    }
}
