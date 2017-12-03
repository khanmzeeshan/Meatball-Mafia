import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Plate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plate extends ScrollingActor
{
    /**
     * Act - do whatever the Plate wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int rollDirection;
    private static final int NUM_FRAGMENTS = 60;
    private GreenfootSound plateCrash = new GreenfootSound("PlateCrash.mp3");
    public boolean broken = false;
    
    public Plate(int size, int direction)
    {
        GreenfootImage image = getImage();
        image.scale(87 + size * 12, 87 + size * 12);
        setImage(image);
        rollDirection = direction;
    }
    public void act() 
    {
        setLocation(getX() + 5*rollDirection, getY());
        turn(5*rollDirection);
        checkMeatball();
        if (!broken) checkRemove();
    }    
    public void checkRemove()
    {
        World w = getWorld();
        if (getY() > w.getHeight() || getX() > w.getWidth())
        {
            w.removeObject(this);
        }
    }
    public void checkMeatball()
    {
        Meatball meatball = (Meatball) getOneIntersectingObject(Meatball.class);
        if (meatball != null && !meatball.stopped)
        {
            broken = true;
            plateCrash.play();
            explode();
            getWorld().removeObject(this);
        }
    }
    public void explode()
    {
        placeDebris(getX(), getY(), NUM_FRAGMENTS);
    }
    private void placeDebris(int x, int y, int numFragments)
    {
        for (int i = 0; i < numFragments; i++)
        {
            getWorld().addObject(new PlateDebris(), x, y);
        }
    }
}
