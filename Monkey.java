import java.util.List;
import java.util.Iterator;
/**
 * This class implements the behavior of monkeys.
 * Monkeys are preys. They have all the 
 * characteristics of preys, but have a specific
 * breeding probability, and maximum litter size.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux
 * 
 * @version 2021.02.28
 */
public class Monkey extends Prey
{

    // The likelihood of a monkey breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    /**
     * Creates a monkey. A monkey is a prey and thus calls
     * the constructor from the Prey superclass.
     * A monkey can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * These attributes are defined in the Prey class.
     * 
     * @param randomAge If true, the monkey will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Monkey(boolean randomAge, Field field, Location location){
        super(randomAge, field, location);
    }

    /**
     * This is what the monkey does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newMonkeys A list to return newly born monkeys.
     */
    public void act(List<Animal> newMonkeys){
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newMonkeys);            
            // Move towards a source of food if found.
            Location newLocation = findPlant();
            //Location freeLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation == null) { 
                // No food found - try to move to a free location.
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
     * @param newMonkeys A list to return newly born monkeys.
     */
    public void giveBirth(List<Animal> newMonkeys){
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> adjLocations = field.adjacentLocations(this.getLocation());
        for (Location location : adjLocations){
            Object temp = field.getObjectAt(location);
            if (temp instanceof Monkey){
                Monkey adjMonkey = (Monkey)temp;
                List<Location> free = field.getFreeAdjacentLocations(getLocation());
                int births = breed(adjMonkey);
                for(int b = 0; b < births && free.size() > 0; b++) {
                    Location loc = free.remove(0);
                    Monkey young = new Monkey(false, field, loc);
                    newMonkeys.add(young);
                }
            }
        }
    }
    /**
     * Generate a number representing the number of births,
     * if a pangolin can breed.
     * A pangolin needs to meet another 
     * pangolin of opposite sex to breed.
     * @return The number of births (may be zero).
     */
    private int breed(Monkey partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && partner.isMale() != isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}

