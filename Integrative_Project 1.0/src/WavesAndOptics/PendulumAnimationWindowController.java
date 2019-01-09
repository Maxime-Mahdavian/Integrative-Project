/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WavesAndOptics;

import integrative_project.AnimationWindowController;
import integrative_project.AssetManager;
import integrative_project.Constants;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class PendulumAnimationWindowController extends AnimationWindowController implements Initializable, Constants {
    
    private Arc pendulumPath;
    private Circle pendulumBall;
    private double lengthOfString;
    private double initialAngle;
    private Line string;
    private PathTransition pathTransition;
    private Timeline secondTime;
    private Rotate rotation;
    private Duration pauseTime;
    private NumberAxis xAxis = new NumberAxis(0,6,0.5);
    private NumberAxis yAxis = new NumberAxis(0,55,2.5);
    private double loops = Constants.LOOPS_PER_SECOND/6;
    private double timeSeconds = 0;
    private XYChart.Series series = new XYChart.Series<>();
    private LineChart<Number,Number> positionGraph = new LineChart<Number,Number>(xAxis, yAxis);
    
    @FXML
    TextField lengthTextField = new TextField();
    
    @FXML
    TextField angleTextField = new TextField();
    
    @FXML
    Rectangle wall = new Rectangle();
    
    @FXML
    AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AssetManager.preloadPendulumAssets();
        animationPane.setBackground(AssetManager.getDefaultBackground());
        graphPane.setBackground(AssetManager.getDefaultBackground());
        anchorPane.setBackground(AssetManager.getDefaultBackground());
        wall.setFill(AssetManager.getPendulumWall());
        setHelpMessage("help_messages/pendulum_help.txt");
        setTitleHelpWindow("Pendulum Help Window");
        positionGraph.getData().add(series);
        positionGraph.setPrefSize(1062,260);
        addToGraphPane(positionGraph);
    }
    
    @Override
    protected void animationLoopActions(){
        if(isPause == false){
            pathTransition.play();
            secondTime.play();
            if(loops >= LOOPS_PER_SECOND/6){
                series.getData().add(new XYChart.Data<>(timeSeconds, (pendulumBall.getTranslateY())/ PIXEL_CM));
                loops = 0;
                timeSeconds+= 0.1;
            }
        }
        loops++;
        
    }
    
    protected void handlePauseButton(){
        //isPause = true;
        //pauseButton.setDisable(true);
        //continueButton.setDisable(false);
        super.handlePauseButton();
        pathTransition.pause();
        pauseTime = pathTransition.getCurrentTime();
        secondTime.pause();
        secondTime.setCycleCount(0);
    }
    
    protected void handleContinueButton(){
        //continueButton.setDisable(true);
        //isPause = false;
        //pauseButton.setDisable(false);
        super.handleContinueButton();
        pathTransition.playFrom(pauseTime);
        secondTime.playFrom(pauseTime);
    }
    
    protected void handleResetButton(){
        super.handleResetButton();
        //messageLabel.setText("");
        //isPause = true;
        //pauseButton.setDisable(true);
        //continueButton.setDisable(true);
        //startButton.setDisable(false);
        //timer.stop();
        pathTransition.stop();
        secondTime.stop();
        secondTime.setCycleCount(Timeline.INDEFINITE);
        animationPane.getChildren().remove(pendulumBall);
        animationPane.getChildren().remove(pendulumPath);
        animationPane.getChildren().remove(string);
        series.getData().clear();
        timeSeconds = 0;
        loops = LOOPS_PER_SECOND/6;
    }
    
    @Override
    protected void loadObject(){
        try{
            errorMessage = "";
            lengthOfString = getUserInput(lengthTextField) * PIXEL_CM;
            if(lengthOfString > 50 * PIXEL_CM)
                throw new NumberFormatException("String length value is too high!");
            else if(lengthOfString < 0)
                throw new NumberFormatException("String length cannot be negative");
            else if(lengthOfString == 0)
                throw new NumberFormatException("There is no string");
        }
        catch(NumberFormatException e){
            errorMessage +=e.getMessage() + "; ";
            validInput = false;
        }
        catch(IllegalArgumentException e){
            lengthOfString = DEFAULT_LENGTH_STRING * PIXEL_CM;
            setMessage("Default values used where input was empty");
        }
        
        try{
            initialAngle = getUserInput(angleTextField);
            initialAngle = initialAngle % 360;
            initialAngle = (initialAngle + 360) % 360;
            if(initialAngle > 180)
                initialAngle -= 360;
            System.out.println(initialAngle);
            if(initialAngle > 90 || initialAngle < -90)
                throw new NumberFormatException("Angle is not within the first or fourth quadrant");
        }
        catch(NumberFormatException e){
            validInput = false;
            errorMessage +=e.getMessage() + "; ";
            
        }
        catch(IllegalArgumentException e){
            initialAngle = DEFAULT_STRING_ANGLE;
            setMessage("Default values used where input was empty");
        }
        
        if(validInput == true){
            isPause = false;
            loadPendulum();
            addToPane(string);
            addToPane(pendulumBall);
            addToPane(pendulumPath);
            
            rotation = new Rotate(-initialAngle);
            string.getTransforms().add(rotation);
            
            secondTime = new Timeline(new KeyFrame(Duration.seconds(4*Math.PI*Math.sqrt((lengthOfString/PIXEL_CM/100)/GRAVITY)),
                                                    new KeyValue(rotation.angleProperty(), initialAngle, Interpolator.EASE_BOTH)));
            secondTime.setAutoReverse(true);
            secondTime.setCycleCount(Timeline.INDEFINITE);
            
            pathTransition = new PathTransition();
            pathTransition.setNode(pendulumBall);
            pathTransition.setPath(pendulumPath);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setDuration(Duration.seconds(4*Math.PI*Math.sqrt((lengthOfString/PIXEL_CM/100)/GRAVITY)));
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.setAutoReverse(true);
            
            xAxis.setLabel("Time");
            yAxis.setLabel("Displacement along Y axis");
            yAxis.setUpperBound((Math.round((Math.sin(Math.toRadians(Math.abs(initialAngle))) * lengthOfString)/PIXEL_CM)  + 5)); 
        }
        else
            setMessage("Invalid input, animation will not start " + errorMessage);
        
        
        
    }
    
    protected void loadPendulum(){
        double initialX = lengthOfString * Math.sin(Math.toRadians(initialAngle)) + animationPane.getWidth()/2;
        double initialY = lengthOfString * Math.cos(Math.toRadians(initialAngle))+ 25;

        pendulumPath = new Arc(animationPane.getWidth()/2, 25, lengthOfString, lengthOfString, initialAngle-90, -2*initialAngle);
        pendulumPath.setOpacity(0);
        pendulumPath.setType(ArcType.OPEN);
        
        string = new Line(0, lengthOfString, 0, 0);
        string.setTranslateX(animationPane.getWidth()/2);
        string.setTranslateY(25);
        
        pendulumBall = new Circle(initialX, initialY, DEFAULT_RADIUS);
        pendulumBall.setFill(AssetManager.getPendulumBob());
        
    }
}
