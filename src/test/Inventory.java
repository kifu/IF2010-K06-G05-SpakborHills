package test;

import java.util.ArrayList;

/*
 * masih dasar dan masih belum ada error handling
 * item abstract class jadi ada warning dari method2 inventory
 */

public class Inventory{
    private ArrayList<Item> items;

    /**
     * Konstruktor inventory
     * @param inventory
     */
    public Inventory(ArrayList<Item> inventory){
        this.items = inventory;
    }
    /**
     * Menambahkan item ke inventory
     * @param inventory
     * @param item
     */
     public void addItem(ArrayList<Item> inventory, Item item){
        inventory.add(item);
     }
    
     /**
      * menghapus item dari list
      * @param inventory
      * @param item
      */
     public void removeItem(ArrayList<Item> inventory, Item item){
        inventory.remove(item);
     }
}