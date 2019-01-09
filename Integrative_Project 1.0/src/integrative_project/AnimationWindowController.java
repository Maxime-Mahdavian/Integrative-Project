/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author cstuser
 */
public class AnimationWindowController implements Constants{
    protected boolean isPause = false;
    protected String errorMessage = "";
    protected boolean isContinuePressed = false;
    protected double lastFrameTime = 0.0;
    protected String helpMessage = "";
    protected double frameDeltaTime;
    protected double currentTime;
    protected AnimationTimer timer;
    protected long timePaused = 0;
    protected long initialTime;
    protected boolean validInput = true;
    protected Label helpLabel;
    protected String title = "";
  
    @FXML
    AnchorPane anchorPane;
    
    @FXML
    protected AnchorPane animationPane;
    
    @FXML
    protected AnchorPane graphPane;
    
    @FXML
    protected Button startButton;
    
    @FXML
    protected Button continueButton;
    
    @FXML
    protected Button resetButton;
    
    @FXML
    protected Button pauseButton;
    
    @FXML
    protected Label messageLabel = new Label();
    
    @FXML
    private void handleBackButton() throws IOException{
        Scene scene = animationPane.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("/integrative_project/MenuWindow.fxml"));
        stage.setScene(new Scene(root));
    }
    
    protected void loadObject(){
        
    }
    

    
    @FXML
    protected void handleStartButton(){
        validInput = true;
        loadObject();
        if(validInput == true){
            lastFrameTime = 0.0f;
            initialTime = System.nanoTime();
            startButton.setDisable(true);
            resetButton.setDisable(false);
            pauseButton.setDisable(false);
            timer = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    currentTime = (now - initialTime)/1000000000.0;
                    frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;
                    animationLoopActions();             
                }   
            };
            timer.start(); 
        }
         
         
        
    }
    
    protected void animationLoopActions(){
        
    }
    
    
    @FXML
    protected void handleResetButton(){
        pauseButton.setDisable(true);
        continueButton.setDisable(true);
        isPause = false;
        lastFrameTime = 0.0f;
        startButton.setDisable(false);
        animationPane.getChildren().clear();
        messageLabel.setText("");
        validInput = true;
        timer.stop();
    }

   
    @FXML
    protected void handleHelpButton(){
     
        String help = null;
        try{
            help = new String(Files.readAllBytes(Paths.get(helpMessage)));
        }
        catch(IOException e){
            setMessage("Something went wrong");
            System.out.println(e.getStackTrace());
        }

        helpLabel = new Label(help);
        
        Scene helpScene = new Scene(helpLabel,600,400);
        Stage helpWindow = new Stage();
        helpWindow.setTitle(title);
        helpWindow.setScene(helpScene);
        
        helpWindow.setX(500);
        helpWindow.setY(200);
       
        helpWindow.show();
        
    }
    
    @FXML
    protected void handleContinueButton(){
        pauseButton.setDisable(false);
        continueButton.setDisable(true);
        isPause = false;
    }
    
    @FXML
    protected void handlePauseButton(){
        pauseButton.setDisable(true);
        continueButton.setDisable(false);
        isPause = true;
    }
    
    protected double getUserInput(TextField textField){
        if(textField.getText().isEmpty())
            throw new IllegalArgumentException();
        else
            return (Double.valueOf(textField.getText()));
    }
    
    protected void setHelpMessage(String helpMessage){
        this.helpMessage = helpMessage;
       
    }
    
    protected void setTitleHelpWindow(String title){
        this.title = title;
    }
    
    protected void setMessage(String message){
        messageLabel.setText(message);
    }
    
    public void addToPane(Node node){
        animationPane.getChildren().add(node);
    }
    
    public void addToGraphPane(Node node){
        graphPane.getChildren().add(node);
    }
    
}
