import lesson7juinit5.FactorialCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactorialCalculatorTest {
    @Test
    void testFactorialOfZero() {
        assertEquals(1, FactorialCalculator.factorial(0));
    }

    @Test
    void testFactorialOfFive() {
        assertEquals(120, FactorialCalculator.factorial(5));
    }

    @Test
    void testFactorialOfNegative() {
        int negativeNumber = -1;
        assertThrows(IllegalArgumentException.class, () ->
                FactorialCalculator.factorial(negativeNumber));
    }
}
