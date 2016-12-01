package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Search extends AppCompatActivity {
    private RecipeDataSource recipeDB;
    private EditText userQuery;
    private Spinner recipeTypeSpinner;
    private Spinner recipeCategorySpinner;

    public void onCreate(Bundle savedInstanceState){
        System.out.println(">>>>>>>>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recipeDB = new RecipeDataSource(this);

        userQuery = (EditText)findViewById(R.id.searchBox);

        Spinner catSpinner = (Spinner) findViewById(R.id.categorySpinner);
        String[] cats = new String[]{"Pick One", "Canadian", "Italian", "Cuban"};
        ArrayAdapter<String> catAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cats);
        catSpinner.setAdapter(catAdaptor);

        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        String[] types = new String[]{"Pick One", "Breakfast", "Lunch", "Dinner"};
        ArrayAdapter<String> typeAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, types);
        typeSpinner.setAdapter(typeAdaptor);

    }

    public void toResults(View view){


        Intent myIntent = new Intent(this, Results.class);
        try {
            myIntent.putExtra("RECIPE_SEARCH_RESULTS", recipeDB.queryDB(userQuery.getText().toString()));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);
    }
}
