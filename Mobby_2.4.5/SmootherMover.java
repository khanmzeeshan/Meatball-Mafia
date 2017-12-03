import greenfoot.*;
/**
 * Write a description of class SmootherMover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public abstract class SmootherMover extends Actor
{
     private Vector speed = new Vector();
    
    private double x = 0;
    private double y = 0;
    
    public SmootherMover()
    {
    }
    
    /**
     * Create new thing initialised with given speed.
     */
    public SmootherMover(Vector speed)
    {
        this.speed = speed;
    }
    
    /**
     * Move forward one step.
     */
    public void move() 
    {
        x = x + speed.getX();
        y = y + speed.getY();
        
        if (outOfWorld()) {
           getWorld().removeObject(this);
        }
        else {
            setLocation(x, y);
        }
    }
    
    private boolean outOfWorld()
    {
        return    (x >= getWorld().getWidth())
               || (x < 0) 
               || (y >= getWorld().getHeight())
               || (y < 0) ;
    }
    
    /**
     * Providing 'setLocation' based on doubles. This will eventually call
     * the built-in 'setLocation' method in Actor.
     */
    public void setLocation(double x, double y) 
    {
        this.x = x;
        this.y = y;
        super.setLocation((int) x, (int) y);
    }
    
    /**
     * Override the default 'setLocation' method to keep our own coordinates
     * up-to-date.
     */
    public void setLocation(int x, int y) 
    {
        setLocation((double) x, (double) y);
    }

    /**
     * Increase the speed with the given vector.
     */
    public void increaseSpeed(Vector s) 
    {
        speed.add(s);
    }
    
    /**
     * Return the current speed.
     */
    public Vector getSpeed() 
    {
        return speed;
    }
}