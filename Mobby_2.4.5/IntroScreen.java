import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class IntroScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroScreen extends World
{
    public int meatSpeed = 5; 
    private GreenfootSound bkgMusic;
    public IntroScreen()
    {    
        super(1600, 700, 1, false); 
        bkgMusic = new GreenfootSound("Intro-Level0.mp3");
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            bkgMusic.stop();
            Backstory backstory = new Backstory();
            Greenfoot.setWorld(backstory);
        }
        if( Greenfoot.getRandomNumber (100) < 40) {
            MeatIntro m = new MeatIntro();
            m.setSpeed(meatSpeed);
            addObject (m, Greenfoot.getRandomNumber(getWidth()-20)+10, -30);
        }
        
    }

    public void started()
    {
        bkgMusic.playLoop();
    }
}
