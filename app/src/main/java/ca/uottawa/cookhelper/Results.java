package ca.uottawa.cookhelper;

import android.app.ListActivity;
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

public class Results extends ListActivity {

    private RecipeDataSource recipeDB;

    private EditText userQuery;
    private EditText userRecipeName;
    private EditText userRecipeDescription;
    private EditText userRecipeIngredients;
    private Spinner userRecipeType;
    private Spinner userRecipeCategory;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recipeDB = new RecipeDataSource(this);
        recipeDB.open();

        List<Entry> values = new ArrayList<Entry>();
        userQuery = (EditText)findViewById(searchBox);



        ArrayList<String> test = new ArrayList<String>();

        Recipe testRes = new Recipe("Name", test, "Italian", "something", "disc");

        try {
            recipeDB.addToDB(testRes);
            System.out.println("Added");
        }
        catch (IOException e){}


        try {
            //values = recipeDB.queryDB(userQuery.getText().toString());
            values = recipeDB.getAllEntries();
            System.out.print("values set");
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
            //values=null;
        }



        this.recipeDB = HomeScreen.recipeDB;
        //recipeDB = new RecipeDataSource(this);
        recipeDB.open();


        //userQuery = (EditText)findViewById(searchBox);

        try {
            values = recipeDB.getAllEntries();
        }
        catch(Exception e){
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