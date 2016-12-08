package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ca.uottawa.cookhelper.R.id.spinner4;
import static ca.uottawa.cookhelper.R.id.spinner5;

public class EditRecipe extends AppCompatActivity {
    private EditText recipeTitle;
    private EditText recipeInstructions;
    private EditText recipeIngredients;
    private Spinner recipeType;
    private Spinner recipeCategory;
    private RecipeDataSource recipeDB;
    private static Entry entry;
    private static Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);


        String s = getIntent().getStringExtra("item_data");
        //Entry entry;
        recipe = new Recipe();

        //System.out.println(s);

        try{
            entry = (Entry)RecipeDataSource.decodeFromString(s);
            recipe = (Recipe)entry.getValue();
        }
        catch(Exception e){
            System.out.println(">>> EditRecipe");
            System.out.println( e.getClass().getCanonicalName());
        }





        recipeTitle = (EditText)findViewById(R.id.editTitle);
        recipeInstructions = (EditText)findViewById(R.id.editInstructions);
        recipeIngredients = (EditText)findViewById(R.id.editIngredients);
        recipeType = (Spinner)findViewById(spinner4);
        recipeCategory = (Spinner) findViewById(spinner5);

        String[] types = new String[]{"Pick One", "Canadian", "Murica", "Italian", "Cuban", "Chinese", "Japanese", "Greek", "Colombian",
                "Thai", "Mexican", "Irish", "French" };
        String[] cats = new String[]{"Pick One","Breakfast","Lunch", "Dinner", "Appetizer", "Dessert", "Drink", "Sauce"};

        ArrayAdapter<String> catAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cats);
        ArrayAdapter<String> typeAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, types);

        recipeCategory.setAdapter(catAdaptor);
        recipeType.setAdapter(typeAdaptor);

        recipeTitle.setText(recipe.getRecipeTitle());
        recipeInstructions.setText(recipe.getText());
        System.out.println("recipe text:" + recipe.getText());
        recipeIngredients.setText(cleanString(recipe.getIngredients()));




    }
    private String cleanString(List list){
        String newString = "";
        for(int i = 0; i < list.size(); i++){
            newString += list.get(i) + " ";
        }
        return newString;
    }
    public void clickedConfirmChanges(View view){
        recipeDB = new RecipeDataSource(this);
        recipeDB.open();


        Recipe newRecipe;
        Entry newEntry;

        String name = recipeTitle.getText().toString();
        String description = recipeInstructions.getText().toString();
        String ingredients = recipeIngredients.getText().toString();
        //String type = recipeType.getSelectedItem().toString();
        //String category = recipeCategory.getSelectedItem().toString();
        String type = recipe.getTypeName();
        String category = recipe.getCategoryName();
        newRecipe = new Recipe(name, CreateRecipe.stringToList(ingredients), category, type, description);



        newEntry = new Entry((long)entry.getKey(),newRecipe);


        recipeDB.editEntry(newEntry);

        finish();

    }
}
