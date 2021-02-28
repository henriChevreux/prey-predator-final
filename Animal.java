import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @edited by Stanislas Jacquet and Henri Chevreux
 * @version 2021.02.28 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    private boolean male;

    private boolean infected;

    private static final double INFECTION_PROBABILITY = 0.2;
    
    private static final double FATALITY_PROBABILITY = 0.01;

    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        male = rand.nextBoolean();
        infected = false;
        
        if (rand.nextDouble() <= INFECTION_PROBABILITY) {infected = true;}
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    public boolean isMale(){return male;}

    public boolean isInfected(){return infected;}

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    protected void spreadInfection()
    {
        if (infected && alive) {
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(location);
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object entity = field.getObjectAt(where);
                if(entity instanceof Animal) {
                    Animal animal = (Animal) entity;
                        if(animal.isAlive() && rand.nextDouble() <= INFECTION_PROBABILITY) { 
                            animal.infect();
                        }
                }
            }
            if (rand.nextDouble() <= FATALITY_PROBABILITY) {setDead();}
        }
    }
    
    
    protected void infect() {infected = true;}
    
    protected abstract int getFoodValue();
    
}
