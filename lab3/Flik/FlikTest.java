import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    /** Performs a few arbitrary tests to see if the product method is correct */

    @Test 
    public void testisSameNumber() {
        /* assertEquals for comparison of ints takes two arguments:
        assertEquals(expected, actual).
        if it is false, then the assertion will be false, and this test will fail.
        */

        assertTrue(Flik.isSameNumber(128, 128));
        //assertTrue(Flik.isSameNumber(127, 127));
        //assertTrue(Flik.isSameNumber(-5, -5));
        //assertTrue(Flik.isSameNumber(0, 0));
    }
}
