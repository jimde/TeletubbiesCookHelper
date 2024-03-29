package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

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
        String[] cats = new String[]{"Pick One","Breakfast","Lunch", "Dinner", "Appetizer", "Dessert", "Drink", "Sauce"};
        ArrayAdapter<String> catAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cats);
        catSpinner.setAdapter(catAdaptor);

        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        String[] types = new String[]{"Pick One", "Canadian", "Murica", "Italian", "Cuban", "Chinese", "Japanese", "Greek", "Colombian",
                "Thai", "Mexican", "Irish", "French" };
        ArrayAdapter<String> typeAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, types);
        typeSpinner.setAdapter(typeAdaptor);

    }

    public void toResults(View view){
        recipeDB = new RecipeDataSource(this);
        recipeDB.open();

        userQuery = (EditText)findViewById(R.id.searchBox);
        recipeCategorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        recipeTypeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        Intent myIntent = new Intent(this, RecipeResults.class);
        System.out.println("gettext:"+userQuery.getText().toString()+","+recipeCategorySpinner.getSelectedItem().toString()
                +","+recipeTypeSpinner.getSelectedItem().toString());


        try{
            String query = userQuery.getText().toString();
            String type =recipeTypeSpinner.getSelectedItem().toString();
            String category = recipeCategorySpinner.getSelectedItem().toString();
            myIntent.putExtra("item_data",
                    RecipeDataSource.encodeToString(new Entry("search_result_list",
                            recipeDB.queryDB(query,type,category))));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }


        startActivity(myIntent);
    }
}
