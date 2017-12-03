import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructions extends World
{
    private GreenfootSound bkgMusic;
    public Instructions()
    {    
        super(1600, 700, 1, false); 
        bkgMusic = new GreenfootSound("Intro-Level0.mp3");
        bkgMusic.playLoop();
    }

    public void act(){
        if (Greenfoot.mouseClicked(this)) {
            bkgMusic.stop();
            Level0 level0 = new Level0();
            Greenfoot.setWorld(level0);
        }
    }
}
