import greenfoot.*;  // (aorld, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * arite a description of class Mobby here.
 * 
 * @author Dane Reimers
 * @version (a version number or a date)
 */
public class Mobby extends SmoothMover
{
    //vars relating to Mobby's hitbox
    private MobbyHitbox mbyh;
    int mbhWidth = 151;
    int mbhHeight = 240;
    int mbhDX = -8;
    int mbhDY = 0;

    //vars for Mobby's abilities
    public int direction = 1; //Mobby's direction as 1 for right, -1 for left
    public int power;    //power of the thrown meatball
    public int health = 200; //Mobby's health
    int speed = 6;  //speed when running

    //vars for running animation
    private double runningFrameTime = 0;    //timer for changing frames
    int runningFrameSpeed = 15; //rate that frames change when running

    //images
    GreenfootImage F1R = new GreenfootImage("MobbyRunning-F1R.png");
    GreenfootImage F2R = new GreenfootImage("MobbyRunning-F2R.png");
    GreenfootImage F1L = new GreenfootImage("MobbyRunning-F1L.png");
    GreenfootImage F2L = new GreenfootImage("MobbyRunning-F2L.png");
    GreenfootImage MTR = new GreenfootImage("MeatballToss-R.png");  //image for throwing meatball right
    GreenfootImage MTL = new GreenfootImage("MeatballToss-L.png");  //image for throwing meatball left
    GreenfootImage MJTR = new GreenfootImage("JumpandThrow-R.png");
    GreenfootImage MJTL = new GreenfootImage("JumpandThrow-L.png");

    //vars for jumping
    int groundLevel; //y coordinate for the ground
    boolean onGround; //true if Mobby is on the ground (not jumping)
    private int ySpeed; //creates var that determines height of jump
    private int apexTimer;  //creates var that runs timer    

    //vars for throwMeatballRight and throwMeatballLeft
    int mBTimer = 0;    //timer that runs while meatball is being thrown to cycle throught animations
    int mBFrameSpeed = 10;   //speed of the throwing of the meatball
    int mBCooldown = 0;     //cooldown between throwing meatballs
    boolean ThrowL = false;     //primes meatball to be thrown left when "space" is pressed and facing left
    boolean ThrowR = false;     //primes meatball to be thrown left when "space" is pressed and facing left
    public boolean resetPower;   //resets power to zero after a meatball has been thrown
    public boolean increase;    //changes power between increasing and decreasing at 0 and max power

    //vars for side scrolling
    private int BOUNDARY = 700;
    public boolean boundedMove = false;

    // Sounds
    private GreenfootSound meatballThrow = new GreenfootSound("meatballThrow.mp3");
    private GreenfootSound plateCrash = new GreenfootSound("PlateCrash.mp3");

    protected void addedToWorld(World w) {
        mbyh = new MobbyHitbox(this, mbhWidth, mbhHeight, mbhDX, mbhDY, false);
        w.addObject(mbyh, getX() + mbhDX, getY() + mbhDY);
    }

    public void act() 
    {
        detectDirection();  //detect the direction Mobby is facing and set int direction to 1 or -1
        run();
        gravity();
        jump();
        throwMeatballs();
        checkHit(); //check to see if Mobby has been hit by a bullet and decrease health if he has
        boundedMove();
    }    

    public void detectDirection() //detect the direction that Mobby is facing
    {
        if (getImage() == F1R || getImage() == F2R || getImage() == MTR) {
            direction = 1;
        }
        if (getImage() == F1L || getImage() == F2L || getImage() == MTL) {
            direction = -1;
        }
    }

    public int getDirection() //share the direction Mobby is facing with other classes
    {
        return direction;
    }

    private void run()
    {
        if (!onGround && getImage() != MJTR && getImage() != MJTL) { //check if Mobby is in the air and not throwing a meatball
            if (direction == 1) {
                setImage(F2R);
            }
            if (direction == -1) {
                setImage(F2L);
            }
        }
        else { 
            if (getImage() != MTR && getImage() != MTL && getImage() != MJTR && getImage() != MJTL) {   //if not throwing a meatball
                if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("left")) {   //lets mobby run
                    runRight();
                    runLeft();
                }
                else { //if Mobby is stationary set image to him standing facing correct direction              
                    if (direction == 1) {
                        setImage(F1R);
                        runningFrameTime = runningFrameSpeed / 2;
                    }
                    if (direction == -1) {
                        setImage(F1L);
                        runningFrameTime = runningFrameSpeed / 2;
                    }
                }
            }
        }
    }  

    //Animate the character to appear to be running by cycling through images and moving
    private void runRight()
    {               
        if (Greenfoot.isKeyDown("right")) {
            if (!boundedMove && noCollisionR()) move(speed);
            if (runningFrameTime == runningFrameSpeed) runningFrameTime = 0;            
            if (runningFrameTime == 0) {
                setImage(F1R);
            }          
            if (runningFrameTime == runningFrameSpeed / 2) {
                setImage(F2R);
            }
            runningFrameTime++;        
        }
    }

    private void runLeft()
    {   
        if (Greenfoot.isKeyDown("left")) {
            if (!boundedMove && noCollisionL()) move(-speed);
            if (runningFrameTime == runningFrameSpeed) runningFrameTime = 0;        
            if (runningFrameTime == 0) {
                setImage(F1L);
            }          
            if (runningFrameTime == runningFrameSpeed / 2) {
                setImage(F2L);
            }
            runningFrameTime++;
        }
    }

    public void gravity()
    {
        groundLevel = getWorld().getHeight() - getImage().getHeight()/2 - 50;
        onGround = (getY() == groundLevel);
        if (!onGround) // in middle of jump
        {
            if (ySpeed == 0 && apexTimer > 0) apexTimer--; // run apex timer
            if (ySpeed == 0 && apexTimer > 0) return; // apex timer still running
            ySpeed++; // adds gravity effect
            setLocation(getX(), getY()+ ySpeed); // fall (rising slower or falling faster)
            if (getY()>=groundLevel) // has landed (reached ground level)
            {
                setLocation(getX(), groundLevel); // set on ground
                Greenfoot.getKey(); // clears any key pressed during jump
            }
            else  // Andrew Lor -  makes it so that Mobby can move L & R mid jump
            {
                if (Greenfoot.isKeyDown("right"))
                {
                    if (noCollisionR()) move(speed);
                    setImage(F2R);
                }
                if (Greenfoot.isKeyDown("left"))
                {
                    if (noCollisionL()) move(-speed);
                    setImage(F2L);
                }
            }
        }
    }

    public void jump()
    {      
        if (Greenfoot.isKeyDown("up") && onGround)
        {
            ySpeed = -25; // add jump speed
            setLocation(getX(), getY()+ ySpeed); // leave ground
            apexTimer = 2;  // set apex timer (adjust value to suit)
        }
    }

    public void throwMeatballs()
    {
        if (direction == 1) {
            throwMeatballRight();
        }
        if (direction == -1) {
            throwMeatballLeft();
        }
    }

    public void throwMeatballRight()
    {
        if (Greenfoot.isKeyDown("space") && mBCooldown >= 15) {
            if(increase) power++;   //make power level fluctuate when f is held
            if(!increase) power--;
            if(power >= 40) increase = false;
            if(power <= 0) increase = true;
            ThrowR = true;  //primes meatball to be thrown when f is released
        }
        if (ThrowR == true && !Greenfoot.isKeyDown("space")) {
            if (mBTimer == 0) {
                if(onGround) setImage(MTR);
                else setImage(MJTR);
                createMeatball();
                meatballThrow.play();
            }     
            if (mBTimer == mBFrameSpeed) {
                if(onGround) setImage(F1R);
                else setImage(F2R);
                mBTimer = -1;
                mBCooldown = 0;
                power = 0;
                increase = true;    //makes power increase at first for next throw
                resetPower = true;  //makes power start at 0 for next throw
                ThrowR = false;
            }
            mBTimer++;
        }
        mBCooldown++;
    }

    public void throwMeatballLeft()
    {
        if (Greenfoot.isKeyDown("space") && mBCooldown >= 15) {
            if(increase) power++;   //make power level fluctuate when f is held
            if(!increase) power--;
            if(power >= 40) increase = false;
            if(power <= 0) increase = true;
            ThrowL = true;  //primes meatball to be thrown when f is released
        }
        if (ThrowL == true && !Greenfoot.isKeyDown("space")) {
            if (mBTimer == 0) {
                if(onGround) setImage(MTL);
                else setImage(MJTL);
                createMeatball();
                meatballThrow.play();
            }     
            if (mBTimer == mBFrameSpeed) {
                if (onGround) setImage(F1L);
                else setImage(F2L);
                mBTimer = -1;   
                mBCooldown = 0;     //resets cooldown timer
                power = 0;      //resets power to zero
                increase = true;    //makes power increase at first for next throw
                resetPower = true;  //makes power start at 0 for next throw
                ThrowL = false;
            }
            mBTimer++;
        }
        mBCooldown++;
    }

    private void createMeatball()  // Andrew Lor throwMeatballs and Meatball class
    {
        Meatball m = new Meatball();
        getWorld().addObject(m, getX() + 85 * direction, getY() + 35);
    }

    private void checkHit()
    {
        if (!mbyh.getBulletIntersections().isEmpty())
        {
            List<Bullet> bullet = mbyh.getBulletIntersections();
            getWorld().removeObject(bullet.get(0));
            health -= 25;
        }
        if (mbyh.getPlateIntersections() != null)
        {
            plateCrash.play();
            Plate plate = (Plate) mbyh.getPlateIntersections();
            plate.explode();
            getWorld().removeObject(plate);
            health -= 25;
        }
        if (health == 0) {
            GameOverScreen world = new GameOverScreen();
            Greenfoot.setWorld(world);
            if (getWorld() instanceof Level2){
                ((Level2)getWorld()).stop();
            }
            if (getWorld() instanceof Level3){
                ((Level3)getWorld()).stop();
            }
            if (getWorld() instanceof LevelBoss){
                ((LevelBoss)getWorld()).stop();
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public boolean noCollisionR()
    {
        Actor objects = getOneObjectAtOffset(95, 0, Actor.class);

        if (objects == null) return true;
        else return false;
    }

    public boolean noCollisionL()
    {
        Actor objects = getOneObjectAtOffset(-95, 0, Actor.class);

        if (objects == null) return true;
        else return false;
    }

    private void boundedMove()
    {
        World w = getWorld();
        if (getX() <= BOUNDARY && Greenfoot.isKeyDown("left") && noCollisionL())
        {
            setLocation(BOUNDARY, getY());
            ((ScrollingWorld)getWorld()).shiftWorld(speed);
            boundedMove = true;
        }
        else if (getX() >= getWorld().getWidth() - BOUNDARY && Greenfoot.isKeyDown("right") && noCollisionR())
        {
            setLocation(getWorld().getWidth() - BOUNDARY, getY());
            ((ScrollingWorld)getWorld()).shiftWorld(-speed);
            boundedMove = true;
        }
        else boundedMove = false;
    }
}