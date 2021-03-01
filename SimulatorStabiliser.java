import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
//import java.Math;

/**
 * The test class SimulatorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SimulatorStabiliser
{
    /**
     * Default constructor for test class SimulatorTest
     */
    public SimulatorStabiliser()
    {
        
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void stabiliseByIteration()
    {
        Simulator simulator = new Simulator();
        double step = 0.01;
        double init = 0.02;
        double max = 0.15;
        SimulatorView view = simulator.getView();
        FieldStats stats = view.getStats();
        Field field = simulator.getField();
        stats.setViableParameter(5);

        for (double l=init; l<max; l+=step){
            for (double p=init; p<max; p+=step){
                for (double j=init; j<max; j+=step){
                    for (double a=init; a<max; a+=step){
                        for (double m=init; m<max; m+=step){
                            
                            simulator.setProbs(l,p,j,a,m);
                            simulator.reset();
                            simulator.simulate(10,0);
                        
                            System.out.println("Lion: "+l+" Pangolin: "+p+" Jaguar: "+j+" Ant: "+a+" Monkey: "+m);
                            if (stats.isViable(field)){System.out.print("Stable!");return;}
                            
                        }
                    }
                }
            }
        }
        
    }
    
    @Test
    public void stabiliseByRandom()
    {
        Simulator simulator = new Simulator();
        
        Random rand = Randomizer.getRandom();
        double min = 0.02;
        double max = 0.15;
        SimulatorView view = simulator.getView();
        FieldStats stats = view.getStats();
        Field field = simulator.getField();
        stats.setViableParameter(5);
        double l,p,j,a,m;
        boolean viableParameters = false;
        while (!viableParameters){
            l = Math.random();
            p = Math.random();
            j = Math.random();
            a = Math.random();
            m = Math.random();
            simulator.setProbs(l,p,j,a,m);
            simulator.reset();
            simulator.simulate(21,0);
                        
            System.out.println("Lion: "+l+" Pangolin: "+p+" Jaguar: "+j+" Ant: "+a+" Monkey: "+m);
            if (stats.isViable(field)){viableParameters = true;System.out.print("Stable!");return;}
        }
    }
}