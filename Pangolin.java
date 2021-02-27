import java.util.List;
import java.util.Iterator;
/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Pangolin extends Prey
{
    // Characteristics shared by all rabbits (class variables).
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
   
   /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Pangolin(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
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
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Animal> newPangolins)
    {
        // New foxes are born into adjacent locations.
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
     * if it can breed.
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
    
    protected double getBreedingProba(){return BREEDING_PROBABILITY;}
    
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
}

