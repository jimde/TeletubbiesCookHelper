package ca.uottawa.cookhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ca.uottawa.cookhelper.IngredientSQLiteHelper.TABLE_INGREDIENTS;

/**
 * Created by Jimmy on 11/30/2016.
 */

public class IngredientDataSource {
    private SQLiteDatabase database;
    private IngredientSQLiteHelper dbHelper;
    private String[] allColumns = { IngredientSQLiteHelper.COLUMN_ID,
            IngredientSQLiteHelper.COLUMN_INGREDIENT};

    public IngredientDataSource(Context context){
        dbHelper = new IngredientSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Entry addToDB(Ingredient ingredient) throws IOException {
        ContentValues values = new ContentValues();
        values.put(IngredientSQLiteHelper.COLUMN_INGREDIENT, encodeToString(ingredient));
        long insertID = database.insert(TABLE_INGREDIENTS, null, values);
        Entry entry = new Entry(insertID, ingredient);
        return entry;
    }
    public void deleteEntry(Entry entry){
        long id = (long)entry.getKey();
        database.delete(TABLE_INGREDIENTS, IngredientSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Entry> getAllEntries() throws IOException, ClassNotFoundException{
        List<Entry> entries = new ArrayList<Entry>();
        Cursor cursor = database.query(TABLE_INGREDIENTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long id = cursor.getLong(0);
            Entry entry = new Entry(id,(Ingredient)decodeFromString(cursor.getString(1)));
            entries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return entries;
    }

    public List<Entry> queryDB(String query) throws IOException, ClassNotFoundException{
        List<Entry> results = new ArrayList<Entry>();
        List<Entry> allEntries = new ArrayList<Entry>();
        Cursor cursor = database.query(TABLE_INGREDIENTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long id = cursor.getLong(0);
            Entry entry = new Entry(id,decodeFromString(cursor.getString(1)));
            allEntries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();






        return results;
    }


    public boolean ingredientExists(String s) throws IOException, ClassNotFoundException{
        List<Entry> ingredients = this.getAllEntries();
        for(int i = 0; i < ingredients.size(); i++){
            if(ingredients.get(i).getValue().equals(s)){
                return true;
            }
        }
        return false;
    }






    public boolean ingredientInRecipe(String query, List<String> ingredients){
        System.out.println(">>>>>>>>>>>>>>>>>>>> start of ingredientInRecipe");
        boolean inList = false;
        for(int i = 0; i < ingredients.size(); i++){
            if(ingredients.get(i).equals(query)){
                return true;
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>> end of ingredientInRecipe");
        return inList;
    }

    public static String encodeToString(Serializable o) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeToString(baos.toByteArray(),0);
    }
    public static Object decodeFromString(String s) throws IOException, ClassNotFoundException{
        byte [] data = Base64.decode(s,0);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }




}

