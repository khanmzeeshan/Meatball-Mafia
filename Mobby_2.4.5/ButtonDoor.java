import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class ButtonDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonDoor extends ScrollingActor
{
    int dX = -36;
    int dY = -302;
    private ButtonDoorHitbox bdhb;
    
    boolean open = false;
    
    protected void addedToWorld(World w) {
        bdhb = new ButtonDoorHitbox(this, 28, 47, dX, dY, false);
        w.addObject(bdhb, getX() + dX, getY() + dY);
    }
    
    public void act() 
    {
        open();
        if (!open) block();
    }    
    
    public void block()
    {
        List<Meatball> meatball = getIntersectingObjects(Meatball.class);
        List<Bullet> bullet = getIntersectingObjects(Bullet.class);
        if (!bullet.isEmpty()) getWorld().removeObject(bullet.get(0));
        if (!meatball.isEmpty()) getWorld().removeObject(meatball.get(0));
    }
    
    public void open()
    {
        List<Meatball> meatball = bdhb.getHitboxIntersections();
        if (!meatball.isEmpty()) {
            open = true;
            getWorld().removeObject(meatball.get(0));
        }
        if (open) {
            setLocation( getX(), getY() + 3);
            if (getY() >= 1050) {
                getWorld().removeObject(this);
                return;
            }
        }
    }  
}
