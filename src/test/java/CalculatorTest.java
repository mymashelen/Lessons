import lesson7testng.Calculator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest {
    @Test
    public void testAdd() {
        Assert.assertEquals(Calculator.add(5, 2), 7);
    }

    @Test
    public void testSubtract() {
        Assert.assertEquals(Calculator.subtract(5, 2), 3);
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals(Calculator.multiply(5, 2), 10);
    }

    @Test
    public void testDivide() {
        Assert.assertEquals(Calculator.divide(8, 2), 4);
    }

    @Test (expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        Calculator.divide(5, 0);
    }
}