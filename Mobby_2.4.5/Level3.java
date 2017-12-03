import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Level3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level3 extends ScrollingWorld
{
    Mobby mobby;
    PowerBar powerBar;
    HealthBar healthBar;

    public double power;
    public boolean resetPower;

    boolean newLeftMobster0 = true; //spawns new mobsters once Mobby has reached a certain point in the level
    boolean newLeftMobster1 = true;
    boolean newLeftMobster2 = true;
    boolean newLeftMobster3 = true;
    boolean newLeftMobster4 = true;
    boolean newLeftMobster5 = true;

    public GreenfootSound bkgMusic;

    public Level3()
    {    
        super("lvl3.png", 16833);

        mobby = new Mobby();
        powerBar = new PowerBar();
        healthBar = new HealthBar();
        bkgMusic = new GreenfootSound("Level 3.mp3");
        shiftWorld(0);
        prepare();
        bkgMusic.playLoop();
    }

    private void prepare()
    {
        addObject(mobby, 250, getHeight() - 240/2 - 50);
        addObject(powerBar, getWidth() / 2, 200);
        addObject(healthBar, getWidth() / 2, 100);
        ElevatorButton elevatorButton = new ElevatorButton();
        addObject(elevatorButton, 450, 438);

        Mobster mobster0 = new Mobster();
        addObject(mobster0,3300,365);
        Mobster mobster1 = new Mobster();
        addObject(mobster1,4800,365);
        Mobster mobster3 = new Mobster();
        addObject(mobster3,7800,365);
        Mobster mobster4 = new Mobster();
        addObject(mobster4, 9300, 365);  
        Mobster mobster5 = new Mobster();
        addObject(mobster5, 10800, 365);
        Mobster mobster6 = new Mobster();
        addObject(mobster6, 12800, 365);
        Mobster mobster7 = new Mobster();
        addObject(mobster7, 14000, 365);
    }

    public void act()
    {
        healthBar.setLocation(mobby.getX(), mobby.getY() - 175);
        powerBar.setLocation(mobby.getX(), mobby.getY() - 150); 

        List<ElevatorButton> eB = getObjects(ElevatorButton.class);

        //either returns mobby to level 2 if elevator button is hit or returns to intro screen if end is reached
        if (eB.get(0).checkHit()) {
            Level3 level3 = new Level3();
            Greenfoot.setWorld(level3);
        }
        if (xOffset <= -15228) {
            LevelBoss levelboss = new LevelBoss();
            Greenfoot.setWorld(levelboss);
            stop();
        }

        if (xOffset <= -4401 && newLeftMobster2)
        {
            LeftMobster leftMobster2 = new LeftMobster();
            addObject(leftMobster2, 50,365);
            newLeftMobster2 = false;
        }
        if (xOffset <= -7614 && newLeftMobster4)
        {
            LeftMobster leftMobster4 = new LeftMobster();
            addObject(leftMobster4, 50,365);
            newLeftMobster4 = false;
        }
        if (xOffset <= -10784 && newLeftMobster5)
        {
            LeftMobster leftMobster5 = new LeftMobster();
            addObject(leftMobster5, 50,365);
            newLeftMobster5 = false;
        }
        if (xOffset <= -14280 && newLeftMobster5)
        {
            LeftMobster leftMobster5 = new LeftMobster();
            addObject(leftMobster5, 50,365);
            newLeftMobster5 = false;
        }
    }

    public void stop(){
        bkgMusic.stop();
    }
}
