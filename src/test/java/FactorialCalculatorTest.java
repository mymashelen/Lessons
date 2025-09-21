import lesson7testng.FactorialCalculator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FactorialCalculatorTest {
    @Test
    public void testFactorialOfFive() {
        Assert.assertEquals(FactorialCalculator.factorial(5), 120);
    }

    @Test
    public void testFactorialOfZero() {
        Assert.assertEquals(FactorialCalculator.factorial(0), 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testFactorialOfNegative() {
        FactorialCalculator.factorial(-10);
    }
}