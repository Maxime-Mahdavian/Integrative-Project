/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mechanics;

import integrative_project.AnimationWindowController;
import integrative_project.AssetManager;
import integrative_project.Constants;
import integrative_project.Projectile;
import integrative_project.Vector2D;
import static integrative_project.Constants.DEFAULT_INITIAL_HEIGHT;
import static integrative_project.Constants.DEFAULT_MASS;
import static integrative_project.Constants.DEFAULT_RADIUS;
import static integrative_project.Constants.GRAVITY;
import static integrative_project.Constants.PIXEL_METER_RATIO;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class EnergyConservationAnimationWindowController extends AnimationWindowController implements Initializable, Constants{
    /**
     * Initializes the controller class.
     */
   
    @FXML
    TextField massTextField =  new TextField();
    
    @FXML
    TextField heightTextField =  new TextField();
    
    @FXML
    private ChoiceBox dampingBox = new ChoiceBox(FXCollections.observableArrayList("No Damping", "20%" , "50%" , "70%"));
    
    @FXML
    AnchorPane anchorPane;
    
    private double mass;
    private double height;
    private Projectile ball;
    private double currentVelocity;
    private double currentHeight;
    private double kineticEnergy;
    private double potentialEnergy;
    private double mechanicalEnergy;
    private double maximumSpeed;
    private double dampingConstant;
    final String[] nameOfAxis = {"Kinetic Energy", "Potential Energy"};
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis(0, mechanicalEnergy, 100);
    final BarChart<String,Number> energyGraph = new BarChart<String,Number>(xAxis,yAxis);
    XYChart.Series <String,Number> energySeries = new XYChart.Series<String, Number>();
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        setHelpMessage("help_messages/conservation_of_energy_help.txt");
        setTitleHelpWindow("Conservation of Energy Help Window");
        
        dampingBox.getItems().add("No Damping");
        dampingBox.getItems().add("20%");
        dampingBox.getItems().add("50%");
        dampingBox.getItems().add("70%");
        dampingBox.getSelectionModel().selectedItemProperty()
            .addListener((ObservableValue observable, 
                    Object oldValue, Object newValue) -> {
                            getDamping(newValue.toString());  
        });
        dampingBox.getSelectionModel().selectFirst();
        energyGraph.getData().add(energySeries);
        energyGraph.setPrefHeight(741);
        addToGraphPane(energyGraph);
        energySeries.setName("Energy (J)");
        energyGraph.setTitle("Energy Conservation Graph");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(nameOfAxis)));
        energyGraph.setAnimated(false);
        AssetManager.preloadEnergyConservationAssets();
        animationPane.setBackground(AssetManager.getEnergyConservationBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());
    }
    
    public void getDamping(String value){

        if(value.equals("No Damping")){
            dampingConstant = 1;
        }   
        if(value.equals("20%")){
            dampingConstant = 0.8;
        }
        if(value.equals("50%")){
            dampingConstant = 0.5;
        }
        if(value.equals("70%")){
            dampingConstant = 0.3;
        }
    }
    
    @Override
    public void animationLoopActions(){
       calculateEnergy();
       if(isPause == false){            
            energySeries.getData().clear();
            energySeries.getData().add(new XYChart.Data<String,Number>(nameOfAxis[0],kineticEnergy));
            energySeries.getData().add(new XYChart.Data<String,Number>(nameOfAxis[1],potentialEnergy));
        }
        ball.update(frameDeltaTime);
        currentHeight = (animationPane.getHeight() - (ball.getPosition().getY()) - DEFAULT_RADIUS) / PIXEL_METER_RATIO;
        //setMessage("Height: " + (currentHeight) + "    Position: " + Math.round(ball.getPosition().getY() - DEFAULT_RADIUS));
        
        if(ball.position.getY() >= animationPane.getHeight() - ball.getCircle().getRadius()){
            mechanicalEnergy *= dampingConstant;
            maximumSpeed = Math.sqrt((mechanicalEnergy * 2) / mass);
            ball.velocity.setY(-maximumSpeed * PIXEL_METER_RATIO);   
        }
        
        if(Math.round(mechanicalEnergy) <= 0){
            timer.stop();
        }

    }
    
    private void calculateEnergy(){
        kineticEnergy = 0.5 * mass * Math.pow(ball.getVelocity().getY()/PIXEL_METER_RATIO , 2);
        potentialEnergy = mechanicalEnergy - kineticEnergy;
        
    }
    
    @Override
    public void loadObject(){
        errorMessage = "";
        try{
            mass = getUserInput(massTextField); 
            if(mass > 50)
                throw new NumberFormatException("Mass is too big!");
            else if(mass < 0)
                throw new NumberFormatException("Mass is smaller than 0");
            else if(mass < 0.01)
                throw new NumberFormatException("Mass is too small");
        }catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            mass = DEFAULT_MASS;
            setMessage("Default values used where input was empty");
        }
        
        try{
            height = getUserInput(heightTextField);
            if(height > 75)
                throw new NumberFormatException("Height is too big");
            else if(height < 0)
                throw new NumberFormatException("Height is smaller than 0");
            else if(height < 10)
                throw new NumberFormatException("Height is too small");
        }catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            height = DEFAULT_INITIAL_HEIGHT;
            setMessage("Default values used where input was empty");
        }
        if(validInput == true){
            mechanicalEnergy = mass * height * GRAVITY;
            maximumSpeed = Math.sqrt((mechanicalEnergy * 2) / mass);
            yAxis.setUpperBound(mechanicalEnergy);
            yAxis.setAutoRanging(false);
            yAxis.setAnimated(false);
            loadBall();
            addToPane(ball.getCircle());
            
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
    }
    
    protected void handlePauseButton(){
        super.handlePauseButton();
        //pauseButton.setDisable(true);
        currentVelocity = ball.getVelocity().getY();
        //isPause = true;
        ball.setVelocity(0, 0);
        ball.setAcceleration(0, 0);
        //continueButton.setDisable(false);
    }
    
    protected void handleContinueButton(){
        //pauseButton.setDisable(false);
        super.handleContinueButton();
        ball.setVelocity(0,currentVelocity);
        //isPause = false;
        ball.setAcceleration(0, GRAVITY * PIXEL_METER_RATIO);
        //continueButton.setDisable(true);
    }
    
    @Override
    public void handleResetButton(){
        /*pauseButton.setDisable(true);
        continueButton.setDisable(true);
        setMessage("");
        isPause = false;
        lastFrameTime = 0.0f;
        startButton.setDisable(false);
        animationPane.getChildren().clear();
        messageLabel.setText("");
        validInput = true;
        timer.stop();*/
        super.handleResetButton();
        energySeries.getData().clear();
        
    }
    
    private void loadBall(){
        Vector2D position = new Vector2D(animationPane.getWidth()/2, animationPane.getHeight() - height * PIXEL_METER_RATIO + DEFAULT_RADIUS);
        ball = new Projectile(position, Constants.ZERO_VECTOR,new Vector2D(0, GRAVITY * PIXEL_METER_RATIO), Constants.DEFAULT_RADIUS);
        
        if(dampingConstant == 1){
            ball.getCircle().setFill(AssetManager.getBouncyBall());            
        }
        if(dampingConstant == 0.8){
            ball.getCircle().setFill(AssetManager.getBasketballBall());
        }
        if(dampingConstant == 0.5){
            ball.getCircle().setFill(AssetManager.getFootballBall());
        }
        if(dampingConstant == 0.3){
            ball.getCircle().setFill(AssetManager.getBowlingBall());
        }

    }
    
}
