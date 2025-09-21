import lesson7juinit5.TriangleAreaCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TriangleAreaCalculatorTest {
    @Test
    void testCalculateArea(){
        assertEquals(6.0, TriangleAreaCalculator.calculateTriangleArea(3, 4, 5), 0.01);
    }

    @Test
    void testInvalidSidesNotFormTriangle(){
        assertThrows(IllegalArgumentException.class, () ->
                TriangleAreaCalculator.calculateTriangleArea(3, 4, 10));
    }

    @Test
    void testNotPositiveSides(){
        assertThrows(IllegalArgumentException.class, () ->
                TriangleAreaCalculator.calculateTriangleArea(3, 4, -1));
    }
}
