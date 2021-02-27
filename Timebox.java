
/**
 * Write a description of class Timebox here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Timebox
{
    // instance variables - replace the example below with your own
    private static final String DAY_DISPLAY_VALUE = "Day";
    private static final String NIGHT_DISPLAY_VALUE = "Night";
    private static final String AM_DISPLAY_VALUE = "AM";
    private static final String PM_DISPLAY_VALUE ="PM";
    
    /**
     * Constructor for objects of class Timebox
     */
    public Timebox()
    {
        
    }

    public int getDay(int step){
        return step/24;
    }
    
    public int getHourOfDay(int step){
        return (step+11)%12+1;
    }
    
    public boolean isDay(int step) {
        if (step%24>=6 && step%24<=18) return true;
        else return false;
    }
    private boolean isAM(int step){
        //int hour = (step+11)%12+1;
        if(step%24<12)
        return true;
        else return false;      
    }

    public String getDayNightString(int step) {
        if (isDay(step)) return DAY_DISPLAY_VALUE;
        else return NIGHT_DISPLAY_VALUE;
    }
    public String getAMAndPMString(int step){
        if (isAM(step))return AM_DISPLAY_VALUE;
        else return PM_DISPLAY_VALUE;
    }
    
    public int toSteps(int days, int hours) {
        return (days*24+hours);
    }
}
