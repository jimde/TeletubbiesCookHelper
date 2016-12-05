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

/*
import static ca.uottawa.cookhelper.R.id.ingredientEntryBox;
import static ca.uottawa.cookhelper.R.id.recipeInstrBox;
import static ca.uottawa.cookhelper.R.id.recipeTitle;
import static ca.uottawa.cookhelper.R.id.spinner4;
import static ca.uottawa.cookhelper.R.id.spinner5;
*/

public class RecipeList extends ListActivity implements AdapterView.OnItemClickListener{

    private TextView userRecipeName;
    private EditText userRecipeDescription;
    private EditText userRecipeIngredients;
    private Spinner userRecipeType;
    private Spinner userRecipeCategory;
    private RecipeDataSource recipeDB;
    private RecentDataSource recentDB;


    public void onCreate(Bundle savedInstanceState){
        System.out.println(">>>>>>>>>> allrecipescreen oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //this.recipeDB = HomeScreen.recipeDB;
        recipeDB = new RecipeDataSource(this);
        recipeDB.open();
        recentDB = new RecentDataSource(this);
        recentDB.open();

        List<Entry> values = new ArrayList<Entry>();
        //userQuery = (EditText)findViewById(searchBox);

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
        /*
        userRecipeName = (TextView) findViewById(recipeTitle);
        userRecipeDescription = (EditText)findViewById(recipeInstrBox);
        userRecipeIngredients = (EditText)findViewById(ingredientEntryBox);
        userRecipeType = (Spinner) findViewById(spinner4);
        userRecipeCategory = (Spinner) findViewById(spinner5);

        String name = userRecipeName.getText().toString();
        String description = userRecipeDescription.getText().toString();
        String ingredients = userRecipeIngredients.getText().toString();
        String type = userRecipeType.getSelectedItem().toString();
        String category = userRecipeCategory.getSelectedItem().toString();

        recipeDB.addToDB(new Recipe(name,stringToList(ingredients),category,type,description));
        */
        Intent myIntent = new Intent(this, CreateRecipe.class);
        startActivity(myIntent);
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

            System.out.println("recentDB.addToQueue(recipe);");
            recentDB.addToQueue(recipe);
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        startActivity(myIntent);


    }

    @Override
    protected void onResume() {
        System.out.println(">>>>>>>>>> allrecipe onresume");
        recipeDB.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println(">>>>>>>>>> allrecipe onresume");
        recipeDB.close();
        super.onPause();
    }


}
