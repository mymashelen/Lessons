package lesson7juinit5;

public class FactorialCalculator {
    public static long factorial(int n){
        if (n < 0){
            throw new IllegalArgumentException("Факториал для отрицательных чисел не определен");
        }
        long result = 1;
        for (int i = 1; i <= n; i++){
            result *= i;
        }
        return result;
    }
}
