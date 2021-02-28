import java.util.Random;
/**
 * This class corresponds to the implementation of 
 * the weather logic for the simulator. 
 * The weather can be foggy rainy or sunny and changes randomly
 * every 20 steps.
 *
 * @author Stanislas Jacquet and Henri Chevreux
 * @version 2021.02.28
 */
public class Weatherbox
{    
    private static final int WEATHER_CHANGE_FREQUENCY = 20;
    
    private static final double RAIN_PROBABILITY = 0.2;
    
    private static final double FOG_PROBABILITY = 0.7;
    
    private String weather;
    
    protected static final Random rand = Randomizer.getRandom();
    
    /**
     * Constructor for objects of class WeatherBox
     */
    public Weatherbox()
    {
        
    }
    /**
     * Changes the status of the weather randomly
     * every X number of steps.
     */

    public void generateWeather()
    {
        int step = Simulator.getStep();
        if (step % WEATHER_CHANGE_FREQUENCY == 0){
            if (rand.nextDouble() <= RAIN_PROBABILITY){
                setWeather("rain");
            }
            else if (rand.nextDouble() <= FOG_PROBABILITY){
                setWeather("fog");
            }
            else {
                setWeather("sun");
            }
        }
    }
    /**
     * Sets the weather to one of the three weather status.
     * Each weather status correspond to one case in the switch
     * statement and has a particular behavior.
     * Throws an Exception if the weather input type does not
     * correspond to one of the three accepted inputs.
     */
    public void setWeather(String weatherString) throws IllegalArgumentException
    {
        switch(weatherString) {  
            case "rain":
                weather = weatherString;
                rainAction();
                //implement rain weather logic
                break;
            case "fog":
                weather = weatherString;
                fogAction();
                //implement fog weather logic
                break;
            case "sun":
                weather = weatherString;
                sunAction();
                //implement sun weather logic (default stats)
                break;
            default:
                throw new IllegalArgumentException("Error: invalid weather type input");
        }
    }

     /**
     * Defines the behavior of a rainy weather
     */
    private void rainAction(){
    Plant.divideGrowthRate();
    Prey.resetFoodValue();
    }
     /**
     * Defines the behavior of a foggy weather.
     */
    private void fogAction(){
        Prey.setFoodValue(2);
        Plant.resetGrowthRate();
    }
     /**
     * Defines the behavior of a sunny weather.
     */
    private void sunAction(){
        Prey.resetFoodValue();
        Plant.resetGrowthRate();
    }
      /** 
     * Gets the current weather.
     * @return a string correponding to 
     * the current weather status.
     */
        public String getWeather(){
        return weather;
    }
}

