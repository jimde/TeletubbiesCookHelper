package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ca.uottawa.cookhelper.R.id.ingredientEntryBox;
import static ca.uottawa.cookhelper.R.id.recipeInstrBox;
import static ca.uottawa.cookhelper.R.id.recipeTitleBox;
import static ca.uottawa.cookhelper.R.id.spinner4;
import static ca.uottawa.cookhelper.R.id.spinner5;

public class CreateRecipe extends AppCompatActivity{
    private TextView userRecipeName;
    private EditText userRecipeDescription;
    private EditText userRecipeIngredients;
    private Spinner userRecipeType;
    private Spinner userRecipeCategory;
    private RecipeDataSource recipeDB;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        recipeDB = new RecipeDataSource(this);
        recipeDB.open();

        userRecipeName = (TextView) findViewById(recipeTitleBox);
        userRecipeDescription = (EditText)findViewById(recipeInstrBox);
        userRecipeIngredients = (EditText)findViewById(ingredientEntryBox);

        userRecipeType = (Spinner)findViewById(spinner4);
        userRecipeCategory = (Spinner) findViewById(spinner5);

        String[] cats = new String[]{"Pick One", "Canadian", "Italian", "Cuban"};
        String[] types = new String[]{"Pick One","Breakfast","Lunch","Dinner"};

        ArrayAdapter<String> typeAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, types);
        ArrayAdapter<String> catAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cats);

        userRecipeCategory.setAdapter(catAdaptor);
        userRecipeType.setAdapter(typeAdaptor);

        userRecipeCategory = (Spinner)findViewById(spinner5);
    }


    private List<String> stringToList(String s){
        String[] split = s.split(" ");
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < split.length; i++){
            list.add(split[i]);
        }
        return list;
    }
    public void clickedConfirmBtn(View view){
        String name = userRecipeName.getText().toString();
        String description = userRecipeDescription.getText().toString();
        String ingredients = userRecipeIngredients.getText().toString();
        String type = userRecipeType.getSelectedItem().toString();
        String category = userRecipeCategory.getSelectedItem().toString();
        List<String> inlist = new ArrayList<String>();
        inlist.add("A");
        inlist.add("B");
        inlist.add("C");
        inlist.add("D");
        inlist.add("E");
        try {
            //recipeDB.addToDB(new Recipe(name, stringToList(ingredients), category, type, description));
            recipeDB.addToDB(new Recipe("recipe name", inlist, "category name", "type name", "this is a description"));
        }
        catch(Exception e){System.out.println( e.getClass().getCanonicalName());}
        System.out.println("end of click confirm");
        Intent myIntent = new Intent(this, RecipeList.class);
        startActivity(myIntent);
        finish();
    }
}
