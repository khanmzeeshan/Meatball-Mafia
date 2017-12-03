import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonDoorHitbox extends ScrollingActor
{
    GreenfootImage body;
    int offsetX;
    int offsetY;
    Actor host;
    
    public ButtonDoorHitbox(Actor a, int w, int h, int dx, int dy, boolean visible)
    {
        host = a;
        offsetX = dx;
        offsetY = dy;
        body = new GreenfootImage(w, h);
        if (visible) {
            body.setColor(Color.GREEN);
            body.setTransparency(100);  //0-255
            body.fill();
        }
        setImage(body);
    }
    
    public void act() 
    {
        if (host.getWorld() != null) {
            setLocation(host.getX() + offsetX, host.getY() + offsetY);
        }
        else {
            getWorld().removeObject(this);
        }
    }    
    
    public List getHitboxIntersections() {
        return getIntersectingObjects(Meatball.class);
    }
}
