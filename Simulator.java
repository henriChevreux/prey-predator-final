import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A predator-prey simulator, based on a rectangular field
 * containing 5 species : 2 predator species (lion and jaguar) 
 * and 3 prey species (ant, pangolin, monkey) that get eaten by predators.
 * This simulator also simulates the behavior of plants (plants are considered
 * to be any type of plants).
 * 
 * 
 * @author David J. Barnes and Michael Kölling, Stanislas Jacquet, Henri Chevreux
 * @version 2021.02.28 
 */
public class Simulator
{
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 180;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 120;
    // The probability that a Lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.03;
    // The probability that a Pangolin will be created in any given grid position.
    private static final double PANGOLIN_CREATION_PROBABILITY = 0.08;  
    // The probability that a Jaguar will be created in any given grid position.
    private static final double JAGUAR_CREATION_PROBABILITY = 0.03;
    // The probability that an Ant will be created in any given grid position.
    private static final double ANT_CREATION_PROBABILITY = 0.08;
    // The probability that a Monkey will be created in any given grid position.
    private static final double MONKEY_CREATION_PROBABILITY = 0.08;
    // The probability that a Plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.03;

    // List of animals in the field.
    private List<Animal> animals;
    //list of plants in the field.
    private List<Plant> plants;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private static int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // A timebox object to manipulate time-related information
    private Timebox timebox;
    // A weatherbox object to manipulate weather-related information
    private Weatherbox weatherbox;
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        plants = new ArrayList<>();
        field = new Field(depth, width);
        

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Pangolin.class, Color.PINK);
        view.setColor(Lion.class, Color.ORANGE);
        view.setColor(Monkey.class, Color.BLUE);
        view.setColor(Ant.class, Color.RED);
        view.setColor(Jaguar.class, Color.BLACK);
        view.setColor(Plant.class, Color.GREEN);
        
        //Create a timebox object
        timebox = new Timebox();
        
        //Create a weatherbox object and
        //sets the current weather to "sun".
        weatherbox = new Weatherbox();
        weatherbox.setWeather("sun");

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(0, 4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numDays, int numHours)
    {
        //convert the input in steps
        int numSteps = timebox.toSteps(numDays, numHours);

        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            //delay(100);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * predators and prey only during the day
     * Iterate over the whole field to determine free locations
     * in order to add new plants to random free locations 
     * in the field, only during the day.
     */
    public void simulateOneStep()
    {
        step++;
        weatherbox.generateWeather();
        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all anoimals act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            if(timebox.isDay()){
                animal.act(newAnimals);
                animal.spreadInfection();
            }
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        
        Random rand = Randomizer.getRandom();
        //let all plants act.
        if (timebox.isDay()){
            for(int row = 0; row < field.getDepth(); row++) {
                for(int col = 0; col < field.getWidth(); col++) {
                    Object element = field.getObjectAt(row, col);
                    if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Plant plant = new Plant(true, field, location);
                    }
                }
            }
        }
        
        
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        

        view.showStatus(timebox.getDay(), timebox.getHourOfDay(), 
            timebox.getDayNightString(),timebox.getAMAndPMString(),weatherbox.getWeather(), field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(timebox.getDay(), timebox.getHourOfDay(), 
            timebox.getDayNightString(),timebox.getAMAndPMString(),weatherbox.getWeather(), field);
    }

    /**
     * Randomly populate the field with predators,preys and plants.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    
                    Lion lion = new Lion(true, field, location);
                    animals.add(lion);
                }
                else if(rand.nextDouble() <= PANGOLIN_CREATION_PROBABILITY) {
                    
                    Pangolin pangolin = new Pangolin(true, field, location);
                    animals.add(pangolin);
                } else if (rand.nextDouble() <= MONKEY_CREATION_PROBABILITY) {
                    
                    Monkey monkey = new Monkey(true, field, location);
                    animals.add(monkey);
                    // else leave the location empty.
                }else if(rand.nextDouble() <= ANT_CREATION_PROBABILITY) {
                    
                    Ant ant = new Ant(true, field, location);
                    animals.add(ant);  
                } else if (rand.nextDouble() <= JAGUAR_CREATION_PROBABILITY) {
                    
                    Jaguar jaguar = new Jaguar(true, field, location);
                    animals.add(jaguar);
                } else if (rand.nextDouble() <= PLANT_CREATION_PROBABILITY){
                    
                    Plant plant = new Plant (true, field, location);
                    plants.add(plant);
                }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    /**
     * Gets the current number of steps since start of simulation
     * @return total number of steps as integer.
     */
    public static int getStep()
    {
        return step;
    }
}

