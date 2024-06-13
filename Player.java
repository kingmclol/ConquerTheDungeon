import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Player class that manages everything the player does, including the controls, stats, and
 * actions.
 * 
 * <p> Players use the controls WASD to move around. They can move freely around the world,
 * but cannot walk through certain objects, and can be affected when in contact with other objects.
 * For example, players cannot walk through barrels and walls. Additionally, they will take damage in lava
 * and spike traps. When they walk on a mine, they will take damage and be launched away.
 * 
 * <p> Player has two weapons, a sword and a staff. By pressing the key "R", they can switch their weapon.
 * Staff can be used for 10 seconds, before having a cooldown of 10 seconds. Both weapons also have ultimate abilities,
 * which can be triggered by pressing the button "Q".
 * 
 * <p> Player has a dash ability, where they will dash in the direction they are moving by pressing "shift".
 * 
 * <p> Players can pick up coins, heal, and powerups that will each provide a benefit to them.
 * 
 * @author Osmond Lin
 * @author Tony Lin
 * @author Neelan Thurairajah
 * @version 2024-06-12
 */
public class Player extends Entity
{    
    //Player Stats
    private boolean isPoweredUp = false;
    private long powerUpStartTime = 0;
    private int normalSpeed;
    private int powerUpSpeed;
    private int attackDmg = 20;
    private int coin = 0;
    private double speed;
    private int xOffset, yOffset, atkSpd = 10, frame = 0, acts = 0, index = 0;

    private HiddenBox Xhitbox, Yhitbox; // HitBoxes

    private static int x, y; // location of the Player.
    //Cooldowns, durations:
    private double timeForStaff = 600.0, remainingCds = 0;
    private int ultimateCooldown = 540;
    private int cooldownTimer = 0;
    private boolean lockStaff = false, enhancedSwings = false;
    private int hitCount = 0;
    //Moving
    private Animation right,left,down,up,dying, staffUp, staffDown, staffLeft, staffRight;
    private GreenfootImage[] swingingUp = new GreenfootImage[6],swingingDown = new GreenfootImage[6],swingingLeft = new GreenfootImage[6],swingingRight = new GreenfootImage[6];
    private static String facing = "right",weapon = "sword";
    private boolean inAttack = false, mouseClick, moving = false;
    private static String[] weaponList = new String[2];

    private Aura aura;

    private boolean isDashing = false;
    private long dashCooldownTime = System.currentTimeMillis();
    private int dashFrames = 0;
    //private double dashDx = 0, dashDy = 0;
    private Vector dashVelocity;

    private double critRate;
    private double critDamage;

    /**
     * Player constructor that sets the initial stats of the player and manages the initial animation frames
     */
    public Player() {
        super(100);
        
        critRate = 0.3;
        critDamage = 1.6;

        normalSpeed = 4;
        powerUpSpeed = normalSpeed + (int)(normalSpeed * 0.3);

        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -37, Color.GREEN, Color.BLACK, false, Color.YELLOW, 1);
        //Animation spritesheet cutter using Mr Cohen's animation class: 
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 10);
        dying = Animation.createAnimation(new GreenfootImage("Player.png"), 20, 1, 10, 64, 64);

        //Initialize weapons
        weaponList[0] = "sword";
        weaponList[1] = "staff";

        Xhitbox = new HiddenBox(40, 60);
        Yhitbox = new HiddenBox(62, 40);

        //weapon:
        for(int i = 0; i<swingingUp.length; i++)
        {
            swingingUp[i] = new GreenfootImage("sword/up" + (i+1) + ".png");
            swingingLeft[i] = new GreenfootImage("sword/left" + (i+1) + ".png");
            swingingDown[i] = new GreenfootImage("sword/down" + (i+1) + ".png");
            swingingRight[i] = new GreenfootImage("sword/right" + (i+1) + ".png");
        }
        staffUp = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 4, 1, 8, 64, 64);
        staffLeft = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 5, 1, 8, 64, 64);
        staffDown = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 6, 1, 8, 64, 64);
        staffRight = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 7, 1, 8, 64, 64,7);
        //Start at frame 0
        setImage(up.getFrame(0));
        collisionBox = new CollisionBox(30, 20, Box.SHOW_BOXES, this, 0, 20); // THIS NEEDS TO BE MOVED TO ENTITY. FOR TESTING ONLY RN

        SoundManager.addSound(50, "swordSound.mp3", 50);
        SoundManager.addSound(50, "staffSound.mp3", 40); 
        SoundManager.addSound(50, "swordUltimateSound.mp3", 50);
    }

    public void act() {
        x = getX();
        y = getY();
        moving = false;
        


        moving = false;
        if(!death){
            if (cooldownTimer > 0) {
                StatsUI.updateUlt(((double)cooldownTimer/ultimateCooldown)*100.0);
                cooldownTimer--; // Decrement cooldown timer for ult
                
            }
            if (cooldownTimer <= 0) {// Can use ultimate once cooldown is over
                useUltimate();
            }
            if(remainingCds > 0) // Cooldown for staff weapon
            {
                remainingCds--;
            }
            else
            {
                if("r".equals(Keyboard.getCurrentKey()))
                {
                    switchWeapon();
                    StatsUI.switchUlt(getCurrentWeapon());
                    if(enhancedSwings){
                        cooldownTimer = ultimateCooldown;
                        enhancedSwings = false;
                    }
                }
            }
            
            if(this.getCurrentWeapon().equals("staff"))
            {
                //If player is currently using staff, decrease the time he is allowed to use it for
                timeForStaff--;
                StatsUI.updateCd1((timeForStaff/600.0)*100.0);
                // continue timer, up to 10 seconds per time whether you end early or not.
            }
            else if(timeForStaff < 600 && this.getCurrentWeapon().equals("sword"))
            {
                timeForStaff = timeForStaff+(1.0); 
                StatsUI.updateCd1((timeForStaff/600.0)*100.0);
                // for every second spent in sword, regenerate 1/6th of the timer second for staff.
            }
            //Mouse click == false prevents spam clicking, which keeps resetting the animation.
            if(Greenfoot.mousePressed(null) && mouseClick == false)
            {
                inAttack = true;
                mouseClick = true;
                frame = 0;
                //set frame 0 when attacking.
            }
            if(flung.equals("none")){
                if(!inAttack)
                {
                    movePlayer();
                }
                else
                {
                    attackAnimation();

                    if(Math.random() < critRate){ //Chance of player to crit
                        if(this.getCurrentWeapon().equals("staff"))
                        {
                            handleShooting((int)((double)attackDmg * critDamage));
                        }
                        else
                        {
                            attack((int)((double)attackDmg * critDamage));
                        }
                    }else{
                        if(this.getCurrentWeapon().equals("staff"))
                        {
                            handleShooting(attackDmg);
                        }
                        else
                        {
                            attack(attackDmg);
                        }
                    }
                }
            }
            checkPowerUpStatus();


        // if still in staff and not middle of attack animation,
            if(timeForStaff <= 0 && !inAttack)
            {
                switchWeapon(); // automatically switch
                StatsUI.switchUlt(getCurrentWeapon());
                remainingCds = 600; // restart Cooldown once staff is expired.
                timeForStaff = 0;
  
            }
        }
        
        acts++;
        super.act();
    }

    /**
     * An aura actor will always follow the player while player is in world.
     * Whenever player is in their power up state, the aura will appear visible.
     */
    public void addedToWorld(World world) {
        super.addedToWorld(world);
        aura = new Aura(this);
        world.addObject(aura, getX(), getY());
    }

    /**
     * Method that moves the player
     */
    private void movePlayer() {
        int dx = 0, dy = 0; //Change in X and Y based on movement
        int x;// Animation Speed base on a factor of variable X
        speed = isPoweredUp ? powerUpSpeed : normalSpeed;
        if(speedBoost > 0){
            speed = speed * speedMod;
        }
        x = (int)speed;
        if(acts % 10 == 0)
        {
            frame = (frame+1)%(up.getAnimationLength());
        }
        acts++;
        if (Greenfoot.isKeyDown("w")) {
            dy -= speed;
            setImage(up.getFrame(frame));
            facing = "up";
            moving = true;
        }
        if (Greenfoot.isKeyDown("s")) {
            dy += speed;
            setImage(down.getFrame(frame));
            facing = "down";
            moving = true;
        }
        if (Greenfoot.isKeyDown("a")) {
            dx -= speed;
            setImage(left.getFrame(frame));
            facing = "left";
            moving = true;
        }
        if (Greenfoot.isKeyDown("d")) {
            dx += speed;
            setImage(right.getFrame(frame));
            facing = "right";
            moving = true;
        }
        if(Greenfoot.getKey() == null && (moving == false))
        {
            idle();
        }
        move(dx, dy, speed);
        if(acts%(10) == 0)
        {
            frame = (frame+1)%(up.getAnimationLength()); 
        }
        if (isDashing) { //manages the player's dash
            dashFrames++;
            displace(dashVelocity);
            if (dashFrames >= 10) {
                isDashing = false;
                dashCooldownTime = System.currentTimeMillis();
            }
        } else {
            StatsUI.updateCd2(((double) (System.currentTimeMillis() - dashCooldownTime)/ 1000.0)*100.0);
            if (System.currentTimeMillis() - dashCooldownTime >= 1000) {
                String key = Keyboard.getCurrentKey();
                if ("shift".equals(key) && (dx != 0 || dy != 0)){
                    dash(dx, dy);
                }
            }
        }
    }

    /**
     * Method that moves the player using vector movement
     * 
     * @param dx   The x-component of the vector
     * @param dy   The y-component of the vector
     * @param spd  The speed at which the player moves
     */
    public void move(double dx, double dy, double spd)
    {
        double vectorMagnitude = Math.sqrt(dx*dx + dy*dy);
        if(vectorMagnitude == 0)
        {
            return;
        }
        double xComponent = dx/vectorMagnitude * spd;
        double yComponent = dy/vectorMagnitude * spd;
        //System.out.println(Math.sqrt(xComponent*xComponent + yComponent*yComponent));
        setLocation(x + xComponent, y + yComponent);
    }

    /**
     * Method that allows the player to dash using vector movement
     * 
     * @param dx   The x-component of the vector
     * @param dy   The y-component of the vector  
     */
    private void dash(int dx, int dy) {
        dashVelocity = new Vector(dx, dy);
        if (isDashing) return; // Prevent dashing again if already dashing

        isDashing = true;
        dashFrames = 0;

        double dashSpeed = 10.0; 
        dashVelocity.scaleTo(dashSpeed);

    }

    /**
     * Method that manages the shooting of the player when in staff mode
     * 
     * @param dmg    The damage that the staff does per bullet
     */
    private void handleShooting(int dmg){
        int staffdmg = (int)(dmg * 0.75);
        staffdmg = Utility.randomIntInRange((int)(0.9*staffdmg), (int)(1.1*staffdmg));
        if (Greenfoot.mouseClicked(null)) { //Bullet moves in the direction of the mouse click
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                shoot(mouseX, mouseY, staffdmg);
            }
        }
    }

    /**
     * Method for the player to attack when in sword mode
     * 
     * @param damage    The damage per swing of the sword
     */
    private void attack(int damage) {
        damage = Utility.randomIntInRange((int)(0.9*damage), (int)(1.1*damage));
        if(frame == 5)//does damage on the fifth frame
        {
            List<Damageable> targets;
            int spd = 6;
            switch(facing)
            {
                case "right":
                    //Xhitbox.makeVisible();
                    getWorld().addObject(Xhitbox, this.getX()+20, this.getY());
                    targets = (List<Damageable>) Xhitbox.getIntersectingActors(Damageable.class);
                    if(enhancedSwings)
                    {
                        SoundManager.playSound("swordUltimateSound.mp3");
                        getWorld().addObject(new Slash(spd, damage, this, true), getX(), getY());
                    }
                    break;
                case "left":
                    //Xhitbox.makeVisible();
                    getWorld().addObject(Xhitbox, this.getX()-20, this.getY());
                    targets = (List<Damageable>)Xhitbox.getIntersectingActors(Damageable.class);
                    if(enhancedSwings)
                    {
                        SoundManager.playSound("swordUltimateSound.mp3");
                        getWorld().addObject(new Slash(-1*spd, damage, this, true), getX(), getY());
                    }
                    break;
                case "up":
                    //Yhitbox.makeVisible();
                    getWorld().addObject(Yhitbox, this.getX()+13, this.getY()-30);
                    targets = (List<Damageable>)Yhitbox.getIntersectingActors(Damageable.class);
                    if(enhancedSwings)
                    {
                        SoundManager.playSound("swordUltimateSound.mp3");
                        getWorld().addObject(new Slash(-1*spd, damage, this, false), getX(), getY());
                    }
                    break;
                case "down":
                    //Yhitbox.makeVisible();
                    getWorld().addObject(Yhitbox, this.getX()+13, this.getY()+30);
                    targets = (List<Damageable>)Yhitbox.getIntersectingActors(Damageable.class);
                    if(enhancedSwings)
                    {
                        SoundManager.playSound("swordUltimateSound.mp3");
                        getWorld().addObject(new Slash(spd, damage, this, false), getX(), getY());
                    }
                    break;
                default:
                    targets = null;
            }
            for(Damageable a : targets)
            {
                if(!(a instanceof Player)){
                    a.damage(damage);
                    if(a instanceof Enemy){
                        Enemy e = (Enemy)a;
                        e.setDamagedState(true);
                    }
                }
            }
            if(enhancedSwings)
            {
                hitCount++;
                if(hitCount >= 7)
                {
                    hitCount = 0;
                    enhancedSwings = false;
                    cooldownTimer = ultimateCooldown;
                }
            }
        }
    }

    /**
     * Returns the direction the player is facing
     * 
     * @return facing   The players current facing direction
     */
    public static String getFacing (){
        return facing;
    }

    /**
     * Returns the current attack damage of the player
     */
    public int getAttackDmg(){
        return attackDmg;
    }

    /**
     * Method that allows player to shoot a bullet
     * 
     * @param targetX    The x component of the target's direction
     * @param targetY    The y component of the target's direction
     */
    private void shoot(int targetX, int targetY, int dmg) {
        Bullet bullet = new Bullet(4, dmg, this, targetX, targetY);
        getWorld().addObject(bullet, getX(), getY());
    }

    /**
     * Method that puts player in power up state for a set duration
     */
    public void activatePowerUp() {
        isPoweredUp = true;
        powerUpStartTime = System.currentTimeMillis();
        aura.makeVisible();
    }

    /**
     * Method that manages animation when the player dies, or when the player's hp reaches 0.
     */

    public void deathAnimation()
    {
        if(!death)
        {
            frame = 0;
            speed = 0;
            death = true;
        }
        frame = (frame+1)%(dying.getAnimationLength());
        setImage(dying.getFrame(frame));
        if(frame == (dying.getAnimationLength()-1))
        {
            die();
        }
        if(this.getWorld() == null) Greenfoot.setWorld(new EndScreenWorld());
    }

    /**
     * Method that manages whether the aura appears or not
     */
    private void checkPowerUpStatus() {
        if (isPoweredUp && (System.currentTimeMillis() - powerUpStartTime >= 8000)) {
            isPoweredUp = false;
            aura.makeInvisible();
        } else if (isPoweredUp) {
            aura.makeVisible();
        }
    }

    public void die() {
        getWorld().removeObject(this);
    }

    /**
     * Returns current hp
     * 
     * @return hp  The current hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Setter method for hp
     * 
     * @param health    The hp of player
     */
    public void setHp(int health){
        hp = health;
    }

    /**
     * Returns the player's x-position in world
     * 
     * @return   The player's x-position in world
     */
    public static int returnX()
    {
        return x;
    }

    /**
     * Returns the player's y-position in world
     * 
     * @return   The player's y-position in world
     */
    public static int returnY()
    {
        return y;
    }

    /**
     * Method that manages the animation when player is not moving
     */
    public void idle()
    {
        switch(facing)
        {
            case "right":
                setImage(right.getFrame(0));
                break;
            case "left":
                setImage(left.getFrame(0));
                break;
            case "up":
                setImage(up.getFrame(0));
                break;
            case "down":
                setImage(down.getFrame(0));
                break;
        }
    }

    /**
     * Method for player to use ultimate ability, one for both sword and staff
     */
    public void useUltimate() {
        String key = Keyboard.getCurrentKey();
        if("q".equals(key)){
            switch(weapon)
            {
                case "staff":
                    SoundManager.playSound("staffSound.mp3");
                    for (int i = 0; i < 15; i++) {
                        Bullet bullet = new Bullet(5, 10, this); // Create a new projectile
                        getWorld().addObject(bullet, getX(), getY()); // Add projectile to the world
                        bullet.setRotation(24*i); // Set the direction
                    }
                    cooldownTimer = ultimateCooldown;
                    break;
                case "sword":
                    if(!enhancedSwings){
                        enhancedSwings = true;
                        hitCount = 0;
                    }

                    break;
            }
        }
    }

    /**
     * Returns the current weapon player is holding
     * 
     * @return weapon   The player's current weapon
     */
    public String getCurrentWeapon()
    {
        return weapon;
    }

    /**
     * Method that manages when player switches in between weapons
     */
    public void switchWeapon()
    {
        index++;
        frame = 0;
        String nextWeapon = weaponList[(index%weaponList.length)];
        weapon = nextWeapon;
        if(weapon.equals("sword"))
        {
            up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 10);
            switch(facing)
            {
                case "up":
                    setImage(up.getFrame(frame));
                    break;
                case "down":
                    setImage(down.getFrame(frame));
                    break;
                case "right":
                    setImage(right.getFrame(frame));
                    break;
                case "left":
                    setImage(left.getFrame(frame));
                    break;
            }
        }
        else if(weapon.equals("staff"))
        {
            up = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 11, 1, 9, 64, 64, 10);
            switch(facing)
            {
                case "up":
                    setImage(up.getFrame(frame));
                    break;
                case "down":
                    setImage(down.getFrame(frame));
                    break;
                case "right":
                    setImage(right.getFrame(frame));
                    break;
                case "left":
                    setImage(left.getFrame(frame));
                    break;
            }
        }
    }

    /**
     * Method that manages the attack animation of the player
     */
    public void attackAnimation()
    {
        switch(weapon)
        {
            case "sword": // if holding sword
                if(frame == (swingingUp.length-1)) // if animation reaches the end.
                {
                    SoundManager.playSound("swordSound.mp3");
                    inAttack = false;
                    frame = 0;
                    switch(facing)
                    {
                        case "up":
                            setImage(up.getFrame(frame));
                            break;
                        case "down":
                            setImage(down.getFrame(frame));
                            break;
                        case "right":
                            setImage(right.getFrame(frame));
                            break;
                        case "left":
                            setImage(left.getFrame(frame));
                            break;
                    }
                    mouseClick = false;
                    return;
                }
                switch(facing)
                {
                    case "up":
                        setImage(swingingUp[frame]);
                        break;
                    case "down":
                        setImage(swingingDown[frame]);
                        break;
                    case "left":
                        setImage(swingingLeft[frame]);
                        break;
                    case "right":
                        setImage(swingingRight[frame]);
                        break;
                }
                if(acts % (atkSpd/2) == 0)
                {
                    frame = (frame+1)%(swingingRight.length);
                }
                break;
            case "staff":
                if(frame == (staffUp.getAnimationLength()-1)) // if animation reaches the end.
                {
                    SoundManager.playSound("staffSound.mp3");
                    inAttack = false;
                    frame = 0;
                    switch(facing)
                    {
                        case "up":
                            setImage(up.getFrame(frame));
                            break;
                        case "down":
                            setImage(down.getFrame(frame));
                            break;
                        case "right":
                            setImage(right.getFrame(frame));
                            break;
                        case "left":
                            setImage(left.getFrame(frame));
                            break;
                    }
                    mouseClick = false;
                    break;
                }
                switch(facing)
                {
                    case "up":
                        setImage(staffUp.getFrame(frame));
                        break;
                    case "down":
                        setImage(staffDown.getFrame(frame));
                        break;
                    case "left":
                        setImage(staffLeft.getFrame(frame));
                        break;
                    case "right":
                        setImage(staffRight.getFrame(frame));
                        break;
                }

                if(acts % (atkSpd/4) == 0)
                {
                    frame = (frame+1)%(staffUp.getAnimationLength());
                }
                break;
            case "bow":
                break;
        }
    }

    /**
     * Method that adds hp to player
     * 
     * @param hp   The amount of hp to be added
     */
    public void addMaxHp(int hp){
        maxHp += hp;
        StatsUI.updateMaxHp(maxHp);
    }

    /**
     * Method that adds attack damage to player
     * 
     * @param dmg   The amount of damage to be added
     */
    public void addAttackDamage(int dmg){
        attackDmg += dmg;
        StatsUI.updateAtkDmg(attackDmg);
    }

    /**
     * Method that adds speed to the player
     * 
     * @param spd   The amount of speed to be added
     */
    public void addSpeed(int spd){
        normalSpeed += spd;
        powerUpSpeed = normalSpeed + (int)(normalSpeed * 0.3);
        StatsUI.updateSpd(normalSpeed);
    }

    /**
     * Method that loads the player's current data
     */
    public void loadPlayerData(String [] data){
        attackDmg = Integer.valueOf(data[1]);
        speed = Integer.valueOf(data[2]);
        maxHp = Integer.valueOf(data[3]);
        hp = Integer.valueOf(data[4]);
        coin = Integer.valueOf(data[5]);
    }

    /**
     * Method that returns the max health of player
     * 
     * @return maxHp   the max health of player
     */
    public int getMaxHp(){
        return maxHp;
    }

    /**
     * Method that returns the speed of the player
     * 
     * @return normalSpeed   The speed of the player
     */
    public int getSpeed(){
        return normalSpeed;
    }

    /**
     * Method that returns the amount of coins the player has in possession
     * 
     * @return coin    The amount of coins the player has
     */
    public int getCoin(){
        return coin;
    }

    /**
     * Method that adds to the number of coins player has
     */
    public void addCoin(){
        coin++;
        StatsUI.updateCoin(coin);
    }

    /**
     * Method that removes coins from player when they purchase from shop
     */
    public void removeCoin(int num){
        coin-= num;
        StatsUI.updateCoin(coin);
    }

    public String toString(){
        return attackDmg +"~" + normalSpeed  + "~" + maxHp + "~" + hp + "~" + coin;
    }

}
