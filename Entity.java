import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>Entities are "mobs" that can do Actions throughout the game. They can be on your team (an ALLY) or against (an ENEMY).</p>
 * 
 * <p>The constructor for any Entity subclass MUST create their own collisionBox based on their image size.
 * Also, you MUST run super.addedToWorld(World w) in its addedToWorld().</p>
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Entity extends SuperActor implements Damageable
{
    // The enum Team stores whether the Entity is on your same team or not (Ally vs hostile)
    public enum Team {
        ALLY,
        ENEMY
    }
    // public static final int MAX_SKILL_POINTS = 5;
    // protected HashMap<StatusModifier.Trigger, ArrayList<StatusModifier>> statusModifiers; // Stores modifiers of each trigger type.
    //protected HealthBar healthBar;
    // protected Team team;
    protected int hp;
    protected int recoveryActs = 0;
    protected ArrayList<Cell> path;
    protected CollisionBox collisionBox;
    protected SuperStatBar hpBar;

    protected boolean inAttack, death, dealtDamage, recievedDamage, flung = false;
    protected ParabolicProjectile projPath;

    protected Timer iFrameTimer;
    protected static final int IFRAMES = 10;

    public Entity(Team team, int maxHp) {
        // this.team = team;
        hp = maxHp;
        // // healthBar = new HealthBar(maxHp);
        // skillPoints = MAX_SKILL_POINTS/2;
        // //Deck = new Deck(Deck.Preset.A);
        // // Initializing the HashMap.
        // intent = Intent.NONE;
        // path = null;
        // moveTimer = new Timer();
        // statusModifiers = new HashMap<StatusModifier.Trigger, ArrayList<StatusModifier>>();
        // for (StatusModifier.Trigger t : StatusModifier.Trigger.values()) { // For types of status trigger times,
            // statusModifiers.put(t, new ArrayList<StatusModifier>()); // Greate an arraylist to keep track of them.
        // }
        iFrameTimer = new Timer();
    }
    public void addedToWorld(World w) {
        w.addObject(collisionBox, getX(), getY());
        w.addObject(hpBar, getX(), getY() - 33); // Position the HP bar slightly above the player
    }
    
    public void act() {
        if(flung){
            fling();
        }
        manageCollision();
        //animate();
    }
    // /**
     // * Given a StatusModifier m, apply it onto this Entity.
     // */
    // public void applyModifier(StatusModifier m) {
        // // Get the ARrayList that holds the modifier of same trigger type as m , and add it there.
        // statusModifiers.get(m.trigger).add(m); 
        // m.setOwner(this);
    // }
    // /**
     // * Given a StatusModifier m, remove it from this Entity, if it exists.
     // */
    // public void removeModifier(StatusModifier m) {
        // // Get the ArrayList that holds the modifiers of same trigger type as m, then remove from the arraylist.
        // statusModifiers.get(m.trigger).remove(m);
    // }
    protected abstract void deathAnimation();
    public int damage(int dmg) {
        if (iFrameTimer.acts() <= IFRAMES) return 0;
        TextBox dmgBox = new TextBox("-" + dmg, 24, Color.ORANGE, null, 2, 255);
        getWorld().addObject(dmgBox, getX()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE), getY()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE));
        dmgBox.fadeOut();
        int dmgTaken;
        if (hp <= dmg)dmgTaken = hp;
        hp -= dmg;
        dmgTaken = dmg;
        hpBar.update(hp);
        if(hp <= 0) die();
        iFrameTimer.mark(); // reset iframes
        return dmgTaken;
    }
    public void heal(int heal) {
        TextBox dmgBox = new TextBox("+" + heal, 24, Color.GREEN, null, 2, 255);
        //getWorld().addObject(dmgBox, getX()-Tile.tileSize/2+Greenfoot.getRandomNumber(GameWorld.tileSize), getY()-GameWorld.tileSize/2+Greenfoot.getRandomNumber(GameWorld.tileSize));
        dmgBox.fadeOut();
        hp+=heal;
        if(hp > 100){
            hp = 100;
        }
        hpBar.update(hp);
    }
    //protected abstract void animate();
    public void die() {
        getWorld().removeObject(this);
    }
    public boolean isAlive() {
        //return hp >= 0;
        return false;
    }
    protected void manageCollision() {
        collisionBox.forcePositionUpdate(); // match collision box position with the entity position
        ArrayList<CollisionBox> collisionBoxes = (ArrayList<CollisionBox>)collisionBox.getIntersectingBoxes(CollisionBox.class);
        collisionBoxes.remove(collisionBox); // Don't want to intersect with myself man.
        for (Box b : collisionBoxes) {
            Hit hit = b.intersectAABB(collisionBox);
            if (hit != null) { // collision oh no
                Vector delta = hit.getDelta();
                this.displace(delta);
            }
        }
        
        if(getX() <= 10){
            setLocation(11, getY());
        }
        if(getX() >= 1015){
            setLocation(1014, getY());
        }
        if(getY() <= 20){
            setLocation(getX(), 21);
        }
        if(getY() >= 740){
            setLocation(getX(), 739);
        }
    }
    public void setFlungState(boolean state){
        flung = state;
    }
    public void fling(){
        if(projPath != null && !projPath.pathDone()){
            //Iterates through 1 point per flungTimer act
            
            //Turns the actor as it moves through the projectile path
            turn(8);
            Vector pos = projPath.nextCoord();
            if(Player.getFacing().equals("right")){
                setLocation(getX() - pos.getX(), getY() - pos.getY());
            }
            else{
                setLocation(getX() + pos.getX(), getY() - pos.getY());
            }
            if(projPath.pathDone()){
            
                setRotation(0);
                flung = false;
            }
        }
        else {
            projPath = new ParabolicProjectile(40, Greenfoot.getRandomNumber(40) + 10, Greenfoot.getRandomNumber(20) + 10);
        }
    }
    public boolean damaged()
    {
        return recievedDamage;
    }
    public void setDamagedState(boolean x)
    {
        recievedDamage = x;
    }
    /**
     * Returns the position of this Entity AT ITS ACTUAL COLLISIONBOX (at its feet)
     */
    public Vector getPosition(){
        return collisionBox.getPosition();
    }
}
