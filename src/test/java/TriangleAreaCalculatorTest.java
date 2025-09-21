import lesson7testng.TriangleAreaCalculator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TriangleAreaCalculatorTest  {
    @Test
    public void testCalculateTriangleArea(){
        Assert.assertEquals(TriangleAreaCalculator.calculateTriangleArea(3,4,5),6.0, 0.01);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testInvalidSidesNotFormTriangle(){
       TriangleAreaCalculator.calculateTriangleArea(3, 4, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testNotPositiveSides(){
        TriangleAreaCalculator.calculateTriangleArea(3, 4, -1);
    }
}