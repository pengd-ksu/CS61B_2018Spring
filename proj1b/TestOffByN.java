import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator OffByN = new OffByN(5);

    @Test
    public void testIsEqualChars() {
        assertTrue(OffByN.equalChars('A', 'F'));
        assertTrue(OffByN.equalChars('g', 'b'));

        assertFalse(OffByN.equalChars('a', 'e'));
    }
    // Your tests go here.
    // Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
