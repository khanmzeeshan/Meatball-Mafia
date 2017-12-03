import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Chef here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chef extends ScrollingActor
{
    //vars for chef's hitbox
    private static final int NUM_FRAGMENTS = 100;
    
    private MobsterHitbox cbh;
    int cbhWidth = 170;
    int cbhHeight = 95;
    int cbhDX = 0;
    int cbhDY = -20;
    
    //vars that affect chef's power
    int mobbyDist = 180; //distance from Mobby chef has to be to start hitting
    int size = 0; //chef's current size
    int range = 250; //distance chef checks to attack Mobby (increases with size)
    int damage = 5; //amount of damage chef does per hit (increases with size)
    
    //images
    GreenfootImage F1R = new GreenfootImage("Butcher Chef-1.png");
    GreenfootImage F2R = new GreenfootImage("Butcher Chef-2.png");
    GreenfootImage F1L = new GreenfootImage("Butcher Chef-1.1.png");
    GreenfootImage F2L = new GreenfootImage("Butcher Chef-2.1.png");
    GreenfootImage F3R = new GreenfootImage("Butcher Chef-3.png");
    GreenfootImage F3L = new GreenfootImage("Butcher Chef-3.1.png");
    
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
    
    //vars for hitting
    private int delay;
    private int DELAY = 10;
    private boolean alternate;
    boolean hit = false;
    
    int bi;
    private GreenfootSound eat = new GreenfootSound("biteSound.mp3");
    private GreenfootSound enemyPop = new GreenfootSound("enemyExplosion.mp3");
    private GreenfootSound knifeHit = new GreenfootSound("KnifeHit.mp3");
    
    protected void addedToWorld(World w) {
        cbh = new MobsterHitbox(this, cbhWidth, cbhHeight, cbhDX, cbhDY, false);
        getWorld().addObject(cbh, getX() + cbhDX, getY() + cbhDY);
    }

    public void act() 
    {
        gravity();
        checkMobby();
        checkHit();
    }    

    public Chef()
    {
        delay = DELAY;
        alternate = false;
        setImage(F1L);
    }

    public void checkMobby()
    {
        List<Mobby> list;  // takes the actor Mobby into the list 
        list = getObjectsInRange(mobbyDist, Mobby.class);
        if (list.isEmpty())
        {
            pace();
        }
        else {
            swing();
        }
    }
    
    public void swing()
    {
        List<Mobby> mobby = getObjectsInRange(1000, Mobby.class);
        if (mobby.get(0).getX() < getX()){
            --delay;
            if (delay ==0)
            {
                knifeHit.play();
                GreenfootImage temp = (alternate) ? F1L : F3L;
                if (alternate)
                {
                    temp = F1L;
                    setLocation(getX(), getY());
                    mobby.get(0).health -= damage;
                }
                else {
                    temp = F3L;
                    setLocation(getX(), getY());
                }
                setImage(temp);
                alternate = !alternate;
                delay=DELAY;
            }
        }
        if (mobby.get(0).getX() > getX()){
            --delay;
            if (delay ==0)
            {
                knifeHit.play();
                GreenfootImage temp = (alternate) ? F1R : F3R;
                if (alternate)
                {
                    temp = F1R;
                    setLocation(getX(), getY());
                    mobby.get(0).health -= damage;
                }
                else {
                    temp = F3R;
                    setLocation(getX(), getY());
                }
                setImage(temp);
                alternate = !alternate;
                delay=DELAY;
            }
        }
        if (mobby.get(0).health == 0)
        {
            GameOverScreen world = new GameOverScreen();
            Greenfoot.setWorld(world);
            Level1 Level1 = (Level1) getWorld();
            Level1.bkgMusic.stop();
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
        List<Mobby> mobby = getObjectsInRange(range, Mobby.class);
        if (!mobby.isEmpty()) {
            if (mobby.get(0).getX() <= getX()) walkLeft();
            if (mobby.get(0).getX() >= getX()) walkRight();
        }
        else {
            if (walkTimer != 0 && wL == true)
            {
                walkLeft();
                walkTimer--;
                if (walkTimer == 0)
                {
                    getWorld().removeObject(cbh);
                    cbh = new MobsterHitbox(this, cbhWidth, cbhHeight, -cbhDX, cbhDY, false);
                    getWorld().addObject(cbh, getX() + cbhDX, getY() + cbhDY);
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
                    getWorld().removeObject(cbh);
                    cbh = new MobsterHitbox(this, cbhWidth, cbhHeight, cbhDX, cbhDY, false);
                    getWorld().addObject(cbh, getX() + cbhDX, getY() + cbhDY);
                    walkTimer = 50;
                    runningFrameTime = 0;
                    wR = false;
                    wL = true;
                }
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
        List<Meatball> meatball = cbh.getHitboxIntersections();
        if (!meatball.isEmpty()) hit = true;
        if (hit == true) {
            mobbyDist += 20;
            getWorld().removeObject(meatball.get(0));
            getWorld().removeObject(cbh);
            cbhWidth += 2;
            cbhHeight += 1;
            cbhDY -= 0.2;
            cbh = new MobsterHitbox(this, cbhWidth, cbhHeight, cbhDX, cbhDY, false);
            getWorld().addObject(cbh, getX() + cbhDX, getY() + cbhDY);
            F1R.scale( F1R.getWidth() + 30 , F1R.getHeight() + 30 );
            F2R.scale( F2R.getWidth() + 30 , F2R.getHeight() + 30 );
            F3R.scale( F3R.getWidth() + 30 , F3R.getHeight() + 30 );
            F1L.scale( F1L.getWidth() + 30 , F1L.getHeight() + 30 );
            F2L.scale( F2L.getWidth() + 30 , F2L.getHeight() + 30 );
            F3L.scale( F3L.getWidth() + 30 , F3L.getHeight() + 30 );
            damage += 5;
            range += 400;
            size++;
            if (size < 3)
            {
                eat.play();
            }
            if (size >= 3) 
            {
                if (getWorld() instanceof Level1) {
                    Level1 w = (Level1) getWorld();
                    explode();
                    w.removeObject(this);
                    
                }
                if (getWorld() instanceof Level2) {
                    Level2 w = (Level2) getWorld();
                    explode();
                    w.removeObject(this);
                    
                }
            }
            hit = false;
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
