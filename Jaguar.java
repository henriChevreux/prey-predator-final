import java.util.List;
import java.util.Iterator;
/**
 * Write a description of class Jaguar here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Jaguar extends Predator
{
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    
    

    /**
     * Constructor for objects of class Jaguar
     */
    public Jaguar(boolean randomAge,Field field, Location location)
    {
        // initialise instance variables
        super(randomAge, field, location);
    }

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

    
    
    
    public void giveBirth(List<Animal>newJaguars){
        // New foxes are born into adjacent locations.
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
    
    
    private int breed(Jaguar partner)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY && partner.isMale() != this.isMale()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}

