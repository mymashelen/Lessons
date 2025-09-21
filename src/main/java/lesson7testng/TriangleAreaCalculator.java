package lesson7testng;

public class TriangleAreaCalculator {
    public static double calculateTriangleArea(double a, double b, double c){
        if (a + b <= c || c + a <= b || c + b <= a){
            throw new IllegalArgumentException("Сумма любых двух сторон должна быть больше третьей!");
        }
        if (a <= 0.0 || b <= 0.0 || c <= 0.0){
            throw new IllegalArgumentException("Длина сторон треугольника должна быть больше 0");
        }
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
