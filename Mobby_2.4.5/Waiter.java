import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Waiter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Waiter extends ScrollingActor
{
    private static final int NUM_FRAGMENTS = 100;
    
    //vars for mobster's hitbox
    private MobsterHitbox mbh;
    int mbhWidth = 170;
    int mbhHeight = 95;
    int mbhDX = 0;
    int mbhDY = -20;
    
    //vars that affect mobster's power
    int mobbyDist = 500; //distance mobster checks to start shooting
    int size = 0; //mobster's current size
    
    //images
    GreenfootImage F1R = new GreenfootImage("Waiter-F1R.png");
    GreenfootImage F2R = new GreenfootImage("Waiter-F2R.png");
    GreenfootImage F1L = new GreenfootImage("Waiter-F1L.png");
    GreenfootImage F2L = new GreenfootImage("Waiter-F2L.png");
    
    //vars for jumping
    int groundLevel;
    boolean onGround;
    private int ySpeed;
    private int apexTimer;
    boolean isJumping;
    
    //vars for walking
    private int walkTimer = 50;
    private int walkingDist;
    private double runningFrameTime = 0;
    int speed = 4;
    int runningFrameSpeed = 18;
    private boolean changeDirection;
    public boolean wL = true;
    public boolean wR = false;
    int run = 0;
    
    //vars for shooting
    int shotTimer = 140;
    int shotTime = 140;
    
    boolean hit = false; //checks if mobster has been hit by meatball
    int bi;
    
    // Sounds
    private GreenfootSound plateThrow = new GreenfootSound("PlateThrow.mp3");
    private GreenfootSound eat = new GreenfootSound("biteSound.mp3");
    private GreenfootSound enemyPop = new GreenfootSound("enemyExplosion.mp3");

    protected void addedToWorld(World w) {
        mbh = new MobsterHitbox(this, mbhWidth, mbhHeight, mbhDX, mbhDY, false);
        getWorld().addObject(mbh, getX() + mbhDX, getY() + mbhDY);
    }
    
    public void act() 
    {
        gravity();
        checkMobby();
        checkHit();
    }    
    
    public void checkMobby()
    {
        List<Mobby> list;  // takes the actor Mobby into the list 
        list = getObjectsInRange(mobbyDist, Mobby.class);
        if (!list.isEmpty())
        {
            shoot();
        }
        if (list.isEmpty())
        {
            pace();
        }
    }
    
    public void walkRight()
    {
        move(speed);
        if (runningFrameTime == runningFrameSpeed) runningFrameTime = 0;            
        if (runningFrameTime == 0) {
                setImage(F1R);
        }          
        if (runningFrameTime == runningFrameSpeed / 2) {
                setImage(F2R);
        }
        runningFrameTime++;   
    }
    public void walkLeft()
    {
        move(-speed);
        if (runningFrameTime == runningFrameSpeed) runningFrameTime = 0;            
        if (runningFrameTime == 0) {
                setImage(F1L);
        }          
        if (runningFrameTime == runningFrameSpeed / 2) {
                setImage(F2L);
        }
        runningFrameTime++;   
    }
    
    public void pace()
    {
        if (walkTimer != 0 && wL == true)
        {
            walkLeft();
            walkTimer--;
            if (walkTimer == 0)
            {
                getWorld().removeObject(mbh);
                mbh = new MobsterHitbox(this, mbhWidth, mbhHeight, -mbhDX, mbhDY, false);
                getWorld().addObject(mbh, getX() + mbhDX, getY() + mbhDY);
                walkTimer = 50;
                runningFrameTime = 0;
                wR = true;
                wL = false;
            }
        }
        if (walkTimer != 0 && wR == true)
        {
            walkRight();
            walkTimer--;
            if (walkTimer == 0)
            {
                getWorld().removeObject(mbh);
                mbh = new MobsterHitbox(this, mbhWidth, mbhHeight, mbhDX, mbhDY, false);
                getWorld().addObject(mbh, getX() + mbhDX, getY() + mbhDY);
                walkTimer = 50;
                runningFrameTime = 0;
                wR = false;
                wL = true;
            }
        }
    }
    
    public void gravity()
    {
        groundLevel = getWorld().getHeight() - getImage().getHeight()/2 - 50;
        onGround = (getY() == groundLevel);
        if (!onGround)  // mid jump
        {
            if (ySpeed == 0 && apexTimer > 0) apexTimer--; // run apex timer
            if (ySpeed == 0 && apexTimer > 0) return; // apex timer still running
            ySpeed++; // adds gravity effect
            setLocation(getX(), getY()+ ySpeed); // fall (rising sloaer or falling faster)
            
            if (getY()>=groundLevel) // has landed (reached ground level)
            {
                setLocation(getX(), groundLevel); // set on ground
                isJumping = false;
                //Greenfoot.getKey(); // clears any key pressed during jump
            }
        }
    }
    
    public void jump()
    {
        if (Greenfoot.getRandomNumber(1000) < 20 && !isJumping) 
        {
            ySpeed = -25;
            setLocation(getX(), getY() + ySpeed);
            apexTimer = 2;
            isJumping = true;
        }
    }
    
    public void checkHit()
    {
        List<Meatball> meatball = mbh.getHitboxIntersections();
        if (!meatball.isEmpty()) hit = true;
        if (hit == true) {
            mobbyDist += 250;
            getWorld().removeObject(meatball.get(0));
            getWorld().removeObject(mbh);
            mbhWidth += 20;
            mbhHeight += 10;
            mbhDY -= 2;
            mbh = new MobsterHitbox(this, mbhWidth, mbhHeight, mbhDX, mbhDY, false);
            getWorld().addObject(mbh, getX() + mbhDX, getY() + mbhDY);
            F1R.scale( F1R.getWidth() + 30 , F1R.getHeight() + 30 );
            F2R.scale( F2R.getWidth() + 30 , F2R.getHeight() + 30 );
            F1L.scale( F1L.getWidth() + 30 , F1L.getHeight() + 30 );
            F2L.scale( F2L.getWidth() + 30 , F2L.getHeight() + 30 );
            shotTime -= 15; //adjusts the time between shots so mobster shoots more rapidly
            shotTimer -= 15;
            size++;
            if (size < 4)
            {
                eat.play();
            }
            if (size >= 4) 
            {
                    Level2 w = (Level2) getWorld();
                    explode();
                    w.removeObject(this);
            }
            hit = false;
        }
    }
    
    public void shoot()
    {
        Actor mobby = (Actor)getWorld().getObjects(Mobby.class).get(0);
        if (mobby.getX() < getX())
        {
            if (shotTimer == shotTime - 10) setImage(F1L);
            World w = getWorld();
            Plate plate = new Plate(size, -1);
            shotTimer--;
            if (shotTimer <= 0)
            {
                setImage(F2L);
                w.addObject(plate, getX() - 110, getY() + 90 + size*6);
                plateThrow.play();
                plate.setRotation(180);
                shotTimer = shotTime;
            }
        }
        if (mobby.getX() > getX())
        {
            if (shotTimer == shotTime - 10) setImage(F1R);
            World w = getWorld();
            Plate plate = new Plate(size, 1);
            shotTimer--;
            if (shotTimer <= 0)
            {
                setImage(F2R);
                w.addObject(plate, getX() + 110, getY() + 90 + size*6);
                plateThrow.play();
                shotTimer = shotTime;
            }
        }
    }
    public void explode()
    {
        placeDebris(getX(), getY(), NUM_FRAGMENTS);
        enemyPop.play();
    }
    private void placeDebris(int x, int y, int numFragments)
    {
        for (int i = 0; i < numFragments; i++)
        {
            bi = Greenfoot.getRandomNumber(2);
            if (bi == 1)
            {
                getWorld().addObject(new MeatballDebris(), x, y);
            }
            else
            {
                getWorld().addObject(new NoodleDebris(), x, y);
            }
        }
    }
}
