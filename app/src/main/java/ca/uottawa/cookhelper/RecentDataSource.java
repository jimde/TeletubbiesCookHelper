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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static ca.uottawa.cookhelper.RecentSQLiteHelper.TABLE_RECENT_RECIPES;

/**
 * Created by Jimmy on 12/4/2016.
 */

public class RecentDataSource {
    private SQLiteDatabase database;
    private RecentSQLiteHelper dbHelper;
    private String[] allColumns = { RecentSQLiteHelper.COLUMN_ID,
            RecentSQLiteHelper.COLUMN_RECENT};
    private int numberStored = 10;

    public RecentDataSource(Context context){
        dbHelper = new RecentSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private Entry addToDB(Recipe recipe) throws IOException {
        ContentValues values = new ContentValues();
        values.put(RecentSQLiteHelper.COLUMN_RECENT, encodeToString(recipe));
        long insertID = database.insert(TABLE_RECENT_RECIPES, null, values);
        Entry entry = new Entry(insertID, recipe);
        return entry;
    }
    public void deleteEntry(Entry entry){
        long id = (long)entry.getKey();
        database.delete(TABLE_RECENT_RECIPES, IngredientSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Entry> getAllEntries() throws IOException, ClassNotFoundException{
        List<Entry> entries = new ArrayList<Entry>();
        Cursor cursor = database.query(TABLE_RECENT_RECIPES, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            long id = cursor.getLong(0);
            Entry entry = new Entry(id,(Recipe)decodeFromString(cursor.getString(1)));
            entries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return entries;
    }

    public void setQueueSize(int size){
        numberStored = size;
    }

    public Entry addToQueue(Recipe recipe){
        System.out.println(">>> added to recent queue");

        List<Entry> allEntries = new ArrayList<Entry>();
        try {
            System.out.println("allEntries = getAllEntries();");
            allEntries = getAllEntries();
            System.out.println("allEntries = getAllEntries(); -- end");
        }
        catch(Exception e){
            System.out.println("allEntries = this.getAllEntries();");
            System.out.println( e.getClass().getCanonicalName());
        }

        try {
            if (allEntries.size() >= numberStored) {
                while(allEntries.size() >= numberStored){
                    deleteEntry(allEntries.get(0));
                    allEntries.remove(0);
                }

            }
        }
        catch(Exception e){
            System.out.println("if (allEntries.size() > numberStored)");
            System.out.println( e.getClass().getCanonicalName());
        }

        Entry entry = new Entry(-1,recipe);
        try {
            System.out.println("entry = addToDB(recipe);");
            entry = addToDB(recipe);
        }
        catch(Exception e){
            System.out.println("entry = addToDB(recipe);");
            System.out.println( e.getClass().getCanonicalName());
        }

        System.out.println(">>> end of recent queue add");

        return entry;


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
        Object o = ois.readObject();
        ois.close();
        return o;
    }




}
