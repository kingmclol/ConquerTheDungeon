import greenfoot.*;

public class Aura extends SuperSmoothMover {
    private Actor owner;
    private boolean visible;
    private Animation animate;
    private int frame = 0, offset = 80, acts = 0;
    public Aura(Actor owner) {
        this.owner = owner;
        this.visible = false;
        animate = Animation.createAnimation(new GreenfootImage("Aura.png"), 0, 1, 4, 205, 220, 0, 150);
        setImage(animate.getFrame(0));
        updateVisibility();
    }

    public void act() {
        setLocation(owner.getX()-(offset/5), owner.getY()-offset);
        animate();
        updateVisibility();
        acts++;
    }
    
    /**
     * Animate.
     */
    public void animate()
    {
        if(acts%3 == 0)
        {
            frame = (frame+1) % (animate.getAnimationLength());
        }
        setImage(animate.getFrame(frame));
    }

    public void makeVisible() {
        visible = true;
    }

    public void makeInvisible() {
        visible = false;
    }

    private void updateVisibility() {
        if (visible) {
            getImage().setTransparency(255); // Fully visible
        } else {
            getImage().setTransparency(0); // Fully invisible
        }
    }
}



