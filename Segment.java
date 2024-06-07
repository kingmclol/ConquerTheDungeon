import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Segment extends Actor
{
    /**
     * Act - do whatever the Line wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Vector start, end;
    GreenfootImage img = new GreenfootImage(50, 5);
    Color color = Color.BLACK;
    int thickness = 3;
    public Segment (Entity start, Entity end) {
        this.start = start.getPosition();
        this.end = end.getPosition();
    }

    public Segment (Vector start, Vector end, Color color) {
        this(start, end);
        this.color = color;
    }

    public Segment (Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }

    public Segment (Vector start, Vector end, int thickness) {
        this.start = start;
        this.end = end;
        this.thickness = thickness;
    }

    public Segment (Color color) {
        this.color = color;
    }

    public void draw() {
        if (start.displacementFrom(end).getMagnitude() <= 0.5) return;
        GreenfootImage img = new GreenfootImage(Utility.round(start.displacementFrom(end).getMagnitude()), thickness);
        img.setColor(color);
        //img.fill();
        setImage(img);
        setLocation(Utility.round(start.getX()), Utility.round(start.getY()));
        turnTowards(Utility.round(end.getX()), Utility.round(end.getY()));
        move(Utility.round(getImage().getWidth()/2));
    }

    public void update (Vector start, Vector end) {
        this.start = start;
        this.end = end;
        draw();
    }

    public void updateRelative(Vector start, Vector end) {
        this.start = start;
        this.end = start.add(end);
        draw();
    }

    public boolean intersectsWall() {
        Wall wall = (Wall)getOneIntersectingObject(Wall.class);
        if(wall == null){
            return false;
        }
        return true;
    }

}