/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justincrawford_inventorysystem.Justincrawford_inventorysystem;

/**
 * FXML Controller class
 * 
 * @author Justin Crawford
 * 3/10/2019
 */


public class MainScreenController implements Initializable {
    
    
    private static final String ADD_PART_PATH = "/View_Controller/AddPart.fxml";
    private static final String ADD_PRODUCT_PATH = "/View_Controller/AddProduct.fxml";
    public static final String PART_TBL_COL_ONE = "partID";
    public static final String PART_TBL_COL_TWO = "name";
    public static final String PART_TBL_COL_THREE = "price";
    public static final String PART_TBL_COL_FOUR = "inStock";
    private static final String PROD_TBL_COL_ONE = "productID";
    private static final String PROD_TBL_COL_TWO = "name";
    private static final String PROD_TBL_COL_THREE = "price";
    private static final String PROD_TBL_COL_FOUR = "inStock";
    private static final String MSG_PART_REMOVED = "The part was removed";
    private static final String MSG_PROD_REMOVED = "The product was removed";
    private static final String MSG_NULL_LIST = "Parts list was null";
    private Inventory inv = new Inventory();
     
    @FXML
    private TextField txt_SearchParts;

    @FXML
    private TableView<Part> tbl_PartsTable;
    
    @FXML
    private TableColumn<Part, Integer> col_partId;

    @FXML
    private TableColumn<?, ?> col_partName;

    @FXML
    private TableColumn<?, ?> col_partInventory;

    @FXML
    private TableColumn<?, ?> col_partPrice;

    @FXML
    private TextField txt_SearchProducts;

    @FXML
    private TableView<Product> tbl_ProductsTable;
  
    @FXML
    private TableColumn<?, ?> col_productId;

    @FXML
    private TableColumn<?, ?> col_productName;

    @FXML
    private TableColumn<?, ?> col_productInvLevel;

    @FXML
    private TableColumn<?, ?> col_productPrice;
    
    @FXML
    private Button btn_exit;
    

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the lists
        if(inv.getAllParts() == null){
            inv.setAllParts(new ArrayList<>());
            inv.setProducts(new ArrayList<>());
            // Setup the parts table columns
            col_partId.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_ONE));
            col_partName.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_TWO));
            col_partPrice.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_THREE));
            col_partInventory.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_FOUR));
            
            // Give the table the allParts list
            try{
                tbl_PartsTable.setItems(FXCollections.observableList(inv.getAllParts()));
            // If the list is null it will throw an exception    
            }
            catch(NullPointerException e){
                Alert alert = new Alert(AlertType.ERROR, MSG_NULL_LIST);
                alert.showAndWait();
            }
            // Set up the product table columns
            col_productId.setCellValueFactory(new PropertyValueFactory<>(PROD_TBL_COL_ONE));
            col_productName.setCellValueFactory(new PropertyValueFactory<>(PROD_TBL_COL_TWO));
            col_productInvLevel.setCellValueFactory(new PropertyValueFactory<>(PROD_TBL_COL_THREE));
            col_productPrice.setCellValueFactory(new PropertyValueFactory<>(PROD_TBL_COL_FOUR));
            // Give the products table the products list
            try{
                tbl_ProductsTable.setItems(FXCollections.observableList(inv.getProducts()));
            }catch(NullPointerException e){
                //do nothing. The table is blank
            }           
        } 
    } 
    /* event handler for the delete part button
     * Deletes a part from inventory if the part is found.
     * If no part is selected do nothing
    */    
    public void btn_deletePart(ActionEvent e)throws IOException{
        int id = -1;
        id = tbl_PartsTable.getSelectionModel().getSelectedItem().getPartID();
        if(id != -1){
            inv.deletePart(id);
            tbl_PartsTable.setItems(FXCollections.observableList(inv.getAllParts()));
            Alert alert = new Alert(AlertType.CONFIRMATION, MSG_PART_REMOVED);
            alert.showAndWait();      
        }
    }
    
    /* Event handler for the delete product button.
     * Deletes a product from the inventory.
     * If no product is selected do nothing
    */
    public void btn_deleteProduct(ActionEvent e)throws IOException {
        int id;
        id = tbl_ProductsTable.getSelectionModel().getSelectedItem().getProductID();
        if(id != -1){
            inv.removeProduct(id);
            tbl_ProductsTable.setItems(FXCollections.observableList(inv.getProducts()));
            Alert alert = new Alert(AlertType.CONFIRMATION, MSG_PROD_REMOVED);
            alert.showAndWait();
        }
    }
    
    // Event handler for add part button
    public void btn_addPart(ActionEvent event) throws IOException{
        // Setup the add part screen
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ADD_PART_PATH));
        Parent root = loader.load();
        Scene addPartScene = new Scene(root);
        // Get the controller
        AddPartController addPartController = loader.getController();
        // Pass the inventory
        addPartController.setInventory(inv);
        //load the screen
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.show();
    }
   
    // Event handler for search part button
   public void btn_search(ActionEvent event)throws IOException {
       // The part id to search
       int findId;
       // get the id from the text field
       findId = Integer.parseInt(txt_SearchParts.getText());
       // search the list for part id
       for(Part p: inv.getAllParts()){
           if(p.getPartID() == findId){
               // select the part
               tbl_PartsTable.getSelectionModel().select(p);
               break;
           }
       }
   }
   
   // Event handler for product search button
   public void btn_searchProduct(ActionEvent event)throws IOException {
       // The product id to search for
       int findId;
       // get the id from the text box
       findId = Integer.parseInt(txt_SearchProducts.getText());
       // search the list for matching id
       for(Product p: inv.getProducts()){
           if(p.getProductID() == findId){
               // select the product
               tbl_ProductsTable.getSelectionModel().select(p);
           }
       }
   }
   
   // Event handler for modify part button
   public void btn_modifyProduct(ActionEvent event)throws IOException {
        Product product;
        // Prepare the add product screen
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ADD_PRODUCT_PATH));
        Parent root = loader.load();
        Scene addProductScene = new Scene(root);
        // If a product in the table is selected
        if(!(tbl_ProductsTable.getSelectionModel().getSelectedItem() == null)){
            // set the product to the selected table item
            product = tbl_ProductsTable.getSelectionModel().getSelectedItem();
            // Get the controller
            AddProductController addProductController = loader.getController();
            // Pass the inventory
            addProductController.setInventory(inv);
            // Pass the associated parts
            addProductController.setTempList(product.getAssociatedParts());
            // Pass the product
            addProductController.setFields(product);
            // load the screen
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(addProductScene);
            stage.show();
       }
   }
   
   // Event handler for the modify part button
   public void btn_modifyPart(ActionEvent event) throws IOException {
        Part part;
        // Setup the add part screen
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ADD_PART_PATH));
        Parent root = loader.load();
        Scene addPartScene = new Scene(root);
        // Get the part to modify
        part = tbl_PartsTable.getSelectionModel().getSelectedItem();
        // get the controller
        AddPartController addPartController = loader.getController();
        // set the inventory
        addPartController.setInventory(inv);
       
        // check if the part is inhouse or outsourced
        if(part.getClass() == Model.Inhouse.class){
           addPartController.setFieldsInhouse((Inhouse)part);
        }
        else if(part.getClass() == Model.Outsourced.class){ 
          addPartController.setFieldsOutSourced((Outsourced)part); 
        }
        // load the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.show();
       
    }
    // Set the inventory and reload the tables
    public void setInventory(Inventory inventory){
        this.inv = inventory;
        tbl_PartsTable.setItems(FXCollections.observableList(inv.getAllParts()));
        tbl_ProductsTable.setItems(FXCollections.observableList(inv.getProducts()));
    }
    
    // Event handler for the add product button
    public void btn_addProduct(ActionEvent event)throws IOException{
        // Prepare the add part scene
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ADD_PRODUCT_PATH));
        Parent root = loader.load();
        Scene addProductScene = new Scene(root);
        // Get the controler
        AddProductController addProductController = loader.getController();
        // Set the inventory
        addProductController.setInventory(inv);
        // load the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addProductScene);
        stage.show();
    }
    // Fixed the exit button
    public void btn_exit(ActionEvent event)throws IOException{
        Stage stage;
        stage = (Stage)btn_exit.getScene().getWindow();
        stage.close();
    }
}
