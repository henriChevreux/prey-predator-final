import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * Write a description of class Prey here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public abstract class Prey extends Animal
{
    // The age at which a lion can start to breed.
    protected static final int BREEDING_AGE = 5;
    // The age to which a lion can live.
    protected static final int MAX_AGE = 40;
    
    // The food value of a single rabbit. In effect, this is the
    // number of steps a lion can go before it has to eat again.
    protected static final int DEFAULT_FOOD_VALUE = 5;
    
    protected static final int MAX_FOOD_VALUE = 8;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
        // The lion's age.
    protected int age;
    // The lion's food level, which is increased by eating pangolins.
    protected int foodLevel;
    
    protected static int foodValue;
    
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

    protected static int getFoodValue(){return foodValue;}
    
    protected static void resetFoodValue(){foodValue = DEFAULT_FOOD_VALUE;}
    
    protected void feed(Plant plant)
    {
        if (plant.getSize()+foodLevel<=MAX_FOOD_VALUE){
            foodLevel+=plant.getSize();
        }
        else {foodLevel = MAX_FOOD_VALUE;}
    }
    
    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
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
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public abstract void giveBirth(List<Animal> newPreys);
    
    public static void setFoodValue(int newFoodValue){foodValue = newFoodValue;}
}
