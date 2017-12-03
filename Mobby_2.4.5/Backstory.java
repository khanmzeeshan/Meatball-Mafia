import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Backstory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Backstory extends World
{
    private GreenfootSound bkgMusic;
    
    public Backstory()
    {    
        super(1600, 700, 1); 
        bkgMusic = new GreenfootSound("Intro-Level0.mp3");
        bkgMusic.playLoop();
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            bkgMusic.stop();
            Instructions instructions = new Instructions();
            Greenfoot.setWorld(instructions);
        }
    }
}
