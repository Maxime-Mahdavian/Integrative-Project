/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author Max
 */
public class AssetManager {
    
    static private Image paneBackgroundImage = null;
    static private Background paneBackground = null;
    
    static private ImagePattern baseballBall = null;
    static private Image projectileAnimationBackgroundImage = null;
    static private Background projectileAnimationBackground = null;
    
    static private ImagePattern positiveParticle = null;
    static private ImagePattern negativeParticle = null;
    
    static private Image ohmsAnimationBackgroundImage = null;
    static private Background ohmsAnimationBackground = null;
    
    static private ImagePattern pendulumBob = null;
    static private ImagePattern pendulumWall = null;
    
    static private ImagePattern bouncyBall = null;
    static private ImagePattern footballBall = null;
    static private ImagePattern basketballBall = null;
    static private ImagePattern bowlingBall = null;
    static private Image energyConservationBackgroundImage = null;
    static private Background energyConservationBackground = null;
    
    
    static private String fileURL(String relativePath){
        
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadMainMenuAssets(){
        
        paneBackgroundImage = new Image(new File("./assets/images/default_background.png").toURI().toString());
        paneBackground = new Background(new BackgroundImage(paneBackgroundImage, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundPosition.DEFAULT, 
                                                                   BackgroundSize.DEFAULT));
    }
    
    static public void preloadProjectileMotionAssets(){
        
        baseballBall = new ImagePattern(new Image(fileURL("./assets/images/Projectile_Animation/baseball.png")));
        projectileAnimationBackgroundImage = new Image(new File("./assets/images/Projectile_Animation/background.png").toURI().toString());
        projectileAnimationBackground = new Background(new BackgroundImage(projectileAnimationBackgroundImage, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundPosition.DEFAULT, 
                                                                   BackgroundSize.DEFAULT));
    }
    

    
    static public void preloadEnergyConservationAssets(){
        
        bouncyBall = new ImagePattern(new Image(fileURL("./assets/images/Conservation_Energy/bouncy_Ball.png")));
        basketballBall = new ImagePattern(new Image(fileURL("./assets/images/Conservation_Energy/basketball_Ball.png")));
        footballBall = new ImagePattern(new Image(fileURL("./assets/images/Conservation_Energy/football_Ball.png")));
        bowlingBall = new ImagePattern(new Image(fileURL("./assets/images/Conservation_Energy/bowling_Ball.png")));
        energyConservationBackgroundImage = new Image(fileURL("./assets/images/Conservation_Energy/backgroundEC.png"));
        energyConservationBackground = new Background(new BackgroundImage(energyConservationBackgroundImage, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundPosition.DEFAULT, 
                                                                   BackgroundSize.DEFAULT));
        
        
    }
    
    static public void preloadCoulombsLawAssets(){
        
        positiveParticle = new ImagePattern(new Image(fileURL("./assets/images/Coulombs_Law/positive_particle.png")));
        negativeParticle = new ImagePattern(new Image(fileURL("./assets/images/Coulombs_Law/negative_particle.png")));
        
        
    }
    
    static public void preloadOhmsLawAssets(){
        ohmsAnimationBackgroundImage = new Image(new File("./assets/images/Ohms_Law/animation_background.png").toURI().toString());
        ohmsAnimationBackground = new Background(new BackgroundImage(ohmsAnimationBackgroundImage, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundRepeat.NO_REPEAT, 
                                                                   BackgroundPosition.DEFAULT, 
                                                                   BackgroundSize.DEFAULT));
    }
    
    static public void preloadThinFilmAssets(){
        
    }
    
    static public void preloadPendulumAssets(){
        pendulumBob = new ImagePattern(new Image(fileURL("./assets/images/Pendulum/ball.png")));
        pendulumWall = new ImagePattern(new Image(fileURL("./assets/images/Pendulum/wall.png")));
    }
    
    
    //Default
    static public Background getDefaultBackground(){
        return paneBackground;
    }
    
    
    //Projectile Motion
    static public ImagePattern getBaseballBall(){
        return baseballBall;
    }
    
    static public Background getProjectileAnimationBackground(){
        return projectileAnimationBackground;
    }
    
    
    //Energy Conservation
    static public ImagePattern getBouncyBall(){
        return bouncyBall;
    }
    
    static public ImagePattern getFootballBall(){
        return footballBall;
    }
    
    static public ImagePattern getBasketballBall(){
        return basketballBall;
    }
    
    static public ImagePattern getBowlingBall(){
        return bowlingBall;
    }
    
    static public Background getEnergyConservationBackground(){
        return energyConservationBackground;
    }
    
    
    //Coulomb's Law
    static public ImagePattern getPositiveParticle(){
        return positiveParticle;
    }
    
    static public ImagePattern getNegativeParticle(){
        return negativeParticle;
    }
    
    
    //Ohm's Law
    static public Background getOhmsAnimationBackground(){
        return ohmsAnimationBackground;
    }
    
    
    //Pendulum
    static public ImagePattern getPendulumBob(){
        return pendulumBob;
    }
    
    static public ImagePattern getPendulumWall(){
        return pendulumWall;
    }
}
