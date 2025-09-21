import lesson7juinit5.NumberComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberComparatorTest {
    @Test
    void testAGreaterThanB() {
        assertEquals("5 > 3", NumberComparator.compare(5, 3));
    }

    @Test
    void testALessThanB() {
        assertEquals("3 < 5", NumberComparator.compare(3, 5));
    }

    @Test
    void testAEqualsB() {
        assertEquals("4 = 4", NumberComparator.compare(4, 4));
    }
}
