import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Level0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level0 extends ScrollingWorld
{
    Mobby mobby;
    PowerBar powerBar;
    HealthBar healthBar;
    private GreenfootSound bkgMusic;
    public Level0()
    {    
        super("lvl0.png", 4000);

        mobby = new Mobby();
        powerBar = new PowerBar();
        healthBar = new HealthBar();
        bkgMusic = new GreenfootSound("Intro-Level0.mp3");
        shiftWorld(0);
        prepare();
        bkgMusic.playLoop();
    }

    private void prepare()
    {       
        addObject(mobby, 800, getHeight() - 240/2 - 50);
        addObject(powerBar, getWidth() / 2, 200);
        addObject(healthBar, getWidth() / 2, 100);
        ButtonDoor buttonDoor = new ButtonDoor();
        addObject(buttonDoor, 1500, getHeight() / 2);
    }

    public void act()
    {
        healthBar.setLocation(mobby.getX(), mobby.getY() - 175);
        powerBar.setLocation(mobby.getX(), mobby.getY() - 150);
        if (xOffset <= -1000) {
            Level1 level1 = new Level1(false);
            Greenfoot.setWorld(level1);
            bkgMusic.stop();
        }
    }
}
