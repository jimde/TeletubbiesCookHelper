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
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class RecipeList extends ListActivity implements AdapterView.OnItemClickListener{


    private RecipeDataSource recipeDB;
    private RecentDataSource recentDB;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeDB = new RecipeDataSource(this);
        recipeDB.open();
        recentDB = new RecentDataSource(this);
        recentDB.open();

        List<Entry> values = new ArrayList<Entry>();


        try {
            values = recipeDB.getAllEntries();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }


        ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(this);

        ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);



    }


    public void clickedAddRecipeBtn(View view) throws IOException {
        Intent myIntent = new Intent(this, CreateRecipe.class);
        startActivity(myIntent);
        finish();
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("clicked list item");

        Intent myIntent = new Intent(RecipeList.this, RecipePage.class);

        try {
            Entry entry = (Entry)parent.getItemAtPosition(position);
            Recipe recipe = (Recipe)entry.getValue();
            System.out.println(recipe.getRecipeTitle());
            //System.out.println(RecipeDataSource.encodeToString(entry));
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));

            recentDB.addToQueue(recipe);
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);


    }

    @Override
    protected void onResume() {
        recipeDB.open();

        List<Entry> values = new ArrayList<Entry>();

        try {
            values = recipeDB.getAllEntries();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        super.onResume();
    }

    @Override
    protected void onPause() {
        recipeDB.close();
        super.onPause();
    }


}
