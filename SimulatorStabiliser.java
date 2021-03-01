import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

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
    public void stabiliseByRandom()
    {
        Simulator simulator = new Simulator();
        SimulatorView view = simulator.getView();
        FieldStats stats = view.getStats();
        Field field = simulator.getField();
        stats.setViableParameter(5);
        double l,p,j,a,m,plant;
        boolean viableParameters = false;
        while (!viableParameters){
            l = Math.random();
            p = Math.random();
            j = Math.random();
            a = Math.random();
            m = Math.random();
            plant = Math.random()*0.4; //avoids simulations where plants overcrowd the field
            simulator.setProbs(l,p,j,a,m, plant);
            simulator.reset();
            simulator.simulate(25,0);
                        
            System.out.println("Lion: "+l+" Pangolin: "+p+" Jaguar: "+j+" Ant: "+a+" Monkey: "+m+" Plant: "+plant);
            if (stats.isViable(field)){viableParameters = true;System.out.print("Stable!");return;}
        }
    }
}