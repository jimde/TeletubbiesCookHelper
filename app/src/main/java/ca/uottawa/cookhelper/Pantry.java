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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ca.uottawa.cookhelper.R.id.newIngredientTextInput;

public class Pantry extends ListActivity implements AdapterView.OnItemClickListener{

    private IngredientDataSource ingredientDB;
    private EditText userIngredientInput;
    private ArrayAdapter<Entry> adapter;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        ingredientDB = new IngredientDataSource(this);
        ingredientDB.open();

        List<Entry> values = new ArrayList<Entry>();

        try {
            values = ingredientDB.getAllEntries();
        }
        catch(IOException i){}
        catch(ClassNotFoundException c){}


        ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(this);

        adapter = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        userIngredientInput = (EditText)findViewById(newIngredientTextInput);
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("clicked pantry item");

        Intent myIntent = new Intent(Pantry.this, IngredientPage.class);

        try {
            Entry entry = (Entry)parent.getItemAtPosition(position);
            Ingredient ingredient = (Ingredient) entry.getValue();
            System.out.println(ingredient.getName());
            myIntent.putExtra("item_data", RecipeDataSource.encodeToString(entry));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);
    }


    public void clickedAddIngredientBtn(View view) throws IOException, ClassNotFoundException{

        Entry e = ingredientDB.addToDB(new Ingredient(userIngredientInput.getText().toString()));
        adapter.add(e);
        adapter.notifyDataSetChanged();
        /*
        List<Entry> ent = ingredientDB.getAllEntries();
        List<Ingredient> dump = new ArrayList<Ingredient>();
        for(int i = 0; i < ent.size(); i++){
            dump.add((Ingredient)ent.get(i).getValue());
        }
        System.out.println(dump.toArray().toString());
        */
    }


    @Override
    protected void onResume() {
        ingredientDB.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        ingredientDB.close();
        super.onPause();
    }


}
