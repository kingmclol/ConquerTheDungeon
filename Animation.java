import greenfoot.*;
public class Animation  
{
    // Class variables
    private GreenfootImage[] rightImages;
    private GreenfootImage[] leftImages;

    private boolean facingRight;

    // Please give RIGHT-FACING images
    public Animation (GreenfootImage[] images){
        // initialize both arrays to the same size:
        rightImages = new GreenfootImage[images.length];
        leftImages = new GreenfootImage[images.length];
        // Loop through and load up both arrays
        for (int i = 0; i < images.length; i++){
            // Use the constructor to make a new copy and avoid
            // accidentally pointing at the same image
            rightImages[i] = new GreenfootImage(images[i]);
            // Copy the right image to make the left image for each frame...
            leftImages[i] = new GreenfootImage(rightImages[i]);
            // .. and then mirror it horizontally so it faces left
            leftImages[i].mirrorHorizontally();
        }
        // The Actor that owns this will start facing right
        facingRight = true;
    }

    public void faceLeft(){
        facingRight = false;
    }

    public void faceRight(){
        facingRight = true;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public int getAnimationLength(){
        return rightImages.length;
    }

    public GreenfootImage getFrame (int frame){
        GreenfootImage result;
        if (facingRight){
            return rightImages[frame];
        }
        return leftImages[frame];
    }

    /**
     * Grabs a part of a sprite sheet (or any other GreenfootImage) and returns it as a new
     * GreenfootImage. The sprite sheet must be larger than the resulting image.
     *
     * @param spriteSheet   the larger spritesheet to pull images from
     * @param xPos  the x position (of the left) of the desired spot to draw from
     * @param yPos  the y position (of the top) of the desired spot to draw from
     * @param frameWidth     the horizontal tile size
     * @param frameHeight    the vertical tile size
     * @return GreenfootImage   the resulting image
     */
    private static GreenfootImage getSlice (GreenfootImage spriteSheet, int xPos, int yPos, int frameWidth, int frameHeight)
    {
        if (frameWidth > spriteSheet.getWidth() || frameHeight > spriteSheet.getHeight()){

            System.out.println("Error in AnimationManager: GetSlice: You specified a SpriteSheet that was smaller than your desired output");
            return null;
        }
        GreenfootImage small = new GreenfootImage (frameWidth-8, frameHeight);
        // negatively offset the larger sprite sheet image so that a correct, small portion
        // of it is drawn onto the smaller, resulting image.
        small.drawImage (spriteSheet, -xPos+4, -yPos);
        return small;
    }
    private static GreenfootImage getSlice (GreenfootImage spriteSheet, int xPos, int yPos, int frameWidth, int frameHeight, int offset)
    {
        if (frameWidth > spriteSheet.getWidth() || frameHeight > spriteSheet.getHeight()){

            System.out.println("Error in AnimationManager: GetSlice: You specified a SpriteSheet that was smaller than your desired output");
            return null;
        }
        GreenfootImage small = new GreenfootImage (frameWidth-8, frameHeight);
        // negatively offset the larger sprite sheet image so that a correct, small portion
        // of it is drawn onto the smaller, resulting image.
        small.drawImage (spriteSheet, -xPos-offset, -yPos);
        return small;
    }
    private static GreenfootImage getSlice (GreenfootImage spriteSheet, int xPos, int yPos, int frameWidth, int frameHeight, int xOffset, int yOffset)
    {
        if (frameWidth > spriteSheet.getWidth() || frameHeight > spriteSheet.getHeight()){

            System.out.println("Error in AnimationManager: GetSlice: You specified a SpriteSheet that was smaller than your desired output");
            return null;
        }
        GreenfootImage small = new GreenfootImage (frameWidth-8, frameHeight);
        // negatively offset the larger sprite sheet image so that a correct, small portion
        // of it is drawn onto the smaller, resulting image.
        small.drawImage (spriteSheet, -xPos-xOffset, -yPos-yOffset);
        return small;
    }

    protected static Animation createAnimation(GreenfootImage spriteSheet, int startRow, int numRows, int numFrames, int width, int height){
        GreenfootImage[] result = new GreenfootImage[numFrames];
        for (int frame = 0; frame < numFrames; frame++){
            //System.out.println(spriteSheet + " Row: " + row + " dir: " + dir + " frame: " + frame);
            result[frame] = new GreenfootImage (getSlice(spriteSheet, frame * width, startRow * height, width, height));
        }
        return new Animation (result);
    }
    protected static Animation createAnimation(GreenfootImage spriteSheet, int startRow, int numRows, int numFrames, int width, int height, int offset){
        GreenfootImage[] result = new GreenfootImage[numFrames];
        for (int frame = 0; frame < numFrames; frame++){
            //System.out.println(spriteSheet + " Row: " + row + " dir: " + dir + " frame: " + frame);
            result[frame] = new GreenfootImage (getSlice(spriteSheet, frame * width, startRow * height, width, height, offset));
        }
        return new Animation (result);
    }
    protected static Animation createAnimation(GreenfootImage spriteSheet, int startRow, int numRows, int numFrames, int width, int height, int offset, int transparency){
        GreenfootImage[] result = new GreenfootImage[numFrames];
        for (int frame = 0; frame < numFrames; frame++){
            //System.out.println(spriteSheet + " Row: " + row + " dir: " + dir + " frame: " + frame);
            result[frame] = new GreenfootImage (getSlice(spriteSheet, frame * width, startRow * height, width, height, offset));
            result[frame].setTransparency(transparency);
        }
        return new Animation (result);
    }
    protected static Animation createAnimation(GreenfootImage spriteSheet, int startRow, int numRows, int numFrames, int width, int height, int xOffset, int yOffset, int transparency, int scaleFactor){
        GreenfootImage[] result = new GreenfootImage[numFrames];
        for (int frame = 0; frame < numFrames; frame++){
            //System.out.println(spriteSheet + " Row: " + row + " dir: " + dir + " frame: " + frame);
            result[frame] = new GreenfootImage (getSlice(spriteSheet, frame * width, startRow * height, width, height, xOffset, yOffset));
            result[frame].setTransparency(transparency);
            result[frame].scale(result[frame].getWidth()*scaleFactor/100, result[frame].getHeight()*scaleFactor/100);
        }
        return new Animation (result);
    }

}
