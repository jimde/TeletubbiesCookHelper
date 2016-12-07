package ca.uottawa.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }



    public void openAboutPage(View view){
        Intent myIntent = new Intent(this, AboutUs.class);
        startActivity(myIntent);
    }
    public void resetApp(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Reset?")
                .setMessage("Are you sure you want to reset?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RecipeDataSource recipeDB = new RecipeDataSource(Settings.this);
                        IngredientDataSource ingredientDB = new IngredientDataSource(Settings.this);
                        RecentDataSource recentDB = new RecentDataSource(Settings.this);
                        recipeDB.open();
                        ingredientDB.open();
                        recentDB.open();
                        recipeDB.clearDB();
                        ingredientDB.clearDB();
                        recentDB.clearDB();
                        System.out.println("cleared databases");
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void setNumberRecent(View view){
        EditText numStored = (EditText)findViewById(R.id.setResultsPerPage);
        RecentDataSource.numberStored = Integer.parseInt(numStored.getText().toString());
    }
}
