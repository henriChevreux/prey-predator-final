import java.util.List;
import java.util.Random;
/**
 * This class implements the behavior of plants.
 * Plants have a maximum size, a growth rate and the food 
 * value they give when they are eaten by preys corresponds to 
 * their sizes.
 *
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux 
 * 
 * @version 2021.02.28
 */
public class Plant 
{
    //The plant's field
    private Field field;
    //The plant's location
    private Location location;
    //the plant's default growth rate
    private static final double DEFAULT_GROWTH_RATE = 0.08;
    //maximum size of a plant 
    private static final int MAX_SIZE = 6;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //The current size of the plant
    private int size;    
    // Whether the plant is alive or not.
    private boolean alive;
    //the plant's growth rate 
    private static double plantGrowthRate;

    /**
     *Creates a new plant.
     * A plant can be created as a new born 
     * (size zero) or with a random size 
     * 
     * @param randomSize If true, the plant will have random size.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(boolean randomSize, Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        alive = true;
        if(randomSize){
            size = rand.nextInt(MAX_SIZE);
        } else{
            size = 0;
        }
        plantGrowthRate = DEFAULT_GROWTH_RATE;
    }

    /**
     * This is what the plant does most of the time - 
     * it is static, doesn't move and it can grow in size.
     * @param newPlants A list to return newly born ants.
     */

    public void act (){
        if (isAlive()){
            growPlant();
        }
    }

    /**
     * A plant can grow if its current size is inferior
     * to its maximum size, and if a randomly generated number 
     * is inferior to its growth rate. If a plant has a bigger size 
     * than its maximum size, then it dies.
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

    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The animal's new location.
     */

    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Checks whether a plant is alive.
     * @return true if plant is alive.
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    public void setDead(){
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    /**
     * Gets the size of the plant.
     * @return size the int size value of the plant.
     */
    protected int getSize(){
        return size;
    }
    /**
     * Gets the growth rate of the plant.
     * @return plantGrowthRate the double growth rate of all plants between 0 and 1.
     */
    protected double getPlantGrowthRate(){
        return plantGrowthRate;
    }
    /**
     * Divides by two the growth rate of plants.
     * This is triggered when it is raining.
     */
    public static void divideGrowthRate(){
        plantGrowthRate = plantGrowthRate/2;
    }
    /**
     * Resets the growth rate of plants.
     */
    public static void resetGrowthRate(){
        plantGrowthRate = DEFAULT_GROWTH_RATE;
    }
}
