import java.util.List;
import java.util.Random;
/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plant 
{
    private Field field;

    private Location location;

    protected static final double DEFAULT_GROWTH_RATE = 0.07;

    //max size of a plant in centimeters.
    private static final int MAX_SIZE = 10;

    private static final Random rand = Randomizer.getRandom();

    private int size;    

    private boolean alive;
    
    protected static double plantGrowthRate;

    /**
     * Constructor for objects of class Plant
     */
    public Plant(boolean randomSize, Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        alive = true;

        size = rand.nextInt(MAX_SIZE);
        plantGrowthRate = DEFAULT_GROWTH_RATE;
    }

    public void act (List<Plant> newPlants){
        if (isAlive()){
            growPlant();
    }
}

    /**
     * we assume that the foodvalue of a plant is equal to its size
     */

    private void growPlant(){
        if(size<=MAX_SIZE && rand.nextDouble() <= plantGrowthRate)
        {
            size++;
        }  
        if (size > MAX_SIZE){
            size = 0;
            setDead();
        } 
    }

    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    public boolean isAlive(){
        return alive;
    }

    public void setDead(){
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected int getSize(){
        return size;
    }
    protected double getPlantGrowthRate(){
        return plantGrowthRate;
    }
    public static void divideGrowthRate(){
        plantGrowthRate = plantGrowthRate/2;
    }
    public static void resetGrowthRate(){
        plantGrowthRate = DEFAULT_GROWTH_RATE;
    }
    
    
}
