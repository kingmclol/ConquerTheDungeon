import greenfoot.*;
import java.util.*;
/**
 * This class is designed to create a parabolic like projectile das
 */
public class ParabolicProjectile  
{
    // number of points in the path
    private int numOfPoints;
    // List of x value points
    private ArrayList<Double> pathPointsX = new ArrayList<Double>();
    // List of y value points
    private ArrayList<Double> pathPointsY = new ArrayList<Double>();
    // Horizontal distance
    private int range;
    // Vertical drop
    private int drop;
    // Max height of the parabola
    private int maxHeight;
    private Actor object;
    private int index = 0;
    public ParabolicProjectile(int range, int drop, int maxHeight){
        this.object = object;
        this.range = range;
        this.drop = -drop;
        this.maxHeight = maxHeight;
        numOfPoints = range;
        createPath();
    }

    private void createPath(){
        // Math for the parabola equation
        // a and r are placeholder variables to make equation more neat
        double r = Math.sqrt(maxHeight); 
        double a = (Math.sqrt(-(drop - maxHeight)) + r)/range; 
        double prevY = 0.0; // starting val used to calc rate of change
        //-(ax-r)^{2}+maxHeight is the equation

        for (int i = 1; i < numOfPoints + 1; i ++){
            pathPointsX.add((double)range/numOfPoints);
            double yVal = (-(Math.pow((a*(i *((double)range/numOfPoints)) - r), 2)) +(double) maxHeight);
            pathPointsY.add(yVal - prevY); // finding rate of change between next point and prev point
            // these points can be added to a current y value in succession to follow a parabola
            prevY = yVal;
        }
        
        
    }
    public int getNumOfPoints(){
        return numOfPoints;
    }
    public boolean pathDone(){
        return numOfPoints == index;
    }
    public ArrayList<Double> getXPoints(){
        return pathPointsX;
    }
    public ArrayList<Double> getYPoints(){
        return pathPointsY;
    }
    public Vector nextCoord(){
        index++;
        return new Vector(pathPointsX.get(index -1), pathPointsY.get(index -1));
    }
 


    

}
