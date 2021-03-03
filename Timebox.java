
/**
 * The TimeBox class represents  the implementation of the logic of
 * the time for the simulator. Steps are converted into hours and days 
 * using a 12-hour clock logic and days are separated into daytime 
 * and nightime.
 *
 * @author Stanislas Jacquet and Henri Chevreux
 * @version 2021.02.28
 */
public class Timebox
{
    private static final String DAY_DISPLAY_VALUE = "Day";
    private static final String NIGHT_DISPLAY_VALUE = "Night";
    private static final String AM_DISPLAY_VALUE = "AM";
    private static final String PM_DISPLAY_VALUE ="PM";
    
    /**
     * Constructor for objects of class Timebox.
     */
    public Timebox()
    {
        
    }
    
    /**
     * Convert steps into days and returns 
     * the number of days.
     */
    public int getDay(){
        int step = Simulator.getStep();
        return step/24;
    }
    
    /**
     * Convert steps into hours of a day
     * by using a 12-hour clock logic
     * @return the hour of a day for each step.
     */
    public int getHourOfDay(){
        int step = Simulator.getStep();
        return (step+11)%12+1;
    }
    
    /**
     * Checks if it is daytime or nightime.
     * @return true if the hours are between
     * 6AM and 6PM, returs false otherwise.
     */
    public boolean isDay() {
        int step = Simulator.getStep();
        if (step%24>=6 && step%24<=18) return true;
        else return false;
    }
    
    /**
     * Checks if the current hour should be 
     * displayed as "AM" OR "PM" in the 
     * 12-hour clock logic
     * @return true if the current hour should
     * be displayed as "AM".
     */
    private boolean isAM(int step){
        if(step%24<12)
        return true;
        else return false;      
    }
    
    /**
     * Returns two different string that will be
     * diplayed on the GUI depending on whether
     * the simulator is running at night or
     * during the day.
     */
    public String getDayNightString() {
        int step = Simulator.getStep();
        if (isDay()) return DAY_DISPLAY_VALUE;
        else return NIGHT_DISPLAY_VALUE;
    }
    
    /**
     * Returns two different string that will be
     * diplayed on the GUI depending on whether
     * the current hour can be displayed as "AM"
     * or as "PM".
     */
    public String getAMAndPMString(){
        int step = Simulator.getStep();
        if (isAM(step))return AM_DISPLAY_VALUE;
        else return PM_DISPLAY_VALUE;
    }
    
    /**
     * Convert days and hours into steps 
     * @return the number of steps corresponding
     * to a number of day(s) and hour(s)
     */
    public int toSteps(int days, int hours) {
        return (days*24+hours);
    }
}
