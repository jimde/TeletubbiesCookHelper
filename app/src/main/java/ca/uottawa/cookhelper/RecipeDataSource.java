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
import java.util.Arrays;
import java.util.List;

import static ca.uottawa.cookhelper.RecipeSQLiteHelper.TABLE_RECIPES;

/**
 * Created by Jimmy on 11/30/2016.
 */

public class RecipeDataSource {


    private SQLiteDatabase database;
    private RecipeSQLiteHelper dbHelper;
    private String[] allColumns = { RecipeSQLiteHelper.COLUMN_ID,
            RecipeSQLiteHelper.COLUMN_RECIPE };

    public RecipeDataSource(Context context) {
        dbHelper = new RecipeSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Entry addToDB(Recipe recipe) throws IOException {
        Entry entry = recipeExists(recipe);
        if(entry == null) {
            ContentValues values = new ContentValues();
            values.put(RecipeSQLiteHelper.COLUMN_RECIPE, encodeToString(recipe));
            long insertID = database.insert(TABLE_RECIPES, null, values);
            entry = new Entry(insertID, recipe);
        }
        return entry;
    }
    private Entry recipeExists(Recipe recipe){
        try{
            List<Entry> allEntries = this.getAllEntries();
            for(int i = 0; i < allEntries.size(); i++){
                Recipe r = (Recipe)allEntries.get(i).getValue();
                if(r.getRecipeTitle().equals(recipe.getRecipeTitle())){
                    return allEntries.get(i);
                }
            }
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        return null;
    }
    public void deleteEntry(Entry entry){
        long id = (long)entry.getKey();
        database.delete(TABLE_RECIPES, RecipeSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Entry> getAllEntries() throws IOException, ClassNotFoundException{
        List<Entry> entries = new ArrayList<Entry>();
        Cursor cursor = database.query(TABLE_RECIPES, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long id = cursor.getLong(0);
            Entry entry = new Entry(id,decodeFromString(cursor.getString(1)));
            entries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return entries;
    }
    public Entry editEntry(Entry entry){
        ContentValues values = new ContentValues();
        try {
            values.put(RecipeSQLiteHelper.COLUMN_RECIPE, encodeToString((Recipe) entry.getValue()));
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        long insertID = (long)entry.getKey();

        database.update(TABLE_RECIPES, values, "_id="+insertID,null);

        return entry;
    }
    public Entry getEntry(long id){
        Cursor cursor = database.query(TABLE_RECIPES, allColumns, "_id="+id, null, null, null, null);
        cursor.moveToFirst();
        Entry entry = new Entry(null,null);
        Recipe recipe = new Recipe();
        try {
            recipe = (Recipe) decodeFromString(cursor.getString(1));
        }
        catch(Exception e){
            System.out.println("getEntry");
            System.out.println( e.getClass().getCanonicalName());
        }
        cursor.close();
        entry = new Entry(id,recipe);
        return entry;
    }
    public List<Recipe> getAllRecipes() throws IOException, ClassNotFoundException{
        List<Entry> entries = this.getAllEntries();
        List<Recipe> recipes = new ArrayList<Recipe>();
        for(int i = 0; i < entries.size();i++){
            recipes.add((Recipe)entries.get(i).getValue());
        }
        return recipes;

    }

    public List<Entry> queryDB(String query) throws IOException, ClassNotFoundException{
        List<Entry> results = new ArrayList<Entry>();
        List<Entry> allEntries = new ArrayList<Entry>();
        Cursor cursor = database.query(TABLE_RECIPES, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long id = cursor.getLong(0);
            Entry entry = new Entry(id,(Recipe)decodeFromString(cursor.getString(1)));
            allEntries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();

        for(int i = 0; i < allEntries.size(); i++){
            Recipe r = (Recipe)allEntries.get(i).getValue();
            System.out.println("checking recipe: " +r.getRecipeTitle());
            System.out.println("check recipe result:"+checkRecipeSearch((Recipe)allEntries.get(i).getValue(),query));
            if(checkRecipeSearch((Recipe)allEntries.get(i).getValue(),query)){
                results.add(allEntries.get(i));
            }
        }




        return results;
    }

    public List<Entry> queryDB(String query, String queryType, String queryCategory) {
        List<Entry> tempResults = new ArrayList<Entry>();
        List<Entry> results = new ArrayList<Entry>();
        List<Entry> allEntries = new ArrayList<Entry>();

        //System.out.println(">>> queryDB(...)");

        try {
            allEntries = this.getAllEntries();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }

        //System.out.println(query.trim().length() > 0);
        if(query.trim().length() > 0) {
            for (int i = 0; i < allEntries.size(); i++) {
                Recipe r = (Recipe) allEntries.get(i).getValue();
                //System.out.println("checking recipe: " + r.getRecipeTitle());
                //System.out.println("check recipe result:" + checkRecipeSearch((Recipe) allEntries.get(i).getValue(), query));
                if (checkRecipeSearch((Recipe) allEntries.get(i).getValue(), query)) {
                    tempResults.add(allEntries.get(i));
                }
            }
        }
        else{
            tempResults = allEntries;
        }

        for(int i = 0; i < tempResults.size(); i++){
            Recipe recipe = (Recipe)tempResults.get(i).getValue();
            if(checkTypeAndCategory(recipe,queryType,queryCategory)){
                results.add(tempResults.get(i));
            }
        }

        //System.out.println("query result size:" + results.size());

        return results;
    }

    public boolean checkTypeAndCategory(Recipe recipe, String type, String category){
        //System.out.println(">>> checkTypeAndCategory");
        //System.out.println("recipe:"+recipe.getRecipeTitle());
        //System.out.println("recipe type:"+recipe.getTypeName());
        //System.out.println("recipe category:"+recipe.getCategoryName());
        //System.out.println("query type:"+type);
        //System.out.println("query category:"+category);
        if(type.equals("Pick One") && category.equals("Pick One")){
            return true;
        }
        else if(type.equals("Pick One")){
            return recipe.getCategoryName().equals(category);
        }
        else if(category.equals("Pick One")){
            return recipe.getTypeName().equals(type);
        }
        else{
            return recipe.getTypeName().equals(type) && recipe.getCategoryName().equals(category);
        }
    }


    private boolean checkRecipeSearch(Recipe recipe, String search){ //check if recipe matches search criteria
        String newSearch = convertExpression(search,recipe);
        //System.out.println(">>>>>>>> newsearch:" + newSearch);
        return evaluateExpression(newSearch);

        /*
        System.out.println(">>>>>>>>>>>>>>>>>>>> end of checkRecipeSearch");
        System.out.println("newSearch:" + newSearch);
        return (newSearch.equals("T "));
        */
    }
    private boolean queryInStatement(String query, String statement){
        String[] split = statement.split(" ");
        for(int i = 0; i < split.length; i++){
            if(split[i].toUpperCase().equals(query.toUpperCase())){
                return true;
            }
        }
        return false;
    }


    private boolean ingredientInRecipe(String query, List<String> ingredients){
        query = query.replaceAll("\\s+","");
        for(int i = 0; i < ingredients.size(); i++){
            boolean test = ingredients.get(i).toUpperCase().contains(query.toUpperCase());
            if(ingredients.get(i).toUpperCase().contains(query.toUpperCase())){
                return true;
            }
        }
        return false;
    }
    private String convertExpression(String search, Recipe recipe){
        String newSearch = "";
        String[] split = search.split(" ");
        for(int i = 0; i < split.length; i++){
            if(!split[i].toUpperCase().equals("AND") && !split[i].toUpperCase().equals("OR") && !split[i].toUpperCase().equals("NOT")){
                if(ingredientInRecipe(split[i],recipe.getIngredients())){
                    newSearch += "T ";
                }
                else{
                    newSearch += "F ";
                }
            }
            else{
                newSearch += split[i] + " " ;
            }
        }
        return newSearch;
    }
    private boolean evaluateExpression(String expression){
        String[] split = expression.split(" ");
        String newSearch = "";
        //System.out.println("split.length: " + split.length);
        for(int i = 0; i < split.length; i++){
            //System.out.println("NOT: " + Arrays.toString(split));
            boolean found = false;
            newSearch = "";
            for(int j = 0; j < split.length; j++){
                //System.out.println(i + ", " + j);
                if(split[j].toUpperCase().equals("NOT") && !found){
                    try{
                        if(split[j+1].toUpperCase().equals("T")){
                            newSearch += "F ";
                        }
                        else{
                            newSearch += "T ";
                        }
                        j++;
                    }
                    catch(Exception e){
                        System.out.println( e.getClass().getCanonicalName());
                    }
                    found = true;
                }
                else{
                    newSearch += split[j] + " ";
                }
            }
            split = newSearch.split(" ");



        }
        //System.out.println("split.length: " + split.length);
        for(int i = 0; i < split.length; i++){
            //System.out.println("AND: " + Arrays.toString(split));
            boolean found = false;
            newSearch = "";
            for(int j = 0; j < split.length; j++){
                //System.out.println(i + ", " + j);
                if(split[j].toUpperCase().equals("AND") && !found){
                    try{
                        if(split[j-1].toUpperCase().equals("T") && split[j+1].toUpperCase().equals("T") && !found){
                            newSearch += "T ";
                        }
                        else{
                            newSearch += "F ";
                        }
                        j++;
                        found = true;

                        StringBuilder sb = new StringBuilder(newSearch);
                        sb.deleteCharAt(newSearch.length()-3);
                        sb.deleteCharAt(newSearch.length()-4);
                        newSearch = sb.toString();

                    }
                    catch(Exception e){
                        System.out.println( e.getClass().getCanonicalName());
                    }
                }
                else{
                    newSearch += split[j] + " ";

                }
                //System.out.println("newSearch:" + newSearch);

            }
            split = newSearch.split(" ");




        }

        //System.out.println("split.length: " + split.length);
        for(int i = 0; i < split.length; i++){
            //System.out.println("OR: " + Arrays.toString(split));
            boolean found = false;
            newSearch = "";
            for(int j = 0; j < split.length; j++){
                //System.out.println(i + ", " + j);
                if(split[j].toUpperCase().equals("OR") && !found){
                    try{
                        if((split[j-1].toUpperCase().equals("T") || split[j+1].toUpperCase().equals("T")) && !found){
                            newSearch += "T ";
                        }
                        else{
                            newSearch += "F ";
                        }
                        j++;
                        found = true;

                        StringBuilder sb = new StringBuilder(newSearch);
                        sb.deleteCharAt(newSearch.length()-3);
                        sb.deleteCharAt(newSearch.length()-4);
                        newSearch = sb.toString();

                    }
                    catch(Exception e){
                        System.out.println( e.getClass().getCanonicalName());
                    }
                }
                else{
                    newSearch += split[j] + " ";

                }
                //System.out.println("newSearch:" + newSearch);

            }
            split = newSearch.split(" ");




        }




        //System.out.println(Arrays.toString(split));
        if(newSearch.equals("T ")){
            return true;
        }
        else{
            return false;
        }


    }


    public void clearDB(){
        List<Entry> allEntries;
        try {
            allEntries = this.getAllEntries();
            for(int i = 0; i < allEntries.size(); i++){
                this.deleteEntry(allEntries.get(i));
            }
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
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
        Object o = new Object();
        try {
            o = ois.readObject();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
        ois.close();
        return o;
    }




}