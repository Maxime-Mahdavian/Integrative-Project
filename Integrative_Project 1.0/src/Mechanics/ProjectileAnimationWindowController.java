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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Owner
 */



public class ProjectileAnimationWindowController extends AnimationWindowController implements Initializable, Constants {
    private Projectile projectile;
    private Vector2D currentVelocity = new Vector2D(0,0);
    private double initialVelocity = 0;
    private double initialAngle = 0;
    private NumberAxis xAxis = new NumberAxis(0,8,0.5);
    private NumberAxis yAxis = new NumberAxis(0,25,5);
    private double loops = Constants.LOOPS_PER_SECOND/4;
    private double timeSeconds = 0;
    private XYChart.Series series = new XYChart.Series<>();
    private LineChart<Number,Number> positionGraph = new LineChart<Number,Number>(xAxis, yAxis);
    
    @FXML
    TextField velocityTextField = new TextField();
    
    @FXML
    TextField angleTextField = new TextField();
    
    @FXML
    AnchorPane anchorPane;
    
    
    @Override
    protected void animationLoopActions(){
        projectile.update(frameDeltaTime);
        if(isPause == false){
            if(loops >= LOOPS_PER_SECOND/4){
                series.getData().add(new XYChart.Data<>(timeSeconds, ((DEFAULT_POSTITION.getY()-projectile.position.getY())/PIXEL_METER_RATIO)));
                loops = 0;
                timeSeconds+= 0.25;
            }  
        }
        if(!isPause)
            loops++;
    }
    
    @Override
    protected void loadObject(){
        errorMessage = "";
        try{
            initialVelocity = getUserInput(velocityTextField) * PIXEL_METER_RATIO;
            if(initialVelocity > 50 * PIXEL_METER_RATIO)
                throw new NumberFormatException("Velocity value is too high");
            else if(initialVelocity < 0)
                throw new NumberFormatException("Velocity is negative");
        }
        catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            initialVelocity = DEFAULT_VELOCITY;
            setMessage("Default values used where input was empty");
        }
        
        try{
            initialAngle = getUserInput(angleTextField);
            initialAngle = initialAngle % 360;
            initialAngle = (initialAngle + 360) % 360;
            if(initialAngle > 180)
                initialAngle -= 360;
            System.out.println(initialAngle);
            if(initialAngle > 90 || initialAngle < 0)
                throw new NumberFormatException("Angle is not within the first quadrant");
        }
        catch(NumberFormatException e){
            validInput = false;
            errorMessage +=e.getMessage() + "; ";
            
        }
        catch(IllegalArgumentException e){
            initialAngle = DEFAULT_ANGLE;
            setMessage("Default values used where input was empty");
        }
        
        if(validInput == true){
            load2DProjectile();
            addToPane(projectile.getCircle());  
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
        xAxis.setLabel("Time");
        yAxis.setLabel("Height");
        yAxis.setUpperBound(Math.round((Math.pow((initialVelocity*Math.sin(Math.toRadians(initialAngle)))/PIXEL_METER_RATIO, 2)/(2*9.8))+1));
    }
  
    
    protected void handlePauseButton(){
        //pauseButton.setDisable(true);
        super.handlePauseButton();
        currentVelocity.setVector(projectile.getVelocity());
        //isPause = true;
        projectile.setVelocity(0, 0);
        projectile.setAcceleration(0, 0);
        //continueButton.setDisable(false);
    }
    
    
    protected void handleContinueButton(){
        //pauseButton.setDisable(false);
        super.handleContinueButton();
        projectile.setVelocity(currentVelocity);
        //isPause = false;
        projectile.setAcceleration(0, GRAVITY * PIXEL_METER_RATIO);
        //continueButton.setDisable(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHelpMessage("help_messages/projectile_animation_help.txt");
        setTitleHelpWindow("Projectile Animation Help Window");
        positionGraph.getData().add(series);
        positionGraph.setPrefSize(1062, 268);
        addToGraphPane(positionGraph);
        AssetManager.preloadProjectileMotionAssets();
        animationPane.setBackground(AssetManager.getProjectileAnimationBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());


    }
    
    
    
    public void handleResetButton(){
        /*pauseButton.setDisable(true);
        continueButton.setDisable(true);
        isPause = false;
        lastFrameTime = 0.0f;
        startButton.setDisable(false);
        animationPane.getChildren().clear();
        messageLabel.setText("");
        validInput = true;
        timer.stop();*/
        super.handleResetButton();
        series.getData().clear();
        timeSeconds = 0;
        loops = LOOPS_PER_SECOND/4;
    }
    
    
    public void load2DProjectile(){
        Vector2D projectileVelocity = new Vector2D((Math.cos(Math.toRadians(initialAngle))*initialVelocity),-(Math.sin(Math.toRadians(initialAngle))*initialVelocity));
        projectile = new Projectile(DEFAULT_POSTITION, projectileVelocity, DEFAULT_ACCELERATION, DEFAULT_RADIUS);
        projectile.setAcceleration(0, GRAVITY * PIXEL_METER_RATIO);
        projectile.getCircle().setFill(AssetManager.getBaseballBall());
    }

    
}
