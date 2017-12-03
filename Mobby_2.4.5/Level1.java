import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Level1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level1 extends ScrollingWorld
{
    Mobby mobby;
    PowerBar powerBar;
    HealthBar healthBar;
    public double power;
    public boolean resetPower;
    public boolean returning;
    public GreenfootSound bkgMusic;
    
    public Level1(boolean returningFromLVL2)
    {    
        super("kitchen3times.png", 7600);
        returning = returningFromLVL2;

        mobby = new Mobby();
        powerBar = new PowerBar();
        healthBar = new HealthBar();
        bkgMusic = new GreenfootSound("Level 1.mp3");
        shiftWorld(0);
        prepare();
        bkgMusic.playLoop();
        
    }

    private void prepare()
    {
        if (!returning) {
            addObject(mobby, getWidth() / 2, getHeight() - 240/2 - 50);
            addObject(powerBar, getWidth() / 2, 200);
            addObject(healthBar, getWidth() / 2, 100);
            PileOfBoxes pileOfBoxes0 = new PileOfBoxes();
            addObject(pileOfBoxes0, 2275, 415);
            PileOfBoxes pileOfBoxes1 = new PileOfBoxes();
            addObject(pileOfBoxes1, 4505, 415);
            Chef Chef0 = new Chef();
            addObject(Chef0,3400,365);
            Chef Chef1 = new Chef();
            addObject(Chef1,3900,365);
            Chef Chef2 = new Chef();
            addObject(Chef2,4300,365);
            Chef Chef3 = new Chef();
            addObject(Chef3,5000,365);
            Chef Chef4 = new Chef();
            addObject(Chef4,5500,365);
            Chef Chef5 = new Chef();
            addObject(Chef5,5800,365);
        }
        if (returning) {
            shiftWorld(-5850);
            addObject(mobby, 800, getHeight() - 240/2 - 50);
            addObject(powerBar, getWidth() / 2, 200);
            addObject(healthBar, getWidth() / 2, 100);
        }
    }

    public void act()
    {
        healthBar.setLocation(mobby.getX(), mobby.getY() - 175);
        powerBar.setLocation(mobby.getX(), mobby.getY() - 150);
        if (xOffset <= -5868) {
            Level2 level2 = new Level2(false);
            Greenfoot.setWorld(level2);
            bkgMusic.stop();
        }
    }
}
