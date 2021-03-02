import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * This class implements the behavior of all the preys 
 * in the simulator. It was assumed that all preys
 * have the same maximum age, breeding age, food value
 * and maximum food value.
 *
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux 
 * @version 2021.02.28
 */

public abstract class Prey extends Animal
{
    // The age at which a prey can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a prey can live.
    private static final int MAX_AGE = 40;
    //The default food value a prey gives to predators
    //when they eat them.
    protected static final int DEFAULT_FOOD_VALUE = 9;
    //maximum food level a prey can have
    private static final int MAX_FOOD_VALUE = 8;
    // The prey's age.
    private int age;
    // The prey's food level, which is increased by eating plants.
    private int foodLevel;
    // The food value given to a predator when he 
    //eats a single prey.
    protected static int foodValue;    

    /**
     *Creates a new prey.
     * A prey can be created as a new born 
     * (age zero and not hungry) or with a random age and hunger. 
     * Its food value is initiated with a default value,
     * but that value can change depending on weather conditions
     * @param randomAge If true, the prey will have random age and hunger.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Prey(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAX_FOOD_VALUE);

        }
        else {
            age = 0;
            foodLevel = MAX_FOOD_VALUE;
        }
        foodValue = DEFAULT_FOOD_VALUE;
    }

    /**
     * Look for plants adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findPlant(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object entity = field.getObjectAt(where);
            if(entity instanceof Plant) {
                Plant plant = (Plant)entity;
                if(plant.isAlive()) { 
                    plant.setDead();
                    feed(plant);
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Returns the food value of a prey.
     * @return foodValue the int food value of a prey.
     */
    protected static int getFoodValue(){return foodValue;}

    /**
     * Gets the food value that a prey receives
     * when eating a plant. Increments the food level
     * of the prey with the food value of the plant.
     * If the food level goes over the maximum value,
     * then the food level is set to the maximum value
     */    
    private void feed(Plant plant)
    {
        if (plant.getSize()+foodLevel<=MAX_FOOD_VALUE){
            foodLevel+=plant.getSize();
        }
        else {foodLevel = MAX_FOOD_VALUE;}
    }

    /**
     * A prey can breed if it has reached the breeding age.
     * @return true if the prey can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Make this prey more hungry. This could result in the prey's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Increase the age.
     * This could result in the prey's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Sets the food value of the prey to a 
     * new food value when there is fog. This method
     * is called in the Weatherbox class.
     */
    public static void setFoodValue(int newFoodValue){
        foodValue = newFoodValue;
    }

    /**
     * resets the food value to its default value 
     * when the weather status is not "fog".
     */
    protected static void resetFoodValue(){
        foodValue = DEFAULT_FOOD_VALUE;
    }
}
