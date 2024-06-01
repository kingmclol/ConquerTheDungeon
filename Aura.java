import greenfoot.*;

public class Aura extends SuperSmoothMover {
    private Actor owner;
    private boolean visible;

    public Aura(Actor owner) {
        this.owner = owner;
        this.visible = false;
        updateVisibility();
    }

    public void act() {
        setLocation(owner.getX(), owner.getY());
        updateVisibility();
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



