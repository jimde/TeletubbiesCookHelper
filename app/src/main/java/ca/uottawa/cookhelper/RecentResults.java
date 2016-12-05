package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecentResults extends ListActivity {
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


        adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);



    }
}
