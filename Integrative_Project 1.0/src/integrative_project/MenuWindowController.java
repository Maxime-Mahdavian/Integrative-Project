/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author cstuser
 */
public class MenuWindowController extends AnimationWindowController implements Initializable {
    
    @FXML
    private ComboBox mechanicsBox = new ComboBox(FXCollections.observableArrayList("2D projectile", "Conservation of Energy"));
    @FXML
    private ComboBox electricityBox = new ComboBox(FXCollections.observableArrayList("Coulomb's Law", "Ohm's Law"));
    @FXML
    private ComboBox wavesBox = new ComboBox(FXCollections.observableArrayList("Thin Films", "Pendulum"));
    
    @FXML
    public void exitButtonAction(){
        System.exit(0);
    }
    @FXML
    private void FAQButtonAction(){
        setHelpMessage("help_messages/main_menu_help.txt");
        setTitleHelpWindow("Main Menu Help Window");
        handleHelpButton();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        mechanicsBox.getItems().add("2D Projectile");
        mechanicsBox.getItems().add("Conservation of Energy");
        mechanicsBox.getSelectionModel().selectedItemProperty()
            .addListener((ObservableValue observable, 
                    Object oldValue, Object newValue) -> {
                        try {
                            loadAnimation(newValue.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                
                    
        });
        
        electricityBox.getItems().add("Coulomb's Law");
        electricityBox.getItems().add("Ohm's Law");
        electricityBox.getSelectionModel().selectedItemProperty()
            .addListener((ObservableValue observable, 
                    Object oldValue, Object newValue) -> {
                        try {
                            loadAnimation(newValue.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
        });
        
        wavesBox.getItems().add("Thin Films");
        wavesBox.getItems().add("Pendulum");
        wavesBox.getSelectionModel().selectedItemProperty()
            .addListener((ObservableValue observable, 
                    Object oldValue, Object newValue) -> {       
                        try {
                            loadAnimation(newValue.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
        });
        AssetManager.preloadMainMenuAssets();
        anchorPane.setBackground(AssetManager.getDefaultBackground());
    }
    
    public void loadAnimation(String id)throws IOException{

        Scene scene = mechanicsBox.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        switch(id){
            case "2D Projectile":
                root = FXMLLoader.load(getClass().getResource("/Mechanics/ProjectileAnimationWindow.fxml"));
                break;
            case "Conservation of Energy":
                root = FXMLLoader.load(getClass().getResource("/Mechanics/EnergyConservationAnimationWindow.fxml"));
                break;
            case "Coulomb's Law":
                root = FXMLLoader.load(getClass().getResource("/ElectricityAndMagnetism/CoulombsLawAnimationWindow.fxml"));
                break;
            case "Ohm's Law":
                root = FXMLLoader.load(getClass().getResource("/ElectricityAndMagnetism/OhmsLawAnimationWindow.fxml"));
                break;
            case "Thin Films":
                root = FXMLLoader.load(getClass().getResource("/WavesAndOptics/ThinFilmsAnimationWindow.fxml"));
                break;
            case "Pendulum":
                root = FXMLLoader.load(getClass().getResource("/WavesAndOptics/PendulumAnimationWindow.fxml"));
                break;
            default:
                root = FXMLLoader.load(getClass().getResource("/integrative_project/MenuWindow.fxml"));
                break;
                
        }
        stage.setScene(new Scene(root));
            
    }
    
}
