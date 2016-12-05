package ca.uottawa.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditRecipe extends AppCompatActivity {
    private EditText recipeTitle;
    private EditText recipeInstructions;
    private EditText recipeIngredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);


        String s = getIntent().getStringExtra("item_data");
        Entry entry;
        Recipe recipe = new Recipe();

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
}
