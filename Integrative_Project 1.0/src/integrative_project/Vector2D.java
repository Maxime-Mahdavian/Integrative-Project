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
public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Accessors and Mutators
    public double getX(){
        return x;
    }
     
    public double getY(){
        return y;
    }
    
    public void  setX(double value){
        x = value;
    }
    public void  setY(double value){
        y = value;
    }
    
    public void setVector(Vector2D newVector){
        x = newVector.getX();
        y = newVector.getY();
    }
    
    // Operations
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D mul(Vector2D other) {
        return new Vector2D(x * other.x, y * other.y);
    }
    
    public Vector2D mul(double multiplier) {
        return new Vector2D(x * multiplier, y * multiplier);
    }
    
    public double magnitude()
    {
        return Math.sqrt(x*x + y*y);        
    }

    public void setMagnitude(double m)
    {
        double original = magnitude();
        double ratio = m / original;
        
        x *= ratio;
        y *= ratio;
    }
    
    public static double distance(Vector2D v1, Vector2D v2)
    {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
