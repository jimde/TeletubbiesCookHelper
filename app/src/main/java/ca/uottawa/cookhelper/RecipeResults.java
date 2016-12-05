package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class RecipeResults extends ListActivity implements AdapterView.OnItemClickListener {
    private RecentDataSource recentDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_results);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("clicked list item");

        recentDB = new RecentDataSource(this);
        recentDB.open();

        Intent myIntent = new Intent(RecipeResults.this, RecipePage.class);

        try {
            Entry entry = (Entry)parent.getItemAtPosition(position);
            Recipe recipe = (Recipe)entry.getValue();
            System.out.println(recipe.getRecipeTitle());
            //System.out.println(RecipeDataSource.encodeToString(entry));
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));

            System.out.println("recentDB.addToQueue(recipe);");
            recentDB.addToQueue(recipe);
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);

    }
}
