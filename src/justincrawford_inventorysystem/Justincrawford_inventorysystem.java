/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justincrawford_inventorysystem;


import Model.Inventory;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


/**
 *
 * @author crawf
 */
public class Justincrawford_inventorysystem extends Application {

   // public static Inventory inventory;
    @Override
    public void start(Stage stage) throws Exception {
     
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);
        
        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
