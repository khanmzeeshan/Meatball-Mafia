import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelBoss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelBoss extends ScrollingWorld
{

    /**
     * Constructor for objects of class LevelBoss.
     * 
     */
    Mobby mobby;
    PowerBar powerBar;
    HealthBar healthBar;

    public double power;
    public boolean resetPower;

    public GreenfootSound bkgMusic;

    public LevelBoss()
    {    
        super("bosslvl.png", 16833);

        mobby = new Mobby();
        powerBar = new PowerBar();
        healthBar = new HealthBar();
        bkgMusic = new GreenfootSound("BossLevel.mp3");
        shiftWorld(0);
        bkgMusic.playLoop();
        prepare();
    }

    public void prepare()
    {
        addObject(mobby, 250, getHeight() - 240/2 - 50);
        addObject(powerBar, getWidth() / 2, 200);
        addObject(healthBar, getWidth() / 2, 100);
        Chandelier ch = new Chandelier();
        addObject(ch, 7300, 100);
        Boss b = new Boss();
        addObject(b, 7000, getHeight() - 250);
    }

    public void act()
    {
        healthBar.setLocation(mobby.getX(), mobby.getY() - 175);
        powerBar.setLocation(mobby.getX(), mobby.getY() - 150); 
    }

    public void stop(){
        bkgMusic.stop();
    }
}
