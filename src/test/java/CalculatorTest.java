import lesson7juinit5.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    void testAdd() {
        assertEquals(7, Calculator.add(5, 2));
    }

    @Test
    void testSubtract() {
        assertEquals(3, Calculator.subtract(5, 2));
    }

    @Test
    void testMultiply() {
        assertEquals(10, Calculator.multiply(5, 2));
    }

    @Test
    void testDivide() {
        assertEquals(4, Calculator.divide(8, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> Calculator.divide(5, 0));
    }
}
