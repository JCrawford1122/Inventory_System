/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Justin Crawford
 * 3/10/2019
 */
abstract public class Part {
    // static variable to increment part id
    private static int id = 0;
    private int partId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public int getPartID() {
        return partId;
    }

    public void setPartID(int partId) {
        this.partId = partId;
        
    }
    
    public void setPartID(){
        this.partId = ++id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
