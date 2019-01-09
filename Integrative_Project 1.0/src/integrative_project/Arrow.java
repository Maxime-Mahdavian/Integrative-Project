/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrative_project;

import javafx.scene.shape.Polygon;

/**
 *
 * @author Owner
 */
public class Arrow extends ShapeObject{
    private Polygon arrow;
    
    public Arrow(Vector2D position, Vector2D velocity, Vector2D acceleration, double width, double height){
        
        super(position, velocity, acceleration);
        arrow = new Polygon();
        arrow.getPoints().addAll(   position.getX(), position.getY(),
                                    position.getX() + height, position.getY() - height,
                                    position.getX() + height, position.getY() - height/2,
                                    position.getX() + height + width, position.getY() - height/2,
                                    position.getX() + height + width, position.getY() + height/2,
                                    position.getX() + height, position.getY() + height/2,
                                    position.getX() + height, position.getY() +  height);
    }
    
    @Override
    public void setPosition(double x, double y){
        super.setPosition(x, y);
         
    }
    
    public void setRotation(double angle){
        arrow.setRotate(angle);
    }
    
    public void mirror(){
        arrow.setRotate(180);
    }
    
    
    @Override
    public void update(double dt){
        super.update(dt);
        arrow.setLayoutX(position.getX());
        arrow.setLayoutY(position.getY());
    }
    
    public Polygon getArrow(){
        return arrow;
    }
}
