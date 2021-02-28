import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Ant extends Prey
{
    // Characteristics shared by all rabbits (class variables).



    // The likelihood of an ant breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;

    /**
     * Create a new ant. An ant may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the ant will have a random age.
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
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Animal> newAnts)
    {
        // New rabbits are born into adjacent locations.
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
     * if it can breed.
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
    
    
    protected double getBreedingProba(){
        return BREEDING_PROBABILITY;
    }
    
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

}

