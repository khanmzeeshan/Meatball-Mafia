import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends SmoothMover
{
    public void act() 
    {
        GreenfootImage powerBar = new GreenfootImage(201, 21);
        powerBar.setColor(Color.GREEN);
        powerBar.drawRect(0, 0, 200, 20);
        List<Mobby> mobby = getObjectsInRange(5000, Mobby.class);
        powerBar.fillRect(0, 0, mobby.get(0).getHealth(), 20);
        setImage(powerBar);
    }    
}
