import java.util.List;
import java.util.Iterator;
/**
 * Write a description of class Monkey here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Monkey extends Prey
{
    
    // The likelihood of an ant breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    /**
     * Constructor for objects of class Monkey
     */
    public Monkey(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
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

    public void giveBirth(List<Animal> newMonkeys){
        // New foxes are born into adjacent locations.
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

    private int breed(Monkey partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && partner.isMale() != isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    protected static int getFoodValue(){return foodValue;}
    
    protected double getBreedingProba(){
        return BREEDING_PROBABILITY;
    }
    
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    
    
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
}

