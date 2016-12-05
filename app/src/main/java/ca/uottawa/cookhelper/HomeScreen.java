package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
}
