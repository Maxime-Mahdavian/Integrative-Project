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
public class ShapeObject {
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D acceleration;
    
    public ShapeObject(Vector2D position, Vector2D velocity, Vector2D acceleration)
    {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }
    
    public Vector2D getPosition()
    {
        return position;
    }
    public Vector2D getVelocity()
    {
        return velocity;
    }
    public Vector2D getAcceleration()
    {
        return acceleration;
    }
    
    public void setPosition(Vector2D position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }
    
    public void setPosition(double x, double y)
    {
        position.setX(x);
        position.setY(y);
            
    }
    
    public void setVelocity(Vector2D velocity){
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
    }
    
    public void setVelocity(double x, double y){
        velocity.setX(x);
        velocity.setY(y);
    }
    
    public void setAcceleration(Vector2D acceleration){
        this.acceleration.setX(acceleration.getX());
        this.acceleration.setY(acceleration.getY());
    }
    
    public void setAcceleration(double x, double y){
        acceleration.setX(x);
        acceleration.setY(y);
    }
    
    public void update(double dt)
    {
        // Update position
        velocity = velocity.add(acceleration.mul(dt));
        position = position.add(velocity.mul(dt));
        
    }
}
