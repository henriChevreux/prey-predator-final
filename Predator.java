import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public  abstract class Predator extends Animal

{
    // The age at which a jaguar can start to breed.
    protected static final int BREEDING_AGE = 15;
    // The age to which a lion can live.
    protected static final int MAX_AGE = 200;
    
    // The food value of a single monkey. In effect, this is the
    // number of steps a jaguar can go before it has to eat again.
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
        
    protected static final int FOOD_VALUE = 16;
    
    protected static final int MAX_FOOD_VALUE = 20;

    // Individual characteristics (instance fields).
    // The jaguar's age.
    protected int age;
    // The jaguar's food level, which is increased by eating pangolins.
    protected int foodLevel;
    //Constructor of class Predator
    public Predator (boolean randomAge, Field field, Location location){
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAX_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = MAX_FOOD_VALUE;
        }
    }
    
    protected void incrementAge(){
        age++;
        if(age>MAX_AGE){
            setDead();
        }
    }

    protected void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0){
            setDead();
        }
    }
    
    protected Location findFood(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object entity = field.getObjectAt(where);
            if(entity instanceof Prey) {
                Prey prey = (Prey) entity;
                    if(prey.isAlive()) { 
                    prey.setDead();
                    feed(prey);
                    return where;
                }
            }
        }
        return null;   
    }
    
    private void feed(Prey prey)
    {
        if (prey.getFoodValue()+foodLevel<=MAX_FOOD_VALUE){foodLevel+=prey.getFoodValue();}
        else {foodLevel = MAX_FOOD_VALUE;}
    }
    
    /**
     * A jaguar can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    protected int getFoodValue(){return FOOD_VALUE;}
    
    
}
