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
public class Inventory {
    
    private ArrayList<Product> products;
    private ArrayList<Part> allParts;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Part> getAllParts() {
        return allParts;
    }

    public void setAllParts(ArrayList<Part> allParts) {
        this.allParts = allParts;
    }
    
    // Add a product to the inventory
    public void addProduct(Product product){
        this.products.add(product);
    }
    
    // Remove a product from the inventory
    public boolean removeProduct(int id){
        this.products.removeIf(product -> product.getProductID() == id);
        return true;
    }
    
    // Remove a part from the inventory
    public boolean deletePart(int id){
        this.allParts.removeIf(part -> part.getPartID() == id);  
        return true;  
    }
    
    // Required method but is unused in the program
    public Product lookUpProduct(int id){
        Product product = new Product();
        return product;
    }
   /*Required method but is not used in the program.
    *Returns a part with matching ID. If ID is not found returns the first part
    * in the list
    */
    public Part lookupPart(int id){
        ArrayList<Part> parts;
        parts = this.getAllParts();
        for (Part part : parts) {
            if(part.getPartID() == id){
                return part;
            }
        }
        return parts.get(0);
    }
    
    // Required method not used in the program. See the overload method
    public void updateProduct(int id){
     
    }
    
    /* Deletes the original product and inserts a new updated product 
     * with the same id.
     */
    public void updateProduct(int id, Product product){
        for(Product p: this.getProducts()){
            if(p.getProductID() == id){
                this.removeProduct(id);
                product.setProductID(id);
                this.addProduct(product);
                break;
            }
        }
    }
    
    // Required method not used in the program. 
    public void updatePart(int id){
       
    }
    
    /* Deletes the original part and inserts a new updated product 
     * with the same id.
    */
    public void updatePart(int id, Part part){
        for(Part p: this.getAllParts()){
            if(p.getPartID() == id){
                this.deletePart(id);
                part.setPartID(id);
                this.addPart(part);
                break;
            }
        }
    }
        
    // Add a part to the inventory
    public void addPart(Part part){
        this.allParts.add(part); 
    }

}
