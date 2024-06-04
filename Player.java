import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Entity
{
    private boolean isPoweredUp = false;
    private long powerUpStartTime = 0;
    private int normalSpeed;
    private int powerUpSpeed;
    private int normalShootingInterval;
    private int powerUpShootingInterval;
    private int shootingTimer;

    private double speed;
    private int xOffset, yOffset, atkSpd = 10, frame = 0, acts = 0, index = 0;
    private List<Enemy> slashableEnemies; //IMPLEMENT COLLISION BOX
    private HiddenBox Xhitbox, Yhitbox;

    private static int x, y; // location of the Player.
    //Cooldowns, durations:
    private double timeForStaff = 600.0, remainingCds = 0;
    //Moving
    private Animation right,left,down,up, staffUp, staffDown, staffLeft, staffRight;
    private GreenfootImage[] swingingUp = new GreenfootImage[6],swingingDown = new GreenfootImage[6],swingingLeft = new GreenfootImage[6],swingingRight = new GreenfootImage[6];
    private static String facing = "right",weapon = "sword";
    private boolean inAttack = false, mouseClick, hitboxLoaded = false;
    private static String[] weaponList = new String[2];

    private Aura aura;

    private int ultimateCooldown = 300;
    private int cooldownTimer = 0;

    private boolean isDashing = false;
    private long dashCooldownTime = 0;
    private int dashFrames = 0;
    private double dashDx = 0, dashDy = 0;

    public Player() {
        super(Team.ALLY, 100);
        normalSpeed = 5;
        powerUpSpeed = 8;
        normalShootingInterval = 50;
        powerUpShootingInterval = 30;
        shootingTimer = 0;
        hp = 100;
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -37, Color.GREEN, Color.BLACK, false, Color.YELLOW, 1);
        //Animation spritesheet cutter using Mr Cohen's animation class: 
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 10);

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
        //Start at frame 0
        setImage(up.getFrame(0));
        collisionBox = new CollisionBox(30, 20, Box.SHOW_BOXES, this, 0, 20); // THIS NEEDS TO BE MOVED TO ENTITY. FOR TESTING ONLY RN
    }

    public void act() {
        x = getX();
        y = getY();

        if (isDashing) {
            dashFrames++;
            move(dashDx, dashDy, 10);
            if (dashFrames >= 10) {
                isDashing = false;
                dashCooldownTime = System.currentTimeMillis();
            }
        } else {
            if (System.currentTimeMillis() - dashCooldownTime >= 1000) {
                if (Greenfoot.isKeyDown("shift")) {
                    dash();
                }
            }
        }

        if (cooldownTimer > 0) {
            cooldownTimer--; // Decrement cooldown timer for ult
        }

        if (weapon.equals("staff") && cooldownTimer <= 0) {
            useStaffUltimate();
        }

        if(remainingCds != 0) // 1 minute
        {
            remainingCds--;
        }
        else
        {
            String key = Greenfoot.getKey();
            if("r".equals(key))
            {
                switchWeapon();
            }
        }
        if(this.getCurrentWeapon().equals("staff"))
        {
            timeForStaff--;
            // continue timer, up to 10 seconds per time whether you end early or not.
        }
        else if(timeForStaff < 600 && this.getCurrentWeapon().equals("sword"))
        {
            timeForStaff = timeForStaff+(600/remainingCds); 
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
        if(!flung){
            if(!inAttack )
            {
                movePlayer();
            }
            else
            {
                attackAnimation();
                if(this.getCurrentWeapon().equals("staff"))
                {
                    handleShooting();
                }
                else
                {
                    attack(10);
                }
            }
        }

        // Add other behaviours here (like checking for collisions, etc.)
        checkPowerUpStatus();

        // if still in staff and not middle of attack animation,
        if(timeForStaff < 0 && !inAttack)
        {
            switchWeapon(); // automatically switch
            remainingCds = 3600; // restart Cooldown once staff is expired.
            timeForStaff = 600; // reset Timer
        }
        acts++;
        super.act();
    }

    public void addedToWorld(World world) {
        super.addedToWorld(world);

        aura = new Aura(this);
        world.addObject(aura, getX(), getY());
    }

    private void movePlayer() {
        if(isPoweredUp)
        {
            speed = powerUpSpeed;

        }
        else
        {
            speed = normalSpeed;
        }
        double speed = isPoweredUp ? powerUpSpeed : normalSpeed;
        int dx = 0, dy = 0; //Change in X and Y based on movement
        int x;// Animation Speed base on a factor of variable X
        if(isPoweredUp)
        {
            x = powerUpSpeed;
        }
        else
        {
            x = normalSpeed;
        }
        if(acts%(atkSpd-(x)/2) == 0)
        {
            frame = (frame+1)%(up.getAnimationLength()); 
        }
        acts++;
        if (Greenfoot.isKeyDown("w")) {
            dy -= speed;
            setImage(up.getFrame(frame));
            facing = "up";
        }if (Greenfoot.isKeyDown("s")) {
            dy += speed;
            setImage(down.getFrame(frame));
            facing = "down";
        }
        if (Greenfoot.isKeyDown("a")) {
            dx -= speed;
            setImage(left.getFrame(frame));
            facing = "left";
        }
        if (Greenfoot.isKeyDown("d")) {
            dx += speed;
            setImage(right.getFrame(frame));
            facing = "right";
        }

        move(dx, dy, speed);
        if(acts % 2 == 0)
        {
            frame = (frame+1)%(right.getAnimationLength());
        }
    }

    public void move(double dx, double dy, double spd)
    {
        double vectorMagnitude = Math.sqrt(dx*dx + dy*dy);
        if(vectorMagnitude == 0)
        {
            return;
        }
        double xComponent = dx/vectorMagnitude * spd;
        double yComponent = dy/vectorMagnitude * spd;
        System.out.println(Math.sqrt(xComponent*xComponent + yComponent*yComponent));
        setLocation(x + xComponent, y + yComponent);
    }

    private void dash() {
        if (isDashing) return; // Prevent dashing again if already dashing

        isDashing = true;
        dashFrames = 0;
        dashDx = 0;
        dashDy = 0;

        double dashSpeed = 20.0; // Speed per frame for a total distance of 200 over 10 frames
        switch (facing) {
            case "up":
                dashDy = -dashSpeed;
                break;
            case "down":
                dashDy = dashSpeed;
                break;
            case "left":
                dashDx = -dashSpeed;
                break;
            case "right":
                dashDx = dashSpeed;
                break;
        }
    }

    private void handleShooting(){
        shootingTimer++;
        int shootingInterval = isPoweredUp ? powerUpShootingInterval : normalShootingInterval;
        if (Greenfoot.mouseClicked(null) && shootingTimer >= shootingInterval) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                shoot(mouseX, mouseY);
                shootingTimer = 0;
            }
        }
    }

    private void attack(int damage) {
        if(frame ==5)
        {
            List<Actor> targets;
            switch(facing)
            {
                case "right":
                    //Xhitbox.makeVisible();
                    getWorld().addObject(Xhitbox, this.getX()+20, this.getY());
                    targets = Xhitbox.getIntersectingActors(Actor.class);
                    break;
                case "left":
                    //Xhitbox.makeVisible();
                    getWorld().addObject(Xhitbox, this.getX()-20, this.getY());
                    targets = Xhitbox.getIntersectingActors(Actor.class);
                    break;
                case "up":
                    //Yhitbox.makeVisible();
                    getWorld().addObject(Yhitbox, this.getX()+13, this.getY()-30);
                    targets = Yhitbox.getIntersectingActors(Actor.class);
                    break;
                case "down":
                    //Yhitbox.makeVisible();
                    getWorld().addObject(Yhitbox, this.getX()+13, this.getY()+30);
                    targets = Yhitbox.getIntersectingActors(Actor.class);
                    break;
                default:
                    targets = null;
            }
            for(Actor a : targets)
            {
                if(a instanceof Enemy)
                {
                    Enemy e = (Enemy)a;
                    /*
                    if(e.damaged() == false)
                    {
                    e.damage(damage);
                    e.setDamagedState(true);
                    }*/
                    e.damage(damage);
                    //e.setDamagedState(true);
                }
            }
        }

        /** OLD CODE
        for (Enemy enemy : slashableEnemies) {
        if(frame == 5 && enemy.damaged() == false) // So it doesn't appear like it died before sword hits.
        {   
        enemy.damage(damage);
        enemy.setDamagedState(true);
        }
        }*/
    }

    public static String getFacing (){
        return facing;
    }

    private void shoot(int targetX, int targetY) {
        Bullet bullet = new Bullet(2, 20, this,targetX, targetY);
        getWorld().addObject(bullet, getX(), getY());
    }

    public void activatePowerUp() {
        isPoweredUp = true;
        powerUpStartTime = System.currentTimeMillis();
        aura.makeVisible();
    }

    public void deathAnimation()
    {
        getWorld().removeObject(this);
    }

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

    public int getHp() {
        return hp;
    }

    public static int returnX()
    {
        return x;
    }

    public static int returnY()
    {
        return y;
    }

    public void idle()
    {

    }

    public void useStaffUltimate() {
        String key = Greenfoot.getKey();
        if("q".equals(key)){
            for (int i = 0; i < 15; i++) {
                Bullet bullet = new Bullet(5, 10, this); // Create a new projectile
                getWorld().addObject(bullet, getX(), getY()); // Add projectile to the world
                bullet.setRotation(Greenfoot.getRandomNumber(360)); // Set the direction
            }
            cooldownTimer = ultimateCooldown;
        }
    }

    public String getCurrentWeapon()
    {
        return weapon;
    }

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
            staffUp = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 4, 1, 8, 64, 64);
            staffLeft = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 5, 1, 8, 64, 64);
            staffDown = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 6, 1, 8, 64, 64);
            staffRight = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 7, 1, 8, 64, 64,7);
        }
    }

    /**
     * 
     */
    public void attackAnimation()
    {
        switch(weapon)
        {
            case "sword": // if holding sword
                if(frame == (swingingUp.length-1)) // if animation reaches the end.
                {
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
                if(acts % atkSpd == 0)
                {
                    frame = (frame+1)%(swingingRight.length);
                }
                break;
            case "staff":
                if(frame == (staffUp.getAnimationLength()-1)) // if animation reaches the end.
                {
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
                if(acts % atkSpd == 0)
                {
                    frame = (frame+1)%(staffUp.getAnimationLength());
                }
                break;
            case "bow":
                break;
        }
    }
}

