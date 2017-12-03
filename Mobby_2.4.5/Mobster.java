import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Mobster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mobster extends ScrollingActor
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
    GreenfootImage F1R = new GreenfootImage("enemy0 - F1R.png");
    GreenfootImage F2R = new GreenfootImage("enemy1 - F2R.png");
    GreenfootImage F1L = new GreenfootImage("enemy0.1 - F1L.png");
    GreenfootImage F2L = new GreenfootImage("enemy1.1 - F2L.png");
    
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
    int shotTimer = 120;
    int shotTime = 120;
    
    boolean hit = false; //checks if mobster has been hit by meatball
    int bi;
    
    // Sounds
    private GreenfootSound gunshot = new GreenfootSound("gunshot.mp3");
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
                Level3 w = (Level3) getWorld();
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
            setImage(F1L);
            World w = getWorld();
            Bullet bullet = new Bullet(size);
            shotTimer--;
            if (shotTimer <= 0)
            {
                w.addObject(bullet, getX() - 125, getY() + 55 + size*6);
                gunshot.play();
                bullet.setRotation(180);
                shotTimer = shotTime;
            }
        }
        if (mobby.getX() > getX())
        {
            setImage(F1R);
            Level3 w = (Level3) getWorld();
            Bullet bullet = new Bullet(size);
            shotTimer--;
            if (shotTimer <= 0)
            {
                w.addObject(bullet, getX() + 100, getY() + 55 + size*6);
                gunshot.play();
                shotTimer = shotTime;
            }            
        }
    }
    public void explode()
    {
        placeDebris(getX(), getY(), NUM_FRAGMENTS);
        enemyPop.play();
        //getWorld().removeObjects(this);
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
