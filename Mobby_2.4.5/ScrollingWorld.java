import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class ScrollingWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingWorld extends World
{
    public int xOffset = 0;
    private int WWIDTH;
    private GreenfootImage bimg;
    
    public double power;
    public boolean resetPower;
    
    public ScrollingWorld(String s, int wWIDTH)
    {    
        super(1600, 700, 1, false);
        bimg = new GreenfootImage(s);
        WWIDTH = wWIDTH;
    }

    public void shiftWorld(int dx)
    {
        if (((xOffset + dx) <= 0 && (xOffset + dx) >= 1600 - WWIDTH))
        {
            xOffset = xOffset + dx;
            shiftWorldBackground(dx);
            shiftWorldActors(dx);
        }
    }

    private void shiftWorldBackground(int dx)
    {
        GreenfootImage bkgd = new GreenfootImage(1600, 700);
        bkgd.drawImage(bimg, xOffset, 0);
        setBackground(bkgd);
    }

    private void shiftWorldActors(int dx)
    {
        List<ScrollingActor> saList;
        saList = getObjects(ScrollingActor.class);
        for( ScrollingActor a : saList)
        {
            a.setAbsoluteLocation(dx);
        }
    }
    
    public void act()
    {
        meatballPower();
    }
    
    public void meatballPower()
    {
        if (Greenfoot.isKeyDown("f")) {
            power++;
            resetPower = false;
        }
        else {
            if (resetPower) power = 0;
        }
    }
}
