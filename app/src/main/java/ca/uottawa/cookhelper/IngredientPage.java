package ca.uottawa.cookhelper;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngredientPage extends AppCompatActivity {
    private Entry entry;
    private Ingredient ingredient;

    private TextView text;
    private TextView text2;
    private LinearLayout lview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_page);

        String s = getIntent().getStringExtra("item_data");
        System.out.println("s:"+s);
        try{
            entry = (Entry)IngredientDataSource.decodeFromString(s);
            ingredient = (Ingredient)entry.getValue();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }

        text = (TextView)findViewById(R.id.ingredientTitle);
        text.setText("afwaegwavgawrv");

        text = new TextView(this);
        text.setText(ingredient.getName());
        text2 = new TextView(this);
        text2.setText("asdfasger");
        lview = (LinearLayout)findViewById(R.id.ingredientLayout);
        lview.addView(text);
        lview.addView(text2);


    }






}
