/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElectricityAndMagnetism;

import integrative_project.AnimationWindowController;
import integrative_project.Arrow;
import integrative_project.AssetManager;
import integrative_project.Constants;
import integrative_project.Projectile;
import integrative_project.Vector2D;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class CoulombsLawAnimationWindowController extends AnimationWindowController implements Initializable, Constants {
    
    private Boolean isPositiveQ1 = true;
    private Boolean isPositiveQ2;
    private double chargeQ2;
    private double chargeQ1;
    private double distance;
    private double force;
    private Projectile particle1;
    private Projectile particle2;
    private Arrow forceArrowQ1;
    private Arrow forceArrowQ2;
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series series = new XYChart.Series<>();
    private LineChart<Number,Number> forceGraph = new LineChart<Number,Number>(xAxis, yAxis);
    private double r = 0.05;
    private double currentVelocity;
    
    @FXML
    private TextField charge1TextField = new TextField();
    
    @FXML
    private TextField charge2TextField = new TextField();
    
    @FXML
    private Label chargeQ1Label = new Label();
    
    @FXML
    private Label chargeQ2Label = new Label();
    
    @FXML
    private Label forceLabel = new Label();
    
    @FXML
    private Label distanceLabel = new Label();
    
    @FXML
    AnchorPane anchorPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHelpMessage("help_messages/coulombs_law_help.txt");
        setTitleHelpWindow("Coulomb's Law Help Window");
        forceGraph.getData().add(series);
        forceGraph.setPrefSize(531, 268);
        addToGraphPane(forceGraph);
        xAxis.setLabel("Distance(m)");
        yAxis.setLabel("Force(N)");
        AssetManager.preloadCoulombsLawAssets();
        animationPane.setBackground(AssetManager.getDefaultBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());
    }
    
    protected void handlePauseButton(){
        super.handlePauseButton();
        //pauseButton.setDisable(true);
        currentVelocity = particle2.getVelocity().getX();
        //isPause = true;
        particle2.setVelocity(0, 0);
        particle2.setAcceleration(0, 0);
        //continueButton.setDisable(false);
    }
    
    
    protected void handleContinueButton(){
        //pauseButton.setDisable(false);
        super.handleContinueButton();
        particle2.setVelocity(currentVelocity, 0);
        //isPause = false;
        //continueButton.setDisable(true);
    }
    
    
    protected void handleResetButton(){
        super.handleResetButton();
        //pauseButton.setDisable(true);
        //continueButton.setDisable(true);
        //isPause = false;
        //lastFrameTime = 0.0f;
        //startButton.setDisable(false);
        //messageLabel.setText("");
        //validInput = true;
        //animationPane.getChildren().clear();
        chargeQ1Label.setText("Q1 = ");
        chargeQ2Label.setText("Q2 = ");
        distanceLabel.setText("Distance = ");
        forceLabel.setText("Force = ");
        r = 0.05;
        //timer.stop();
        series.getData().clear();
    }
    
    @Override
    protected void animationLoopActions(){
        
        particle2.update(frameDeltaTime);
        calculateForce();
        if(force>0)
            animationPane.getChildren().removeAll(forceArrowQ1.getArrow(),forceArrowQ2.getArrow());
        if(particle2.getPosition().getX() - 1.1*particle2.getRadius()<=  particle1.getPosition().getX() + particle1.getRadius() || particle2.getPosition().getX() >= animationPane.getWidth()-particle2.getRadius())
            particle2.setVelocity(-particle2.getVelocity().getX(), 0);
        createArrows();
    }
    
    @Override
    protected void loadObject(){
        errorMessage = "";
        try{
            chargeQ1 = getUserInput(charge1TextField);
            if(Math.abs(chargeQ1) > 25)
                throw new NumberFormatException("Charge value is too high");
        }
        catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            chargeQ1 = DEFAULT_CHARGE;
            setMessage("Default values used where input was empty");
        }
        
        try{
            chargeQ2 = getUserInput(charge2TextField);
            if(Math.abs(chargeQ2) > MAX_CHARGE)
                throw new NumberFormatException("Charge value is too high");
        }
        catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            chargeQ2 = DEFAULT_CHARGE;
            setMessage("Default values used where input was empty");
        }

        
        if(validInput == true){
            
            isPositiveQ1 = chargeQ1>0;
            isPositiveQ2 = chargeQ2>0;
            
            if(isPositiveQ1)
                chargeQ1Label.setText("Q1 = +" + chargeQ1+ " μC");
            else
                chargeQ1Label.setText("Q1 = " + chargeQ1+ " μC");

            if(chargeQ2 > 0)
                chargeQ2Label.setText("Q2 = +" + chargeQ2 + " μC");
            else
                chargeQ2Label.setText("Q2 = " + chargeQ2 + " μC");

            
            
            loadParticles();
            calculateForce();
            createArrows();
            //yAxis.setUpperBound(150);
            forceGraph.setAnimated(false);
            for(int i = 0; i < 25;i++){
                series.getData().add(new XYChart.Data<>(r, (9E9 * Math.abs(chargeQ1*chargeQ2))/Math.pow(r, 2)));
                r+=0.05;
            }
            yAxis.setUpperBound(150);
            addToPane(particle1.getCircle());
            addToPane(particle2.getCircle());
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
        
    }
    
    private void calculateForce(){
        distance = (particle2.getPosition().getX() - particle1.getPosition().getX()) * PIXEL_METER_RATIO/100;
        distanceLabel.setText(String.format("Distance = %.0f cm", distance));
        force = (9E9 * Math.abs(chargeQ1*chargeQ2))/Math.pow(distance/100, 2);
        forceLabel.setText(String.format("Force = %.3f N",force));
    }
    
    private void createArrows(){
        if(force>0){
           if(Objects.equals(isPositiveQ1, isPositiveQ2)){
                forceArrowQ1 = new Arrow(new Vector2D(particle1.getPosition().getX() - force*FORCE_LENGTH - 10 - particle1.getRadius(),particle1.getPosition().getY()), ZERO_VECTOR, ZERO_VECTOR, force*FORCE_LENGTH, 10);
                forceArrowQ2 = new Arrow(new Vector2D(particle2.getPosition().getX() + particle2.getRadius(), particle2.getPosition().getY()), ZERO_VECTOR, ZERO_VECTOR, force*FORCE_LENGTH, 10);
                forceArrowQ2.mirror();
            }
            else{
                forceArrowQ1 = new Arrow(new Vector2D(particle1.getPosition().getX() + particle1.getRadius(), particle1.getPosition().getY()), ZERO_VECTOR, ZERO_VECTOR, force*FORCE_LENGTH, 10);
                forceArrowQ1.mirror();
                forceArrowQ2 = new Arrow(new Vector2D(particle2.getPosition().getX() - force*FORCE_LENGTH - 10 - particle2.getRadius(), particle2.getPosition().getY()), ZERO_VECTOR, ZERO_VECTOR, force*FORCE_LENGTH, 10);

            }

            forceArrowQ1.getArrow().setFill(Paint.valueOf("red"));

            forceArrowQ2.getArrow().setFill(Paint.valueOf("red"));
            
            addToPane(forceArrowQ1.getArrow());
            addToPane(forceArrowQ2.getArrow());
            }
    }
    
    private void loadParticles(){
        chargeQ2 *= 1E-6;
        chargeQ1 *= 1E-6;
        particle1 = new Projectile(new Vector2D((Math.abs(chargeQ1)*CHARGE_RADIUS_RATIO) + 100,animationPane.getHeight()/2),
                                                ZERO_VECTOR,
                                                ZERO_VECTOR,
                                                (Math.abs(chargeQ1)*CHARGE_RADIUS_RATIO) + 4);
        
        particle2 = new Projectile(new Vector2D(animationPane.getWidth()-(Math.abs(chargeQ2)*CHARGE_RADIUS_RATIO) - 10, animationPane.getHeight()/2),
                                                TRAIN_VELOCITY,
                                                ZERO_VECTOR,
                                                (Math.abs(chargeQ2)*CHARGE_RADIUS_RATIO) + 4);
        
        if(isPositiveQ1)
                particle1.getCircle().setFill(AssetManager.getPositiveParticle());
        else if(chargeQ1 == 0)
            particle1.getCircle().setFill(Color.BLACK);
        else
            particle1.getCircle().setFill(AssetManager.getNegativeParticle());
        
        if(chargeQ2 > 0)
            particle2.getCircle().setFill(AssetManager.getPositiveParticle());
        else if(chargeQ2 == 0)
            particle2.getCircle().setFill(Color.BLACK);
        else
            particle2.getCircle().setFill(AssetManager.getNegativeParticle());
            
    }
    
}
