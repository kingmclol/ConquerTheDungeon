import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Skeleton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Skeleton extends Enemy
{
    private int frame = 0, acts = -1;
    public Skeleton(){
        collisionBox = new CollisionBox(30, 55, Box.SHOW_BOXES, this);
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -33, Color.RED, Color.BLACK, false, Color.YELLOW, 1);
        spd = 2;
        death = false;
        dealtDamage = false;
        inAttack = false;
        
    }
    public void act()
    {
        super.act();
        acts++;
        if(getDistance(player) <= 300){
            inAttack = true;
        }else{
            inAttack = false;
        }
        if(this.getHp() <= 0)
        {
            inAttack = false;
        }
        if(this.getHp() > 0 && !inAttack)
        {
            findTarget();
            manageAnimation();
            attack();
        }
        if(inAttack) // If attacking:
        {
            attackAnimation();
        }
        if(this.getHp() <= 0) // trigger death animation.
        {
            deathAnimation();
        }
        
    }
    
    public void findTarget(){
        
        if(player != null && getDistance(player) >= 300){
            pathToEntity(player);
        }
    }
    
    public void attack()
    {
        List<Player> target = getObjectsInRange(200, Player.class);
        if(!target.isEmpty())
        {
            //inAttack = true;
            //Animation here
        }
    }
    
    public void manageAnimation()
    {
        
    }
    
    public void deathAnimation()
    {
    
    }
    
    public void attackAnimation()
    {
        
    }
}
