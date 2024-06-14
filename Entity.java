import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>Entities are "mobs" that can do Actions throughout the game. They can be on your team (an ALLY) or against (an ENEMY).</p>
 * 
 * <p>The constructor for any Entity subclass MUST create their own collisionBox based on their image size.
 * Also, you MUST run super.addedToWorld(World w) in its addedToWorld().</p>
 * 
 * @author Freeman Wang
 * @author Osmond Lin
 * @author Tony Lin
 * @version 2024-06-12
 */
public abstract class Entity extends SuperActor implements Damageable
{
    protected int hp;
    protected int frame = 0, acts = 0;
    protected int maxHp;
    protected double speedBoost = 0 ;
    protected Animation dying;
    protected CollisionBox collisionBox;
    protected SuperStatBar hpBar;

    protected boolean inAttack, death = false, dealtDamage, recievedDamage = false;
    protected double speedMod = 1.0;
    protected ParabolicProjectile projPath; 
    protected String flung = "none";

    protected Timer iFrameTimer;
    protected static final int IFRAMES = 10;
    private ArrayList<Vector> path = new ArrayList<Vector>();
    /**
     * Determines whether to show pathfinding related logs.
     */
    private static final boolean SHOW_PATHFINDING_DEBUG = false;
    /**
     * Creates an entity with the given max hp.
     * @param maxHp The max health of the entity.
     */
    public Entity(int maxHp) {
        this.maxHp = maxHp;
        hp = maxHp;
        iFrameTimer = new Timer();
    }
    public void addedToWorld(World w) {
        w.addObject(collisionBox, getX(), getY());
        w.addObject(hpBar, getX(), getY() - 33); // Position the HP bar slightly above the player
    }
    public void act() {
        if(getWorld() != null && !flung.equals("none")){
            fling();
        }
        if(speedBoost > 0){
            speedBoost--;
        }
        if (hp <= 0) {
            deathAnimation(15, dying.getAnimationLength());
        }
        manageCollision();
    }
    protected void deathAnimation(int timeInBetween, int length)
    {
        if(!death)
        {
            frame = 0;
            death = true;
        }
        if(acts % timeInBetween == 0)
        {
            frame = (frame+1)%(length);
        }
        setImage(dying.getFrame(frame));
        if(frame == (length-1))
        {
            die();
        }
    }
    /**
     * Makes this Entity take damage.
     * @param dmg The damage to take
     * @return The amount of damage recieved.
     */
    public int damage(int dmg) {
        if (iFrameTimer.acts() <= IFRAMES) return 0; // immune.
        // Add some damage box.
        TextBox dmgBox = new TextBox("-" + dmg, Utility.randomIntInRange(20, 36), Color.ORANGE, null, 3, 255);
        getWorld().addObject(dmgBox, getX()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE), getY()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE));
        dmgBox.fadeOut();
        
        // Calcuate damage taken and update
        int dmgTaken;
        if (hp <= dmg)dmgTaken = hp;
        hp -= dmg;
        dmgTaken = dmg;
        if(this instanceof Player){
            StatsUI.updateHP(((double) hp / maxHp )* 100.0);
        }
        hpBar.update(hp);
        iFrameTimer.mark(); // reset iframes
        return dmgTaken;
    }
    /**
     * Heal this Entity.
     * @param heal How much hp to heal
     */
    public void heal(int heal) {
        // Create a heal text box
        TextBox dmgBox = new TextBox("+" + heal, Utility.randomIntInRange(20, 36), Color.GREEN, null, 3, 255);
        getWorld().addObject(dmgBox, getX()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE), getY()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE));
        dmgBox.fadeOut();
        // Calculate heal
        hp+=heal;
        if(hp > maxHp){
            hp = maxHp;
        }
        if(this instanceof Player){
            
            StatsUI.updateHP(((double) hp / maxHp )* 100.0);
           
        }
        hpBar.update(hp);
    }
    //protected abstract void animate();
    public void die() {
        getWorld().removeObject(this);
    }
    /**
     * Manages collision detection of this Entity, keeping it from intersecting with other obstructions (such as
     * another entity, or a wall.
     */
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
    }
    public void setFlungState(boolean state){
        if(state){
            if(Player.getFacing().equals("right") ){
                flung = "left";
            }
            else if (Player.getFacing().equals("left")){
                flung = "right";
            }
            else if (Utility.randomIntInRange(0, 1) == 0){
                flung = "right";
            }
            else{
                flung = "left";
            }
        }
    }
    public void fling(){
        if(projPath != null && !projPath.pathDone()){
            //Iterates through 1 point per flungTimer act
            
            //Turns the actor as it moves through the projectile path
            turn(8);
            Vector pos = projPath.nextCoord();
            if(flung.equals("left") ){
                setLocation(getX() - pos.getX(), getY() - pos.getY());
            }
            else if (flung.equals("right")){
                setLocation(getX() + pos.getX(), getY() - pos.getY());
            }
            if(projPath.pathDone()){
            
                setRotation(0);
                flung = "none";
            }
        }
        else {
            projPath = new ParabolicProjectile(40, Greenfoot.getRandomNumber(40) + 10, Greenfoot.getRandomNumber(20) + 10);
        }
    }
    /**
     * Pathfind to a specific positino, moving distance units at a time.
     * @param target The place to pathfind to.
     * @param distance the distance to travel.
     */
    protected void pathTowards(Vector target, double distance) {
        Board board = ((GameWorld)getWorld()).getBoard();
        if (target == null) {
            return; // Can't do anything about this.
        }

        // Get nodes from the nodeGrid for comparison
        Vector myPosition = getPosition();
        Node targetNode = board.getNode(board.getCellWithRealPosition((int)target.getX(), (int)target.getY()));
        Node currentNode = board.getNode(board.getCellWithRealPosition((int)myPosition.getX(), (int)myPosition.getY()));
        if (targetNode == null || currentNode == null || !targetNode.isWalkable() || !currentNode.isWalkable()) { // A node does not exist or is not walk to able.
            if (SHOW_PATHFINDING_DEBUG) {
                if (targetNode == null) System.out.println("targetNode is null");
                if (currentNode == null) System.out.println("currentNode is null");
                if (!targetNode.isWalkable()) System.out.println("targetNode not walkable");
                if (!currentNode.isWalkable()) System.out.println("currentNode not walkable");
            }
            
        }
        else { // Do pathfdingin depending on the nodes given.
            if (targetNode == currentNode) { // Same node as target!!!
                moveTowards(target, distance); // Within same node (no need to pathfind), so just move towards the target directly.
                return; // nothing else to do.
            }
            else if (targetNode != targetNodePrev) { // target position is different from what was originally. Either new target, or the target moved somewhere else.
                if(SHOW_PATHFINDING_DEBUG) System.out.println("target:" + target + " | " + " new target node! new: " + targetNode + " vs. prev: " + targetNodePrev);
                targetNodePrev = targetNode; // store the target's node for comparison later on.
                
                // Create a new path to the new target node.
                ArrayList<Node> nodes = board.findPath(currentNode, targetNode);
                if (nodes != null) {
                    path = (ArrayList<Vector>)((GameWorld)getWorld()).getBoard().convertPathToPositions(nodes);
                    path.remove(0); // This is the node I am currently in.
                }
                // if (board.findPath(currentNode, targetNode) != null)path = null; // need new path to the target.
            }
        }
    
        // The actual moving part.
        if (path == null) { // no path, or target node changed (target moving), or target changed
            // Pathfind to target.
            ArrayList<Node> nodes = board.findPath(currentNode, targetNode);
            if (nodes != null) {
                path = (ArrayList<Vector>)((GameWorld)getWorld()).getBoard().convertPathToPositions(nodes);
                path.remove(0); // This is the node I am currently in.
            }
        }

        if (path != null) {
            if (path.size() > 0) { // If there is still a positino to go to,
                Vector nextPos = path.get(0); // get that position
                if (SHOW_PATHFINDING_DEBUG) System.out.println(this + " moving to " + nextPos);
                moveTowards(nextPos, distance); // Move towards the next positino.

                if (displacementFrom(nextPos) < 5) { // If close to the target position
                    if (SHOW_PATHFINDING_DEBUG) System.out.println(this + " removed " + nextPos);
                    path.remove(0); // remove from list of positions to move to.
                }
            }
            else { // There are no more positinos to move to (At destination)
                path = null; // set path as null. No more to move.
                if (SHOW_PATHFINDING_DEBUG) {
                    System.out.println(this + " has completed pathfinding.");
                }
                return;
            }
        }
    }
    /** 
     * Move towards another Entity. This time, with pathfinding involved.
     * @param target The Entitty to move towards.
     * @param The distance the Entity should travel. Also can be seen as the "speed."
     */
    protected void pathTowards(Entity target, double distance) {
        pathTowards(target.getPosition(), distance);
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
     * Returns the position of this Entity AT ITS ACTUAL COLLISIONBOX (at its feet), overriding the getPosition()
     * in SuperActor
     * @return The Vector representing my position at my FEET
     */
    @Override
    public Vector getPosition(){
        return collisionBox.getPosition();
    }
    public void setSpeed(double speedMod){
        this.speedMod = speedMod;
        this.speedBoost = 50;
    }
}
