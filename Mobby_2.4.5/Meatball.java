import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Meatball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Meatball extends ScrollingActor
{
    //vars for direction of meatball
    boolean Check = true;
    double direction = 1;
    
    public double power; //power of meatball
    private int vSpeed = 30; //initial vertical speed of meatball
    private int timer = 0; //timer to remove meatball after a little while
    
    public boolean stopped = false;
    
    public void act() 
    {
        if (Check == true) {
            getDirection();
            getPower();
            resetPower();
        }
        arc();
        remove();
        
    }
    
    public void getDirection() //gets direction Mobby is facing
    {
            direction = ((Mobby) getWorld().getObjects(Mobby.class).get(0)).getDirection();
    }
    
    public void getPower()
    {
            power = ((Mobby) getWorld().getObjects(Mobby.class).get(0)).power;
    }
    
    public void resetPower()
    {
        ((Mobby) getWorld().getObjects(Mobby.class).get(0)).resetPower = true;
        Check = false; //makes sure direction is only checked once so meatball flies in correct direction
    }
    
    public void arc() //sets the path of the flight of the meatball
    {       
        World w = getWorld();
        if( getY() <= w.getHeight() - 80) {
            setLocation(getX() + power*direction, getY() - (power/2) - vSpeed);
            vSpeed = vSpeed - 3;
            GreenfootImage image = getImage();
            image.rotate(5);
            setImage(image);
        }   
        else {
            setLocation(getX(), getY());
            stopped = true;
        }
    }
    
    public void remove() //removes meatball after time period
    {
        timer++;
        if (timer >= 200) {
            getWorld().removeObject(this);
        }
    }

}
