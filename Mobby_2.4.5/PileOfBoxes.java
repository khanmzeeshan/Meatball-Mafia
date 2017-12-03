import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class ButtonDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PileOfBoxes extends ScrollingActor
{
    int crack; //increases when boxes are hit, cracks once then removes boxes
    private static final int NUM_FRAGMENTS = 60;
    private GreenfootSound boxHit = new GreenfootSound("singleBoxHit.mp3");
    private GreenfootSound boxCrash = new GreenfootSound("boxesBreak.mp3");
    
    public void act() 
    {
        breakApart();
        if (crack < 2) block();
    }    
    
    public void block()
    {
        List<Meatball> meatball = getIntersectingObjects(Meatball.class);
        List<Bullet> bullet = getIntersectingObjects(Bullet.class);
        if (!bullet.isEmpty()) getWorld().removeObject(bullet.get(0));
    }
    
    public void breakApart()
    {
        Meatball meatball = (Meatball) getOneIntersectingObject(Meatball.class);
        if (meatball != null) {
            getWorld().removeObject(meatball);
            boxHit.play();
            crack++;
        }
        if (crack == 1) setImage("boxes_stage2_scaled.png");
        if (crack == 2) {
            explode();
            boxCrash.play();
            getWorld().removeObject(this);
            return;
        }
    }
    public void explode()
    {
        placeDebris(getX(), getY(), NUM_FRAGMENTS);
        //getWorld().removeObjects(this);
    }
    private void placeDebris(int x, int y, int numFragments)
    {
        for (int i = 0; i < numFragments; i++)
        {
            getWorld().addObject(new BoxDebris(), x, y);
        }
    }
}
