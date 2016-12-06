package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;

public class RecipePage extends ListActivity {
    private TextView recipeTitle;
    private TextView recipeDescription;
    private TextView recipeCategory;
    private TextView recipeType;
    private static Entry entry;
    private RecipeDataSource recipeDB;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("RecipePage oncreate");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        recipeDB = new RecipeDataSource(this);
        recipeDB.open();


        String s = getIntent().getStringExtra("item_data");
        //Entry entry;
        Recipe recipe = new Recipe();

        //System.out.println(s);

        try{
            entry = (Entry)RecipeDataSource.decodeFromString(s);
            recipe = (Recipe)entry.getValue();
        }
        catch(Exception e){
            System.out.println(">>> RecipePage");
            System.out.println( e.getClass().getCanonicalName());
        }


        recipeTitle = (TextView)findViewById(R.id.recipePageTitle);
        recipeDescription = (TextView)findViewById(R.id.recipePageDescription);
        recipeType = (TextView)findViewById(R.id.recipePageType);
        recipeCategory = (TextView)findViewById(R.id.recipePageCategory);

        recipeTitle.setText(recipe.getRecipeTitle());
        recipeDescription.setText(recipe.getText());
        recipeType.setText(recipe.getTypeName());
        recipeCategory.setText(recipe.getCategoryName());




        System.out.println(Arrays.toString(recipe.getIngredients().toArray()));

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, recipe.getIngredients());
        setListAdapter(adapter);


    }

    public void clickedEdit(View view){
        Intent myIntent = new Intent(this, EditRecipe.class);



        try {
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }





        startActivity(myIntent);
    }
    public void clickedDelete(View view){
        System.out.println("clicked delete recipe");

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recipeDB = new RecipeDataSource(RecipePage.this);
                        recipeDB.open();
                        recipeDB.deleteEntry(entry);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();





        //finish();
    }

    @Override
    public void onResume(){
        Recipe recipe = (Recipe) recipeDB.getEntry((long) entry.getKey()).getValue();

        recipeTitle = (TextView) findViewById(R.id.recipePageTitle);
        recipeDescription = (TextView) findViewById(R.id.recipePageDescription);
        recipeType = (TextView) findViewById(R.id.recipePageType);
        recipeCategory = (TextView) findViewById(R.id.recipePageCategory);

        recipeTitle.setText(recipe.getRecipeTitle());
        recipeDescription.setText(recipe.getText());
        recipeType.setText(recipe.getTypeName());
        recipeCategory.setText(recipe.getCategoryName());

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipe.getIngredients());
        setListAdapter(adapter);


        super.onResume();
    }


}
