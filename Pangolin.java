import java.util.List;
import java.util.Iterator;
/**
 * This class implements the behavior of pangolins.
 * Pangolins are preys. They have all the 
 * characteristics of preys, but have a specific
 * breeding probability, and maximum litter size.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux
 * 
 * @version 2021.02.28 
 */
public class Pangolin extends Prey
{
    // The likelihood of a pangolin breeding.
    public static double BREEDING_PROBABILITY = 0.59;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    /**
     * Creates a pangolin. A jaguar is a prey and thus calls
     * the constructor from the Prey superclass.
     * A pangolin can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * These attributes are defined in the Prey class.
     * 
     * @param randomAge If true, the pangolin will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Pangolin(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * This is what the pangolin does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newPangolins A list to return newly born pangolins.
     */
    public void act(List<Animal> newPangolins)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newPangolins);            
            // Move towards a source of food if found.
            Location newLocation = findPlant();

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
     * Check whether or not this pangolin is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPangolins A list to return newly born pangolins.
     */
    public void giveBirth(List<Animal> newPangolins)
    {
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> adjLocations = field.adjacentLocations(this.getLocation());
        for (Location location : adjLocations){
            Object temp = field.getObjectAt(location);
            if (temp instanceof Pangolin){
                Pangolin adjPangolin = (Pangolin)temp;
                List<Location> free = field.getFreeAdjacentLocations(getLocation());
                int births = breed(adjPangolin);
                for(int b = 0; b < births && free.size() > 0; b++) {
                    Location loc = free.remove(0);
                    Pangolin young = new Pangolin(false, field, loc);
                    newPangolins.add(young);
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
    private int breed(Pangolin partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && partner.isMale() !=isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}

