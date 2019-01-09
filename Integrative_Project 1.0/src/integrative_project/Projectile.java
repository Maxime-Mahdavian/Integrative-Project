/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Owner
 */
public class Projectile extends ShapeObject{
    private Circle circle;
    private double radius;
    
    public Projectile(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius){
        super(position, velocity, acceleration);
        
        this.radius = radius;
        circle = new Circle(radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
        circle.setFill(Paint.valueOf("black"));
    }
    
    @Override
    public void setPosition(double x, double y){
        super.setPosition(x, y);
        circle.setLayoutX(position.getX() - circle.getLayoutBounds().getMinX());
        circle.setLayoutY(position.getY() - circle.getLayoutBounds().getMinY());  
    }
    
    public double getRadius(){
        return radius;
    }
    
    public Circle getCircle()
    {
        return circle;
    }
    
    @Override
    public void update(double dt){
        super.update(dt);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }
    
}
