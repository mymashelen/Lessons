import lesson7testng.NumberComparator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NumberComparatorTest {
    @Test
    public void testAGreaterThanB() {
        Assert.assertEquals(NumberComparator.compare(5, 3), "5 > 3");
    }

    @Test
    public void testALessThanB() {
        Assert.assertEquals(NumberComparator.compare(3, 5), "3 < 5");
    }

    @Test
    public void testAEqualsB() {
        Assert.assertEquals(NumberComparator.compare(4, 4), "4 = 4");
    }
}
