import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingActor extends SmoothMover
{
    public void setAbsoluteLocation(int dx){
        setLocation(getX() + dx, getY());
    }
}
