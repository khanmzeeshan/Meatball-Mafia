import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Boss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boss extends ScrollingActor
{
    /**
     * Act - do whatever the Boss wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int health = 100;
    int speed = 4;
    int mobbyDist = 1000;
    boolean changeDir;
    private int timer;
    private static final int TIMER = 130;
    boolean dead = false;

    int size = 5;
    private double runningFrameTime = 0;
    int runningFrameSpeed = 18;

    private GreenfootSound grunt = new GreenfootSound("grunt.mp3");
    private GreenfootSound gunshot = new GreenfootSound("gunshot.mp3");
    private GreenfootSound eat = new GreenfootSound("biteSound.mp3");

    GreenfootImage Img2 = new GreenfootImage("boss0.png");
    GreenfootImage Img1 = new GreenfootImage("boss1.png");

    public Boss()
    {
        timer = TIMER;
    }

    public void act() 
    {
        // Add your action code here.
        checkHit();
        pace();
        if (!dead) checkMobby();
    }    

    public void checkMobby()
    {
        List<Mobby> list;  // takes the actor Mobby into the list 
        list = getObjectsInRange(mobbyDist, Mobby.class);
        if (!list.isEmpty())
        {
            shoot();
        }
    }

    public void checkHit()
    {
        GlassDebris glassHit = (GlassDebris) getOneIntersectingObject(GlassDebris.class);
        Meatball meatball = (Meatball) getOneIntersectingObject(Meatball.class);
        if (glassHit != null)
        {
            getWorld().removeObject(glassHit);
            grunt.play();
            Img1.scale( Img1.getWidth() - 20 , Img1.getHeight() - 20 );
            Img2.scale( Img2.getWidth() - 20 , Img2.getHeight() - 20 );
            setLocation( getX(), getY() + 10);
            size--;
            if (size == 0)
            {
                LevelBoss w = (LevelBoss) getWorld();
                w.stop();
                dead = true;
                getWorld().removeObject(this);
                YouWin win = new YouWin();
                Greenfoot.setWorld(win);
            }
        }
        if (meatball != null) 
        {
            eat.play();
            getWorld().removeObject(meatball);
            if (size < 5) {
                Img1.scale( Img1.getWidth() + 20 , Img1.getHeight() + 20 );
                Img2.scale( Img2.getWidth() + 20 , Img2.getHeight() + 20 );
                setLocation( getX(), getY() - 10);
                size++;
            }
            else {
                PoopMeatball pM = new PoopMeatball();
                getWorld().addObject(pM, getX() + 80, getY() + 80);
            }
        }
    }

    public void pace()
    {
        if (timer != 0)
        {
            move(speed);
            if (runningFrameTime == runningFrameSpeed)
            {
                runningFrameTime = 0;
            }
            if (runningFrameTime == 0) {
                setImage(Img1);
            }          
            if (runningFrameTime == runningFrameSpeed / 2) {
                setImage(Img2);
            }
            runningFrameTime++;
            timer--;
        }
        if (timer == 0)
        {
            speed = speed * -1;
            timer = TIMER;
        }
    }
    int shotTimer = 120;
    int shotTime = 120;
    public void shoot()
    {
        Bullet bullet = new Bullet(size);
        shotTimer--;
        if (shotTimer <= 0)
        {
            getWorld().addObject(bullet, getX() - 135, getY() + 108 + size*6);
            gunshot.play();
            bullet.setRotation(180);
            shotTimer = shotTime;
        }
    }
}
