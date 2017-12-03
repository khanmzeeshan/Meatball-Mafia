import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class ElevatorButtonHitbox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElevatorButton extends ScrollingActor
{
    boolean set = true;
    boolean visible = false;
    
    public void act()
    {
        if (set = true) {
            GreenfootImage elevatorButton = new GreenfootImage(33, 55);
            if (visible) {
                elevatorButton.setColor(Color.RED);
                elevatorButton.setTransparency(100);  //0-255
                elevatorButton.fill();
            }
            setImage(elevatorButton);
            set = false;
        }
    }
    
    public boolean checkHit()
    {
        if (getOneIntersectingObject(Meatball.class) != null) {
            return true;
        }
        else return false;
    }
}
