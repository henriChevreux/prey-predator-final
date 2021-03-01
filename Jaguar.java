import java.util.List;
import java.util.Iterator;
/**
 * This class implements the behavior of jaguars.
 * Jaguars are predators. They have all the 
 * characteristics of predators, but have a specific
 * breeding probability, and maximum litter size.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux
 * 
 * @version 2021.02.28
 */
public class Jaguar extends Predator
{
    // The likelihood of a jaguar breeding.
    public static double BREEDING_PROBABILITY = 0.196;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    /**
     * Creates a jaguar. A jaguar is a predator and thus calls
     * the constructor from the Predator superclass.
     * A jaguar can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * These attributes are defined in the Predator class.
     * 
     * @param randomAge If true, the jaguar will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.     \
     */
    public Jaguar(boolean randomAge,Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * This is what the jaguar does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newJaguars A list to return newly born jaguars.
     */
    public void act(List<Animal> newJaguars){
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newJaguars);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not this Jaguar is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newJaguars A list to return newly born jaguars.
     */
    public void giveBirth(List<Animal> newJaguars){
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> adjLocations = field.adjacentLocations(this.getLocation());
        for (Location location : adjLocations){
            Object temp = field.getObjectAt(location);
            if (temp instanceof Jaguar){
                Jaguar adjJaguar = (Jaguar)temp;
                List<Location> free = field.getFreeAdjacentLocations(getLocation());
                int births = breed(adjJaguar);
                for(int b = 0; b < births && free.size() > 0; b++) {
                    Location loc = free.remove(0);
                    Jaguar young = new Jaguar(false, field, loc);
                    newJaguars.add(young);
                }
            }
        }
    }
     /**
     * Generate a number representing the number of births,
     * if a jaguar can breed.
     * A jaguar needs to meet another 
     * jaguar of opposite sex to breed.
     * @return The number of births (may be zero).
     */
    private int breed(Jaguar partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && partner.isMale() != this.isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}
