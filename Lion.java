import java.util.List;
import java.util.Iterator;
/**
 * This class implements the behavior of lions.
 * Lions are predators. They have all the 
 * characteristics of predators, but have a specific
 * breeding probability, and maximum litter size.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux
 * 
 * @version 2021.02.28
 */
public class Lion extends Predator
{
    // The likelihood of a lion breeding.
    private static double breedingProbability = 0.5614;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    
    /**
     * Creates a lion. A lion is a predator and thus calls
     * the constructor from the Predator superclass.
     * A lion can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * These attributes are defined in the Predator class.
     * 
     * @param randomAge If true, the lion will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lion(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * This is what the lion does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newLions A list to return newly born lions.
     */
    public void act(List<Animal> newLions)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newLions);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
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
     * Check whether or not this Lion is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newLions A list to return newly born lions.
     */
    public void giveBirth(List<Animal> newLions)
    {
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> adjLocations = field.adjacentLocations(this.getLocation());
        for (Location location : adjLocations){
            Object temp = field.getObjectAt(location);
            if (temp instanceof Lion){
                Lion adjLion = (Lion)temp;
                List<Location> free = field.getFreeAdjacentLocations(getLocation());
                int births = breed(adjLion);
                for(int b = 0; b < births && free.size() > 0; b++) {
                    Location loc = free.remove(0);
                    Lion young = new Lion(false, field, loc);
                    newLions.add(young);
                }
            }
        }
    }
     /**
     * Generate a number representing the number of births,
     * if a lion can breed.
     * A lion needs to meet another 
     * lion of opposite sex to breed.
     * @return The number of births (may be zero).
     */
    private int breed(Lion partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability && partner.isMale() != this.isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    /**
     * Gets the associated field of the simulator.
     * @param newBreedingProba The new double probability between 0 and 1.
     */
    public static void setBreedingProbability(double newBreedingProba)
    {
        if(newBreedingProba>=0.00 && newBreedingProba<=1.00){
            breedingProbability=newBreedingProba;
        }
    }
}
    

