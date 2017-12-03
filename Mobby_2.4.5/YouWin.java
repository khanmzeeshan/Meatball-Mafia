import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class YouWin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class YouWin extends World
{

    private GreenfootSound bkgMusic;
    public YouWin()
    {    
        super(1600, 700, 1); 
        bkgMusic = new GreenfootSound("Win.mp3");
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
