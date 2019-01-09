/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WavesAndOptics;

import integrative_project.AnimationWindowController;
import integrative_project.Arrow;
import integrative_project.AssetManager;
import integrative_project.Constants;
import integrative_project.Vector2D;
import static integrative_project.Constants.ARROW_SPEED;
import static integrative_project.Constants.DEFAULT_THICKNESS;
import static integrative_project.Constants.REFLECTED_SPEED;
import static integrative_project.Constants.ZERO_VECTOR;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class ThinFilmsAnimationWindowController extends AnimationWindowController implements Initializable, Constants {
    
    private double thickness;
    private double thinFilmIndex;
    private double angle;
    private Arrow lightArrow;
    private Arrow lightArrow1;
    private Arrow lightArrow2;
    private Arrow reflectArrow;
    private Arrow reflectArrow2;
    private String thinFilmChoice;
    
    @FXML
    private TextField thicknessField;
    @FXML
    private ChoiceBox thinFilmBox;
    @FXML
    private Rectangle thinFilm;
    @FXML
    private Rectangle ground;
    @FXML
    private Label constructiveLabel;
    @FXML
    private Label destructiveLabel;
    @FXML
    AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHelpMessage("help_messages/thin_film_help.txt");
        setTitleHelpWindow("Thin Film Help Window");
        
        animationPane.setBackground(AssetManager.getDefaultBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());
        
        thinFilmBox.getItems().add("Water (n = 1.33)");
        thinFilmBox.getItems().add("Oil Slick (n = 1.22)");
        thinFilmBox.getItems().add("MgF2 (n = 1.38)");
        thinFilmBox.getItems().add("Glass (n = 1.50)");
        thinFilmBox.getSelectionModel().selectedItemProperty()
            .addListener((ObservableValue observable, 
                    Object oldValue, Object newValue) -> {
                            thinFilmChoice = newValue.toString();  
        });
        thinFilmBox.getSelectionModel().selectFirst();
    }    
    
    private void getThinFilm(String value){
        switch (value) {
            case "Water (n = 1.33)":
                thinFilmIndex = 1.33;
                thinFilm.setFill(Color.AQUA);
                break;
            case "Oil Slick (n = 1.22)":
                thinFilmIndex = 1.22;
                thinFilm.setFill(Color.MAROON);
                break;
            case "MgF2 (n = 1.38)":
                thinFilmIndex = 1.38;
                thinFilm.setFill(Color.WHITE);
                break;
            case "Glass (n = 1.50)":
                thinFilmIndex = 1.5;
                thinFilm.setFill(Color.ALICEBLUE);
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void handlePauseButton(){
        super.handlePauseButton();
        //continueButton.setDisable(false);
        lightArrow.setVelocity(ZERO_VECTOR);
        lightArrow1.setVelocity(ZERO_VECTOR);
        lightArrow2.setVelocity(ZERO_VECTOR);
        reflectArrow.setVelocity(ZERO_VECTOR);
        reflectArrow2.setVelocity(ZERO_VECTOR);
    }

    protected void handleContinueButton(){
        //continueButton.setDisable(true);
        super.handleContinueButton();
        if(animationPane.getChildren().contains(lightArrow.getArrow()))
            lightArrow.setVelocity(ARROW_SPEED);
        else if(animationPane.getChildren().contains(lightArrow1.getArrow()))
            lightArrow2.setVelocity(141.42/1.33*Math.sin(angle), 141.42/1.33*Math.cos(angle));
        else if(animationPane.getChildren().contains(reflectArrow.getArrow()))
            reflectArrow.setVelocity(141.42/1.33*Math.sin(angle), - 141.42/1.33*Math.cos(angle));
        else if(animationPane.getChildren().contains(reflectArrow2.getArrow()))
            reflectArrow2.setVelocity(REFLECTED_SPEED);
        if(animationPane.getChildren().contains(lightArrow1.getArrow()))
            lightArrow.setVelocity(ARROW_SPEED);
        
    }
    
    @Override
    protected void animationLoopActions(){
        lightArrow.update(frameDeltaTime);
        lightArrow1.update(frameDeltaTime);
        lightArrow2.update(frameDeltaTime);
        reflectArrow.update(frameDeltaTime);
        reflectArrow2.update(frameDeltaTime);
        
        if(lightArrow.getArrow().getLayoutY() >= 272 && !(lightArrow.getVelocity().getX() == 0)){
            lightArrow.setVelocity(ZERO_VECTOR);
            lightArrow1.setVelocity(REFLECTED_SPEED);
            lightArrow2.setVelocity(141.42/1.33*Math.sin(angle), 141.42/1.33*Math.cos(angle));
            animationPane.getChildren().remove(lightArrow.getArrow());
            addToPane(lightArrow1.getArrow());
            addToPane(lightArrow2.getArrow());
            
        }
        
        if(lightArrow2.getArrow().getLayoutY() >= 0.785*thinFilm.getHeight() + 297 && !(lightArrow2.getVelocity().getX() == 0) ){
            lightArrow2.setVelocity(ZERO_VECTOR);
            reflectArrow.setVelocity(141.42/1.33*Math.sin(angle), - 141.42/1.33*Math.cos(angle));
            animationPane.getChildren().remove(lightArrow2.getArrow());
            addToPane(reflectArrow.getArrow());
        }
        
        if(reflectArrow.getArrow().getLayoutY() <= 0.2185*thinFilm.getHeight() + 303 && !(reflectArrow.getVelocity().getX() == 0)){
            reflectArrow.setVelocity(ZERO_VECTOR);
            reflectArrow2.setVelocity(REFLECTED_SPEED);
            animationPane.getChildren().remove(reflectArrow.getArrow());
            addToPane(reflectArrow2.getArrow());
        }      
            
    }
    
    @Override
    protected void handleResetButton(){
        pauseButton.setDisable(true);
        continueButton.setDisable(true);
        isPause = false;
        lastFrameTime = 0.0f;
        startButton.setDisable(false);
        animationPane.getChildren().remove(lightArrow.getArrow());
        animationPane.getChildren().remove(lightArrow1.getArrow());
        animationPane.getChildren().remove(lightArrow2.getArrow());
        animationPane.getChildren().remove(reflectArrow.getArrow());
        animationPane.getChildren().remove(reflectArrow2.getArrow());
        constructiveLabel.setText("");
        destructiveLabel.setText("");
        messageLabel.setText("");
        validInput = true;
        timer.stop();
    }
    
    @Override
    protected void loadObject(){
        errorMessage = "";
        try{
            thickness = getUserInput(thicknessField);
            if(thickness > 1000)
                throw new NumberFormatException("Thickness value is too high");
            else if(thickness < 0)
                throw new NumberFormatException("Thickness value is negative");
            else if(thickness < 100)
                throw new NumberFormatException("Thickness value is too small");
        }
        catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            thickness = DEFAULT_THICKNESS;
            setMessage("Default values used where input was empty");
        }
        
        if(validInput == true){
            getThinFilm(thinFilmChoice);
            thinFilm.setHeight(thickness * 0.2);
            thinFilm.setLayoutY(animationPane.getHeight() - ground.getHeight() - thinFilm.getHeight());
            
                
            System.out.println(thinFilm.getLayoutY() + " " + ground.getLayoutY() + " " + thinFilm.getY() + " " + ground.getY() + " " + thinFilm.getHeight() + " " + ground.getHeight());
            lightArrow = new Arrow(new Vector2D(0,thinFilm.getLayoutY() - 300), ARROW_SPEED, ZERO_VECTOR, 75, 5);
            lightArrow.setRotation(225);
            
            angle = (Math.asin(Math.sin(Math.toRadians(45))/thinFilmIndex));
            
            lightArrow2 = new Arrow(new Vector2D(0, thinFilm.getLayoutY() - 300), ZERO_VECTOR, ZERO_VECTOR, thinFilm.getHeight()/2 + 5 , 5);
            lightArrow2.setRotation(225);
            lightArrow2.setPosition(1.1665*thinFilm.getHeight()+69, 0.165*thinFilm.getHeight()+307);
            lightArrow2.setRotation(270-Math.toDegrees(angle));
            
            reflectArrow = new Arrow(new Vector2D(0, thinFilm.getLayoutY() - 300), ZERO_VECTOR, ZERO_VECTOR, thinFilm.getHeight()/2 + 5 , 5);
            reflectArrow.setRotation(270-Math.toDegrees(angle));
            reflectArrow.setPosition(1.8*thinFilm.getHeight()+70,0.66*thinFilm.getHeight()+303);
            reflectArrow.getArrow().setLayoutX(1.8*thinFilm.getHeight()+70);
            reflectArrow.getArrow().setLayoutY(-0.0005*thinFilm.getHeight()*thinFilm.getHeight()+1.3455*thinFilm.getHeight()+279);
            reflectArrow.setRotation(180 - (90-Math.toDegrees(angle)));

            reflectArrow2 = new Arrow(new Vector2D(0, thinFilm.getLayoutY() - 300), ZERO_VECTOR, ZERO_VECTOR, 75 , 5);
            reflectArrow2.setRotation(225);
            reflectArrow2.setPosition(1.8*thinFilm.getHeight()+127, 0.2185*thinFilm.getHeight() + 247);
            reflectArrow2.getArrow().setLayoutX(1.8*thinFilm.getHeight()+127);
            reflectArrow2.getArrow().setLayoutY(0.2185*thinFilm.getHeight() + 247);
            reflectArrow2.setRotation(135);
            
            lightArrow1 = new Arrow(new Vector2D(0, thinFilm.getLayoutY() - 300), ZERO_VECTOR, ZERO_VECTOR, 75 , 5);
            lightArrow1.setRotation(225);
            lightArrow1.setPosition(1.1665*thinFilm.getHeight()+69, 0.2185*thinFilm.getHeight() + 247);
            lightArrow1.getArrow().setLayoutX(1.1665*thinFilm.getHeight()+69);
            lightArrow1.getArrow().setLayoutY(0.2185*thinFilm.getHeight() + 247);
            lightArrow1.setRotation(135);
            
            addToPane(lightArrow.getArrow());
            String constructiveValues = "\nEnhanced Wavelengths:\n";
            String destructiveValues = "\nDestroyed Wavelengths:\n";
            for(double i = 1; i < (int)2*thickness*thinFilmIndex/400; i++){ 
                double constructive = 2*thickness*thinFilmIndex/i;
                double destructive = 2*thickness*thinFilmIndex/(i + 0.5);
                if(constructive <=700 && constructive >= 400)
                    constructiveValues += String.format("\n%.2f nm",constructive);
                if(destructive <=700 && destructive >= 400)
                    destructiveValues += String.format("\n%.2f nm",destructive);
            }
            
            constructiveLabel.setText(constructiveValues);
            destructiveLabel.setText(destructiveValues);
            
            
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
        
        
    }
}
