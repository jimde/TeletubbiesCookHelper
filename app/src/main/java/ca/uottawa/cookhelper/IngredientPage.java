package ca.uottawa.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IngredientPage extends AppCompatActivity {
    private Entry entry;
    private Ingredient ingredient;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_page);

        String s = getIntent().getStringExtra("item_data");
        try{
            entry = (Entry)IngredientDataSource.decodeFromString(s);
            ingredient = (Ingredient)entry.getValue();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }

        text = (TextView)findViewById(R.id.testText);
        //text.setText("afwaegwavgawrv");



    }






}
