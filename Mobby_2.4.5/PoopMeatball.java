import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PoopMeatball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoopMeatball extends ScrollingActor
{
    /**
     * Act - do whatever the PoopMeatball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int power = 10;
    int vSpeed = 15;
    
    public void act() 
    {
        fly();
    }    
    
    public void fly()
    {
        World w = getWorld();
        if( getY() <= w.getHeight() - 80) {
            setLocation(getX() + power, getY() - (power/2) - vSpeed);
            vSpeed = vSpeed - 3;
            GreenfootImage image = getImage();
            image.rotate(5);
            setImage(image);
        }   
        else {
            setLocation(getX(), getY());
        }
    }
}
