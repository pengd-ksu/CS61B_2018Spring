import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testIsEqualChars() {
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('%', '&'));

        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('%', '^'));
    }
    // Your tests go here.
    // Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
