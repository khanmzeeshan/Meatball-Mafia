import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GlassDebris here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GlassDebris extends SmootherMover
{
    private static final Vector GRAVITY = new Vector(90,1.5);
    private static final int FORCE = 15;
    
    public GlassDebris()
    {
        int direction = Greenfoot.getRandomNumber(360);
        int speed = Greenfoot.getRandomNumber(FORCE);
        increaseSpeed(new Vector(direction, speed));
        
        // random image size
        GreenfootImage img = getImage();
        //int width = Greenfoot.getRandomNumber(30) + 1;
        //int height = Greenfoot.getRandomNumber(30) + 1;
        img.scale (60, 60);
        
        setRotation (Greenfoot.getRandomNumber(360));
        
    }
    
    /**
     * Act - do whatever the NoodleDebris wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        increaseSpeed(GRAVITY);
        move();
    }      
}
