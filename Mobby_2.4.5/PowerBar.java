import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class PowerBar here.
 * 
 * @author Dane Reimers
 * @version (a version number or a date)
 */
public class PowerBar extends SmoothMover
{
    public void act() 
    {
        GreenfootImage powerBar = new GreenfootImage(121, 21);
        powerBar.setColor(Color.RED);
        powerBar.drawRect(0, 0, 120, 20);
        powerBar.fillRect(0, 0, ((Mobby) getWorld().getObjects(Mobby.class).get(0)).power * 3, 30);
        setImage(powerBar);
    }    
}
