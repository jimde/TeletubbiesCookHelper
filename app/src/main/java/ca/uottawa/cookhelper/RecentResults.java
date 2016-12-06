package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecentResults extends ListActivity implements AdapterView.OnItemClickListener{
    private RecentDataSource recentDB;
    private ArrayAdapter<Entry> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_results);

        recentDB = new RecentDataSource(this);
        recentDB.open();

        List<Entry> values = new ArrayList<Entry>();

        try {
            values = recentDB.getAllEntries();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }

        ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(this);

        adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);



    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("clicked list item");

        Intent myIntent = new Intent(RecentResults.this, RecipePage.class);

        try {
            Entry entry = (Entry)parent.getItemAtPosition(position);
            Recipe recipe = (Recipe)entry.getValue();
            System.out.println(recipe.getRecipeTitle());
            //System.out.println(RecipeDataSource.encodeToString(entry));
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));


        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);
    }
    public void clearRecent(View view){
        recentDB = new RecentDataSource(this);
        recentDB.open();
        recentDB.clearDB();
    }
}
