import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Lion extends Predator
{
    // Characteristics shared by all lions (class variables).
    
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    

    /**
     * Create a lion. A lion can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
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
     * pangolins. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born lions.
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
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    public void giveBirth(List<Animal> newLions)
    {
        // New foxes are born into adjacent locations.
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
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed(Lion partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProba() && partner.isMale() != this.isMale()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
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
    

