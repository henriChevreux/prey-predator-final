import java.util.List;
import java.util.Iterator;

/**
 *This class implements the behavior of ants.
 * Ants are preys. They have all the 
 * characteristics of preys, but have a specific
 * breeding probability, and maximum litter size.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux 
 * @version 2021.02.28
 */
public class Ant extends Prey
{
    // The likelihood of an ant breeding.
    public static double BREEDING_PROBABILITY = 0.3201;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    /**
    Creates an ant. An ant is a prey and thus calls
     * the constructor from the Prey superclass.
     * A monkey can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * These attributes are defined in the Prey class.
     * 
     * @param randomAge If true, the ant will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Ant(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * This is what the ant does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newAnts A list to return newly born ants.
     */
    public void act(List<Animal> newAnts)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newAnts);            
            // Try to move into a free location.
            Location newLocation = findPlant();
            if (newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
                if (newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
            else {
                setLocation(newLocation);
            }
        }
    }

    /**
     * Check whether or not this monkey is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnts A list to return newly born ants.
     */
    public void giveBirth(List<Animal> newAnts)
    {
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Ant young = new Ant(false, field, loc);
            newAnts.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if an ant can breed.
     * An ant needs to meet another 
     * ant of opposite sex to breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}

