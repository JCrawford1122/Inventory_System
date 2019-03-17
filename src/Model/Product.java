/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Justin Crawford
 * 3/10/2019
 */
public class Product {
    private static int id = 0;
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getProductID() {
        return productID;
    }
    
    public void setProductID(int id) {
        this.productID = id;
    }
    
    public void setProductID(){
        this.productID = ++id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
   
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int id){
        this.associatedParts.removeIf(part -> part.getPartID() == id);
        return true;
    }
    
    public Part lookupAssociatedPart(int id){
        ArrayList<Part> parts;
        parts = this.getAssociatedParts();
        for (Part part : parts) {
            if(part.getPartID() == id){
                return part;
            }
        }
        return parts.get(0);
    }
    
}


