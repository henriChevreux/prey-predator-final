import java.util.Random;
/**
 * Write a description of class WeatherBox here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weatherbox
{
    // instance variables - replace the example below with your own
    
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
        // initialise instance variables
        
    }

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
    
    private void rainAction(){};
    private void fogAction(){
        Prey.setFoodValue(2);
    
    }
    private void sunAction(){
        Prey.resetFoodValue();
    };
    
    public String getWeather(){
        return weather;
    }
}

