import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * This class implements the behavior of predators.
 * Predators can eat preys. All predators have common 
 * characteristics such as a maximum age, a breeding age,
 * a maximum food level.
 *
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux 
 * 
 * @version 2021.02.28
 */

public  abstract class Predator extends Animal

{
    // The age at which a predator can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a predator can live.
    private static final int MAX_AGE = 150;
    //maximum food level a predator can have
    private static final int MAX_FOOD_VALUE = 18;
     // The predator's age.
    private int age;
    // The predator's food level, which is increased by eating preys.
    private int foodLevel;
    /**
     *Creates a new predator.
     * A predator can be created as a new born 
     * (age zero and not hungry) or with a random age and hunger. 
     * 
     * @param randomAge If true, the predator will have random age and hunger.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Predator (boolean randomAge, Field field, Location location){
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAX_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = MAX_FOOD_VALUE;
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge(){
        age++;
        if(age>MAX_AGE){
            setDead();
        }
    }

    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0){
            setDead();
        }
    }

    /**
     * Look for preys adjacent to the current location.
     * Only the first live prey is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object entity = field.getObjectAt(where);
            if(entity instanceof Prey) {
                Prey prey = (Prey) entity;
                if(prey.isAlive()) { 
                    prey.setDead();
                    feed(prey);
                    return where;
                }
            }
        }
        return null;   
    }
    /**
     * Gets the food value that a predator receives
     * when eating a prey. Increments the food level
     * of the predator with the food value of the prey 
     * if it is inferior to the maximum food level.
     * If the food level goes over the maximum value,
     * then the food level is set to the maximum value
     */

    private void feed(Prey prey)
    {
        if (prey.getFoodValue()+foodLevel<=MAX_FOOD_VALUE){foodLevel+=prey.getFoodValue();}
        else {foodLevel = MAX_FOOD_VALUE;}
    }

    /**
     * A predatpr can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
