package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ca.uottawa.cookhelper.R.id.searchBox;

public class Results extends ListActivity implements AdapterView.OnItemClickListener{

    private RecipeDataSource recipeDB;

    private EditText userQuery;
    private EditText userRecipeName;
    private EditText userRecipeDescription;
    private EditText userRecipeIngredients;
    private Spinner userRecipeType;
    private Spinner userRecipeCategory;



    public void onCreate(Bundle savedInstanceState){
        System.out.println(">>>>>>>>>> results oncreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Entry entry;
        List<Entry> values = new ArrayList<Entry>();
        String s = getIntent().getStringExtra("item_id");
        System.out.println("s:"+s);
        try {
            entry = (Entry) RecipeDataSource.decodeFromString(s);
            values = (ArrayList<Entry>)entry.getValue();
        }
        catch(Exception e){
            System.out.println(">>> reading from intent");
            System.out.println( e.getClass().getCanonicalName());
        }




        ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


        //if(values!=null) {
        //ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, values);
        //setListAdapter(adapter);
        //}else{
        //List<Entry> abc = new ArrayList<Entry>();
        //abc.add(new Entry<Boolean,String>(true,"No Recipies Found"));
        //ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1,abc);
        //}

        /*
        userRecipeName = (EditText)findViewById(userRecipeNameTextInput);
        userRecipeDescription = (EditText)findViewById(userRecipeDescriptionTextInput);
        userRecipeIngredients = (EditText)findViewById(userIngredientsInput);
        userRecipeType = (Spinner) findViewById(userRecipeTypeInput);
        userRecipeCategory = (Spinner) findViewById(userRecipeCategoryInput);
        */
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("clicked list item");

        Intent myIntent = new Intent(Results.this, RecipePage.class);

        try {
            Entry entry = (Entry)parent.getItemAtPosition(position);
            Recipe recipe = (Recipe)entry.getValue();
            System.out.println(recipe.getRecipeTitle());
            System.out.println(RecipeDataSource.encodeToString(entry));
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);
    }
    /*
    public void clickedAddRecipeBtn(View view){
        String name = userRecipeName.getText().toString();
        String description = userRecipeDescription.getText().toString();
        String ingredients = userRecipeIngredients.getText().toString();
        String type = userRecipeType.getSelectedItem().toString();
        String category = userRecipeCategory.getSelectedItem().toString();

        Entry
    }
    */
}