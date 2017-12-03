import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class MeatIntro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeatIntro extends Actor
{
    private int speed;
    
    public void act() 
    {
        setLocation(getX(), getY() + speed); 
        turn (10);
    }
    private void checkRemove() 
    {
        World w = getWorld();
        if( getY() > w.getHeight()+ 30) {
            w.removeObject(this);
        }
         if( getX() > w.getHeight()+ 30) {
            w.removeObject(this);
        }
    }    
    
    public void setSpeed ( int s){
        speed = s;
    }
}
