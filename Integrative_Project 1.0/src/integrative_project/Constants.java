/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

/**
 *
 * @author cstuser
 */
public interface Constants {
    
    //2D Kinematics Projectile Motion
    final double GRAVITY = 9.8;
    final Vector2D DEFAULT_ACCELERATION = new Vector2D(0, GRAVITY);
    final Vector2D DEFAULT_POSTITION = new Vector2D(10, 470);
    final double DEFAULT_RADIUS = 10;
    final double DEFAULT_ANGLE = 45;
    final double DEFAULT_VELOCITY = 300;
    final double PIXEL_METER_RATIO = 10;
    final double LOOPS_PER_SECOND = 60;
    
    //Conservation of Energy
    final double DEFAULT_INITIAL_HEIGHT = 50;
    final double DEFAULT_MASS = 10;
    final Vector2D ZERO_VECTOR = new Vector2D(0,0);
    
    //Coulomb's Law
    final double DEFAULT_CHARGE = 5;
    final double CHARGE_RADIUS_RATIO = 1.5E6;
    final double FORCE_LENGTH = 4;
    final double MAX_CHARGE = 25;
    final Vector2D TRAIN_VELOCITY = new Vector2D(-65,0);
    
    //Ohm's Law
    
    final double MAX_VOLTAGE = 5;
    final double MAX_RESISTANCE = 100;
    final double CHARGE_DEFAULT_RADIUS = 5;
    
    //Pendulum
    
    final double DEFAULT_LENGTH_STRING = 10;
    final double PIXEL_CM = 8;
    final double DEFAULT_STRING_ANGLE = 75;
    

    //Thin Films
    final double DEFAULT_THICKNESS = 500;
    final double PIXEL_NM = 0.1;
    final Vector2D ARROW_SPEED = new Vector2D(100, 100);
    final Vector2D REFLECTED_SPEED = new Vector2D(100, -100);
}
