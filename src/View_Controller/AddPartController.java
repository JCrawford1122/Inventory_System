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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author crawf
 */
public class AddPartController implements Initializable {
    private Inventory inventory;
    private static final String MAIN_PATH = "/View_Controller/MainScreen.fxml";
    private static final String SAVE = "Save";
    private static final String SAVE_CHANGES = "Save Changes";
    private static final String ERROR_PRICE = "Invalid Price";
    private static final String ERROR_INSTOCK= "Invalid Inventory;";
    private static final String ERROR_MIN = "Invalid min value";
    private static final String ERROR_MAX = "Invalid max value";
    private static final String ERROR_MIN_TO_LARGE = "Min must be less than max";
    private static final String ERROR_INVALID_MACHINE_ID = "Part created with an invalid Machine ID";
    private static final String COMPANY_NAME = "Company Name";
    private static final String ENTER_NAME = "Enter Name";
    private static final String MACHINE_ID = "Machine ID";
    private static final String ACTION_CANCELED = "Part creation canceled";
    private static final String FIVE = "5";
   
    @FXML
    private TextField txt_addPartId;
    @FXML
    private RadioButton rad_inHouse;
    @FXML
    private RadioButton rad_outsourced;
    @FXML
    private TextField txt_addPartName;

    @FXML
    private TextField txt_addPartInv;

    @FXML
    private TextField txt_addPartPrice;
    

    @FXML
    private TextField txt_addPartMax;

    @FXML
    private TextField txt_addPartMin;

    @FXML
    private TextField txt_addPartNameOrId;

    @FXML
    private Button btn_addPartSave;

    @FXML
    private Button btn_addPartCancel;
    
    @FXML
    private Label lbl_nameOrId;
    
    
  
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     System.out.println(inventory);
    }
    
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    
    // Sets the fields of the view to the values of an inhouse part
    public void setFieldsInhouse(Inhouse part){
        // set the shared fields
        setFields(part);
        // set the inhouse only field
        txt_addPartNameOrId.setText(Integer.toString(part.getMachineID()));  
        
    }
    
    // Set the fields to the view to the values of an outsourced part
    public void setFieldsOutSourced(Outsourced outsourced){
        
        // Set the shared fields
        setFields(outsourced);
        // select the outsourced radio button
        outsourcedSelected();
        // Set the outsourced only field
        txt_addPartNameOrId.setText(outsourced.getCompanyName());
        
        
    }
    // Sets the shared fields of inhouse and outsourced parts
    public void setFields(Part part){
        txt_addPartId.setText(Integer.toString(part.getPartID()));
        txt_addPartName.setText(part.getName());
        txt_addPartPrice.setText(Double.toString(part.getPrice()));
        txt_addPartInv.setText(Integer.toString(part.getInStock()));
        txt_addPartMin.setText(Integer.toString(part.getMin()));
        txt_addPartMax.setText(Integer.toString(part.getMax()));
        btn_addPartSave.setText(SAVE_CHANGES);
    }
    
    
    // Event hander for the save part button
    public void savePart(ActionEvent event)throws IOException{
        Part part;
        // set the main stage
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PATH));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        // Get the main controller
        MainScreenController mainScreenController = loader.getController();
        // Check for valid input
        if(validateInput()){
            // make a new part
            part = makePart();
            // Add a new part
            if(btn_addPartSave.getText().equals(SAVE)){
                inventory.addPart(part);
            // Update an existing part
            } else if(btn_addPartSave.getText().equals(SAVE_CHANGES)){
                // Set the id to the ID of the original part
                part.setPartID(Integer.parseInt(txt_addPartId.getText()));
                inventory.updatePart(part.getPartID(), part);
            }
            // Set the mainscreen inventory to reflect changes
            mainScreenController.setInventory(inventory);
            // load the main screen
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        }
    }
    
    // Make a new part
    private Part makePart(){
        Part part;
        String name;
        double price;
        int inStock;
        int min;
        int max;
        
        // Get the values from the view fields
        name = txt_addPartName.getText();
        price = Double.parseDouble(txt_addPartPrice.getText());  
        inStock = Integer.parseInt(txt_addPartInv.getText());
        min = Integer.parseInt(txt_addPartMin.getText());
        max = Integer.parseInt(txt_addPartMax.getText());
        // Make an inhouse part
        if(rad_inHouse.isSelected()){
            part = makeInhousePart(name, price, inStock, min, max);
        }
        // make an outsourced part
        else{
            part = makeOutsourced(name, price, inStock, min, max);
        }
        
        return part;
        
    }
    // Create an inhouse part and set the fields
    private Part makeInhousePart(String name, double price, int inStock, int min,
                                 int max){
    Inhouse part = new Inhouse();
    part.setPartID();
    int machineId = 0;
    part.setName(name);
    part.setPrice(price);
    part.setInStock(inStock);
    part.setMin(min);
    part.setMax(max);
    
    // get the machine id from the textfield
    try {
        machineId = Integer.parseInt(txt_addPartNameOrId.getText());
    } catch(NumberFormatException e) {
        // alert the user of bad input
        Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INVALID_MACHINE_ID);
        alert.showAndWait();
    }
    part.setMachineID(machineId);
    return part;
    }
    
    // Creates a new outsourced part and sets the fields
    private Part makeOutsourced(String name, double price, int inStock, int min,
                                int max){
    Outsourced part = new Outsourced();
    
    String companyName = txt_addPartNameOrId.getText();
    part.setCompanyName(companyName);
    part.setName(name);
    part.setPrice(price);
    part.setInStock(inStock);
    part.setMin(min);
    part.setMax(max);
    part.setPartID();
    return part;
    }
    
    // Selects the outsourced radio button
    public void outsourcedSelected(){
        rad_outsourced.setSelected(true);
        rad_inHouse.setSelected(false);
        lbl_nameOrId.setText(COMPANY_NAME);
        txt_addPartNameOrId.setText(ENTER_NAME);
        
        
    }
    // Selects the inhouse radio button
    public void inHouseSelected(){
        rad_outsourced.setSelected(false);
        lbl_nameOrId.setText(MACHINE_ID);
        txt_addPartNameOrId.setText(FIVE);
        
    }
    
    // validates the input fields
    // Satisfies J1 exception handling
    public boolean validateInput(){
        boolean isValid = true;
        String name = txt_addPartName.getText();
        double price;
        int inStock;
        int min = 0;
        int max = 0;
        // names must have at least one character
        if(name.length() == 0){
            isValid = false;
        }
        // price must be a number
        try{
        price = Double.parseDouble(txt_addPartPrice.getText());
        }catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_PRICE);
            alert.showAndWait();
            isValid = false;
        }
        //Inventory must be a number
        try{
        inStock = Integer.parseInt(txt_addPartInv.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_INSTOCK);
            alert.showAndWait();
            isValid = false;
        }
        // minimum must be a number
        try{
        min = Integer.parseInt(txt_addPartMin.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_MIN);
            alert.showAndWait();
            isValid = false; 
        }
        // max must be a number
        try {
        max = Integer.parseInt(txt_addPartMax.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_MAX);
            alert.showAndWait();
            isValid = false;
        }
        //min must be less than or equal to max
        if(min > max){
             Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_MIN_TO_LARGE);
            alert.showAndWait();
            isValid = false;
        }
        return isValid;
    }
    
    // Event handler for the cancel button
    public void btn_cancel(ActionEvent event)throws IOException{
        // Setup the main screen
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PATH));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        // Get the main controller
        MainScreenController mainScreenController = loader.getController();
        // set the main inventory
        mainScreenController.setInventory(inventory);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, ACTION_CANCELED);
        alert.showAndWait();
        // load the main screen
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }
}
