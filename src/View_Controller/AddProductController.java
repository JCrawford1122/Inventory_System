/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;


import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author crawf
 */
public class AddProductController implements Initializable {
    // the list that will be the associated parts
    private ArrayList<Part> templist = new ArrayList();
    private Inventory inventory;
    private static final String MAIN_SCREEN_PATH = "/View_Controller/MainScreen.fxml";
    private static final String PART_TBL_COL_ONE = "partID";
    private static final String PART_TBL_COL_TWO = "name";
    private static final String PART_TBL_COL_THREE = "price";
    private static final String PART_TBL_COL_FOUR = "inStock";
    private static final String ERROR_INVALID_PRICE = "Invalid Price";
    private static final String ERROR_INVALID_INV = "INV must be an integer, Seting value to 0...";
    private static final String ERROR_INVALID_MIN = "Min must be an integer";
    private static final String ERROR_MIN_TO_LARGE = "Min must be less than max";
    private static final String ERROR_INVALID_MAX = "Max must be an integer";
    private static final String SAVE = "Save";
    private static final String SAVE_CHANGES = "Save Changes";
    private static final String MODIFY_PRODUCT = "Modify Product";
    private static final int UNSET = 0;
    @FXML
    private TextField txt_searchParts;

    @FXML
    private TextField txt_productId;

    @FXML
    private TextField txt_productName;

    @FXML
    private TextField txt_inv;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_max;

    @FXML
    private TextField txt_min;

    @FXML
    private TableView<Part> tbl_apPartsTable;

    @FXML
    private TableColumn<?, ?> col_partId;

    @FXML
    private TableColumn<?, ?> col_partName;

    @FXML
    private TableColumn<?, ?> col_partInventory;

    @FXML
    private TableColumn<?, ?> col_partPrice;

    @FXML
    private TableView<Part> tbl_apAssociatedParts;

    @FXML
    private TableColumn<?, ?> col_associatedPartId;

    @FXML
    private TableColumn<?, ?> col_associatedPartName;

    @FXML
    private TableColumn<?, ?> col_associatedPartInventory;

    @FXML
    private TableColumn<?, ?> col_associatedPartPrice;

    @FXML
    private Button btn_saveProduct;
   
    @FXML
    private Label lbl_title;
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setup available part table columns
        col_partId.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_ONE));
        col_partName.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_TWO));
        col_partPrice.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_THREE));
        col_partInventory.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_FOUR));
        try{
            // set the table to all parts
            tbl_apPartsTable.setItems(FXCollections.observableList(inventory.getAllParts()));        
        }catch(NullPointerException e){
            // the table was null, do nothing for now.
        } 
        // setup the table columns for associated parts
        col_associatedPartId.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_ONE));
        col_associatedPartName.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_TWO));
        col_associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_THREE));
        col_associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>(PART_TBL_COL_FOUR));  
    }
    
    
    public void setTempList(ArrayList<Part> list){
        this.templist= list;
    }
    // sets the inventory and reload the parts table
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
        tbl_apPartsTable.setItems(FXCollections.observableList(inventory.getAllParts()));
        System.out.println(this.inventory);
    }
    
    // Sets the view fields with product data to update
    public void setFields(Product product){
        // Set the text fields
        txt_productId.setText(Integer.toString(product.getProductID()));
        txt_productName.setText(product.getName());
        txt_price.setText(Double.toString(product.getPrice()));
        txt_inv.setText(Integer.toString(product.getInStock()));
        txt_min.setText(Integer.toString(product.getMin()));
        txt_max.setText(Integer.toString(product.getMax()));
        // set the associated parts table 
        tbl_apAssociatedParts.setItems(FXCollections.observableList(product.getAssociatedParts()));
        setTempList(product.getAssociatedParts());
        // Rename the button so the save method knows we are updating a product
        btn_saveProduct.setText(SAVE_CHANGES);
        // Change the title to show the modify title.
        lbl_title.setText(MODIFY_PRODUCT);   
    }
    
    // Event handler for the save product button
    public void btnSave(ActionEvent event)throws IOException{
        // prepare the scene
        Product product;
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_PATH));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        // get the main controller
        MainScreenController mainScreenController = loader.getController();
        // Check the product fields are valid
        if(validateProduct()){
            // make a new product
            product = makeProduct();
            // Add a new product to the inventory
            if(btn_saveProduct.getText().equals(SAVE)){
            inventory.addProduct(product);
            // Update an existing product
            }else if(btn_saveProduct.getText().equals(SAVE_CHANGES)){
                product.setProductID(Integer.parseInt(txt_productId.getText()));
                inventory.updateProduct(product.getProductID(), product);    
            }
            // Set the inventory
            mainScreenController.setInventory(inventory);
            // load the main screen
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        }
    }
    // Makes a new product from view fields data
    public Product makeProduct(){
        Product product = new Product();
        product.setProductID();
        product.setName(txt_productName.getText());
        product.setPrice(Double.parseDouble(txt_price.getText()));
        product.setInStock(Integer.parseInt(txt_inv.getText()));
        product.setMin(Integer.parseInt(txt_min.getText()));
        product.setMax(Integer.parseInt(txt_max.getText()));
        product.setAssociatedParts(templist);
        
        return product;
        
    }
    
    // Event handler for adding parts to a product
    // Satisfies J2 for exception handling
    public void btnAddPart(ActionEvent event)throws IOException{
        Part part;
        // get the available part
        part = tbl_apPartsTable.getSelectionModel().getSelectedItem();
        // Add the part to a list
        templist.add(part);
        // Set the associated parts table
        tbl_apAssociatedParts.setItems(FXCollections.observableList(templist));
    }
    
    // Event handler for removing a part from the associated parts
    public void btnDeletePart(ActionEvent event) throws IOException{
        Part part;
        // Get the part to disassociate
        part = tbl_apAssociatedParts.getSelectionModel().getSelectedItem();
        // Remove the part from the list
        templist.remove(part);
        // Reset the associated parts table
        tbl_apAssociatedParts.setItems(FXCollections.observableList(templist));
    }
    
    // Event handler for the cancel button
    public void btnCancel(ActionEvent event)throws IOException{
        // Prepare the scene
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_PATH));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        // Get the main controller
        MainScreenController mainScreenController = loader.getController();
        // Set the inventory
        mainScreenController.setInventory(inventory);
        // Load the main scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }
    
    // Event handler for search part button
    public void btnSearchPart(ActionEvent event)throws IOException{
        // The id number to find
        int findId;
        // get the id # from the textfield
        findId = Integer.parseInt(txt_searchParts.getText());
        // Search the parts in the table for a matching id
        for(Part p: inventory.getAllParts()){
           if(p.getPartID() == findId){
               tbl_apPartsTable.getSelectionModel().select(p);
           }
       }
    }
    
    //Validates that product fields are valid
    public boolean validateProduct(){
        Boolean isValid = true;
        int min = UNSET;
        int max = UNSET;
        // Product must have a name
        if(txt_productName.getText().isEmpty()){
            isValid = false;
        }
        // Price field must contain a number
        try{
            Double.parseDouble(txt_price.getText());  
        }catch(NumberFormatException e){
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INVALID_PRICE);
            alert.showAndWait();
        }
        // inStock field must contain an integer
        try {
            Integer.parseInt(txt_inv.getText());
            
        }catch(NumberFormatException e){
            isValid = false;
            txt_inv.setText("0");
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INVALID_INV);
            alert.showAndWait();
        }
        // min field must be an integer
        try {
            min = Integer.parseInt(txt_min.getText());
            
        }catch(NumberFormatException e){
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INVALID_MIN);
            alert.showAndWait();
        }
        // max field must be an integer
        try {
            max = Integer.parseInt(txt_max.getText());
            
        }catch(NumberFormatException e){
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INVALID_MAX);
            alert.showAndWait();
        }
        // min must be < or = to max
        if(min > max){
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_MIN_TO_LARGE);
            alert.showAndWait();
        }
        return isValid;
    }
    
    
    
}
