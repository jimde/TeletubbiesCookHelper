package ca.uottawa.cookhelper;

import java.io.Serializable;

/**
 * Created by Jimmy on 11/30/2016.
 */

public class Entry<K,V> implements Serializable{

    K key;
    V value;

    K getKey(){
        return key;
    }
    V getValue(){
        return value;
    }

    public Entry(K k, V v){
        key = k;
        value = v;
    }
    public String toString(){
        return value.toString();
    }

}