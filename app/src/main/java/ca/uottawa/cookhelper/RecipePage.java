package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;

public class RecipePage extends ListActivity {
    private TextView recipeTitle;
    private TextView recipeDescription;
    private TextView recipeCategory;
    private TextView recipeType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        String s = getIntent().getStringExtra("item_data");
        Entry entry;
        Recipe recipe = new Recipe();

        System.out.println(s);

        try{
            entry = (Entry)RecipeDataSource.decodeFromString(s);
            recipe = (Recipe)entry.getValue();
        }
        catch(Exception e){
            System.out.println(">>> RecipePage");
            System.out.println( e.getClass().getCanonicalName());
        }


        recipeTitle = (TextView)findViewById(R.id.recipePageTitle);
        recipeDescription = (TextView)findViewById(R.id.recipePageDescription);
        recipeType = (TextView)findViewById(R.id.recipePageType);
        recipeCategory = (TextView)findViewById(R.id.recipePageCategory);

        recipeTitle.setText(recipe.getRecipeTitle());
        recipeDescription.setText(recipe.getText());
        recipeType.setText(recipe.getTypeName());
        recipeCategory.setText(recipe.getCategoryName());


        List<String> test = new ArrayList<String>();
        test.add("A");
        test.add("B");
        test.add("C");
        test.add("D");
        test.add("E");

        System.out.println(Arrays.toString(recipe.getIngredients().toArray()));

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, recipe.getIngredients());
        setListAdapter(adapter);


    }
}
