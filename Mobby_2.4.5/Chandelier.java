import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class Chandelier here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chandelier extends ScrollingActor
{
    boolean hit = false;
    
    private static final int NUM_FRAGMENTS = 1;
    
    GreenfootSound brokenGlass = new GreenfootSound("glassBreak.mp3");
    GreenfootSound hitGlass = new GreenfootSound("hitChandelier.mp3");
    
    private ChandelierHitbox chandelier;
    int cWidth = 200;
    int cHeight = 80;
    int cDX = 0;
    int cDY = 30;
    /**
     * Act - do whatever the Chandelier wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected void addedToWorld(World w) {
        chandelier = new ChandelierHitbox(this, cWidth, cHeight, cDX, cDY, false); 
        getWorld().addObject(chandelier, getX() + cDX, getY() + cDY);
    }
    public void checkHit()
    {
        List<Meatball> meatball = chandelier.getHitboxIntersections();
        if (!meatball.isEmpty()) hit = true;
        if (hit == true) {
            getWorld().removeObject(meatball.get(0));
            dropCrystal();
            hit = false;
        }
    }
    public void act() 
    {
        // Add your action code here.
        checkHit();
    }    
    public void dropCrystal()
    {
        placeDebris(getX(), getY(), NUM_FRAGMENTS);
        hitGlass.play();
        
    }
    private void placeDebris(int x, int y, int numFragments)
    {
        for (int i = 0; i < numFragments; i++)
        {
            getWorld().addObject(new GlassDebris(), x, y);
            /*bi = Greenfoot.getRandomNumber(2);
            if (bi == 1)
            {
                getWorld().addObject(new MeatballDebris(), x, y);
            }
            else
            {
                getWorld().addObject(new NoodleDebris(), x, y);
            }*/
        }
    } 
}
