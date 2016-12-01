package ca.uottawa.cookhelper;

import java.io.Serializable;

/**
 * Created by Jimmy on 11/30/2016.
 */

public class Ingredient implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    public Ingredient(String name){
        this.name = name;
    }
    public String getName(){return name;}

    public String toString(){
        return name;
    }
}

