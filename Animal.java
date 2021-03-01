import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Stanislas Jacquet and Henri Chevreux
 * @version 2021.02.28 
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    //whether the animal is a male or not.
    private boolean male;
    //whether the animal is infected by the disease.
    private boolean infected;
    //the probability that an animal gets infected
    private static final double INFECTION_PROBABILITY = 0.08;
    //the probability that an animal dies because he is infected
    private static final double FATALITY_PROBABILITY = 0.01;

    // A shared random number generator
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * Animal can either be a male or a female 
     * Animal has a chance to be infected when created.
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

    /**
     * Check whether the animal is a male or not.
     * @return true if the animal is a male.
     */
    public boolean isMale(){return male;}

    /**
     * Check whether the animal is infected by the disease
     * or not.
     * @return true if the animal is infected.
     */
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

    /**
     * Describes the behavior of the disease.
     * If an animal is alive and infected 
     * then if there are other animals in its 
     * adjacent locations, the infected animal
     * has a random chance of infecting other animals.
     */
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
                    if(alive && rand.nextDouble() <= INFECTION_PROBABILITY) { 
                        infect();
                    }
                }
            }
            if (rand.nextDouble() <= FATALITY_PROBABILITY) {setDead();}
        }
    }

    /**
     * Sets the value of 'infected' variable to true
     * when a animal is infected by the disease.
     */
    protected void infect() {infected = true;}    
}
