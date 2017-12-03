import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends ScrollingActor
{
    public int speed = 15;
    
    public Bullet(int size)
    {
        GreenfootImage image = getImage();
        image.scale(15 + size * 3,15 + size * 3);
        setImage(image);
    }
    public void act() 
    {
        move(5);
        checkRemove();
    }    
    public void checkRemove()
    {
        World w = getWorld();
        if (getY() > w.getHeight() || getX() > w.getWidth())
        {
            w.removeObject(this);
        }
    }
}
