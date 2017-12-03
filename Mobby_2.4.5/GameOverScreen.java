import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class GameOverScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOverScreen extends World
{
    private GreenfootSound bkgMusic;
    public GameOverScreen()
    {    
        super(1600, 700, 1); 
        bkgMusic = new GreenfootSound("GameOver.mp3");
        bkgMusic.play();
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            IntroScreen world = new IntroScreen();
            Greenfoot.setWorld(world);
            bkgMusic.stop();
        }
    }
}
