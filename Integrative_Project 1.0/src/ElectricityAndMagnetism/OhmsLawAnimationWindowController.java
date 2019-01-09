/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElectricityAndMagnetism;

import integrative_project.AnimationWindowController;
import integrative_project.AssetManager;
import integrative_project.Constants;
import integrative_project.Projectile;
import integrative_project.Vector2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class OhmsLawAnimationWindowController extends AnimationWindowController implements Initializable, Constants {
    
    @FXML
    private Slider voltageSlider = new Slider();
    
    @FXML
    private Slider resistanceSlider = new Slider();
    
    @FXML
    private Label voltageValueLabel = new Label();
    
    @FXML
    private Label resistanceValueLabel = new Label();
    
    @FXML
    private Label currentValueLabel = new Label();
    
    @FXML
    AnchorPane anchorPane;
    
    private ArrayList<Projectile> charges = null;
    private ArrayList<Vector2D> currentVelocity = null;
    private Vector2D velocity = null;
    private double voltageGraph = 0;
    private double currentGraph = 0;
    private double current;
    private NumberAxis xAxis = new NumberAxis(0,10,1);
    private NumberAxis yAxis = new NumberAxis(0,3,0.5);
    private XYChart.Series ElectricSeries = new XYChart.Series<>();
    private LineChart<Number,Number> graph = new LineChart<Number,Number>(xAxis, yAxis);
    private int chargeNumber = 1;
    private double loops = 0;
    private Vector2D position = new Vector2D(487, 444);
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHelpMessage("help_messages/ohms_law_help.txt");
        setTitleHelpWindow("Ohm's Law Help Window");
        AssetManager.preloadOhmsLawAssets();
        animationPane.setBackground(AssetManager.getOhmsAnimationBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());
        charges = new ArrayList<>();
        currentVelocity = new ArrayList<>();
        voltageSlider.setMax(MAX_VOLTAGE);
        voltageSlider.setMin(1);
        voltageSlider.setValue(2.5);
        voltageSlider.setShowTickLabels(true);
        voltageSlider.setShowTickMarks(true);
        voltageSlider.setMajorTickUnit(1);
        voltageSlider.setBlockIncrement(0.5);
        
        voltageSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    voltageValueLabel.setText(String.format("%.2f", new_val) + " V");
            }
        });
        
        resistanceSlider.setMax(MAX_RESISTANCE);
        resistanceSlider.setMin(10);
        resistanceSlider.setValue(50);
        resistanceSlider.setShowTickLabels(true);
        resistanceSlider.setShowTickMarks(true);
        resistanceSlider.setMajorTickUnit(10);
        resistanceSlider.setBlockIncrement(1);
        
        resistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    resistanceValueLabel.setText(String.format("%.2f", new_val) + " Ω");
            }
        });
        
        setValueLabel();
        
        xAxis.setLabel("Voltage (V)");
        yAxis.setLabel("Current (A)");
        graph.getData().add(ElectricSeries);
        graph.setPrefSize(950, 260);
        addToGraphPane(graph);
        
        
    }
  

    @Override
    public void animationLoopActions(){
        if(voltageSlider.getValue() == 0){
            setMessage("The voltage is zero, therefore there is no current");
        }
        
        if(loops >= 0.125*Constants.LOOPS_PER_SECOND/current && !isPause){
            charges.add(new Projectile(position, velocity , new Vector2D(0,0), CHARGE_DEFAULT_RADIUS));
            charges.get(chargeNumber).getCircle().setFill(Color.WHITE);
            addToPane(charges.get(chargeNumber).getCircle());
            chargeNumber++;
            loops = 0;
        }
        
        else{
            for (Projectile iterationProjectile : charges) {
                if(animationPane.getChildren().contains(iterationProjectile.getCircle()))
                    iterationProjectile.update(frameDeltaTime);
                if(!isPause){
                  if(iterationProjectile.position.getX() >= 875 && iterationProjectile.position.getY() >= 444){
                    iterationProjectile.setVelocity(0, -velocity.getX());

                    }
                    if( iterationProjectile.position.getX() >= 875 &&  iterationProjectile.position.getY() <= 96)
                        iterationProjectile.setVelocity(-velocity.getX(),0);

                    if(iterationProjectile.position.getX() <= 100 && iterationProjectile.position.getY() <= 96){
                        iterationProjectile.setVelocity(0,velocity.getX());
                    }

                    if(iterationProjectile.position.getX() <= 100 && iterationProjectile.position.getY() >= 444)
                            iterationProjectile.setVelocity(velocity.getX() ,0);
                    

                    if(iterationProjectile.position.getX()>= position.getX() && iterationProjectile.getPosition().getY() > position.getY())
                        animationPane.getChildren().remove(iterationProjectile.getCircle());  
                }
            }
            
        }
        if(!isPause)
            loops++;
        
    }
    
    private void setValueLabel(){
        
        String voltageValue = String.format("%.2f", voltageSlider.getValue()) + " V";
        String resistanceValue = String.format("%.2f", resistanceSlider.getValue()) + " Ω";
        String currentValue = "Current: " + String.format("%.3f", (voltageSlider.getValue() / resistanceSlider.getValue())) + " A";
        
        voltageValueLabel.setText(voltageValue);
        resistanceValueLabel.setText(resistanceValue);
        currentValueLabel.setText(currentValue);
    }

    
    protected void handlePauseButton(){
        //pauseButton.setDisable(true);
        //isPause = true;
        super.handlePauseButton();
        for(int i = 0; i < charges.size(); i++){
            currentVelocity.add(new Vector2D(charges.get(i).getVelocity().getX(), charges.get(i).getVelocity().getY()));
            charges.get(i).setVelocity(ZERO_VECTOR);
            
        }
        
        //continueButton.setDisable(false);
    }
    
    
    protected void handleContinueButton(){
        for(int i = 0; i < charges.size(); i++)
            charges.get(i).setVelocity(currentVelocity.get(i));
        super.handleContinueButton();
        currentVelocity.clear();
        //pauseButton.setDisable(false);
        //isPause = false;
        //continueButton.setDisable(true);
    }
    
   
    protected void handleResetButton(){
        super.handleResetButton();
        /*pauseButton.setDisable(true);
        continueButton.setDisable(true);
        isPause = false;
        lastFrameTime = 0.0f;
        startButton.setDisable(false);
        animationPane.getChildren().clear();*/
        charges.clear();
        ElectricSeries.getData().clear();
        chargeNumber = 1;
        loops = 0;
        /*messageLabel.setText("");
        validInput = true;
        timer.stop();*/
    }
    
    @Override
    public void loadObject(){
        errorMessage = "";
        if(validInput == true){
            loadCharges();
            yAxis.setAutoRanging(true);
            for(int i = 0; i < 25; i++){
                   ElectricSeries.getData().add(new XYChart.Data<>(voltageGraph,currentGraph));
                   voltageGraph += 0.5;
                   currentGraph = voltageGraph/resistanceSlider.getValue();
                   
            }
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
        
        
         
    }
    
    private void loadCharges(){
        double voltage = voltageSlider.getValue();
        double resistance = resistanceSlider.getValue();
        current = voltage/resistance;
        velocity = new Vector2D(150,0);
        charges.add(new Projectile(position, velocity , new Vector2D(0,0), CHARGE_DEFAULT_RADIUS));
        charges.get(0).getCircle().setFill(Color.WHITE);
        setValueLabel();
        addToPane(charges.get(0).getCircle());
        
        System.out.print(0.125*Constants.LOOPS_PER_SECOND/current);
        
    }
}
