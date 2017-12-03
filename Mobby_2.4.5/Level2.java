import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Level2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level2 extends ScrollingWorld
{
    Mobby mobby;
    PowerBar powerBar;
    HealthBar healthBar;

    public double power;
    public boolean resetPower;

    boolean canReturn = false;  //allows mobby to return to level 1 if he has moved far enough from the entrance to level 2
    boolean returninglvl3; //detects if mobby is returning from level 3

    public GreenfootSound bkgMusic;

    public Level2(boolean returning)
    {    
        super("2level_high.png", 9500);
        returninglvl3 = returning;

        mobby = new Mobby();
        powerBar = new PowerBar();
        healthBar = new HealthBar();
        bkgMusic = new GreenfootSound("Level 2.mp3");
        shiftWorld(0);
        prepare();
        bkgMusic.playLoop();
    }

    private void prepare()
    {
        if (!returninglvl3) {
            addObject(mobby, 525, getHeight() - 240/2 - 50);
            addObject(powerBar, getWidth() / 2, 200);
            addObject(healthBar, getWidth() / 2, 100);
            ElevatorButton elevatorButton = new ElevatorButton();
            addObject(elevatorButton, 8715, 438);
            Waiter waiter0 = new Waiter();
            addObject(waiter0,3300,365);
            Waiter waiter1 = new Waiter();
            addObject(waiter1,4800,365);
            Waiter waiter2 = new Waiter();
            addObject(waiter2, 6300,365);
            Waiter waiter3 = new Waiter();
            addObject(waiter3,7800,365);   
        }
        else {
            addObject(mobby, 525, getHeight() - 240/2 - 50);
            addObject(powerBar, getWidth() / 2, 200);
            addObject(healthBar, getWidth() / 2, 100);
            ElevatorButton elevatorButton = new ElevatorButton();
            addObject(elevatorButton, 8715, 438);
        }
    }

    public void act()
    {
        healthBar.setLocation(mobby.getX(), mobby.getY() - 175);
        powerBar.setLocation(mobby.getX(), mobby.getY() - 150);  

        List<ElevatorButton> eB = getObjects(ElevatorButton.class);

        //either returns mobby to level 1 if he turns back or progresses to level 3 if elevator button is hit
        if (eB.get(0).checkHit()) {
            Level3 level3 = new Level3();
            Greenfoot.setWorld(level3);
            bkgMusic.stop();
        }
        if (xOffset < 0) canReturn = true;
        if (xOffset >= 0 && canReturn) {
            Level1 level1 = new Level1(true);
            Greenfoot.setWorld(level1);
            stop();
        }
    }

    public void stop(){
        bkgMusic.stop();
    }
}
