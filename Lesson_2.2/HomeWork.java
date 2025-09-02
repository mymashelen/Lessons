import java.lang.reflect.Array;
import java.util.Arrays;

public class HomeWork {
    public static void main(String[] args) {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
        System.out.println(checkSum(5,16));
        printNumberSign(1);
        System.out.println(isNegative(0));
        repeatString("TEST", 4);
        System.out.println(isLeapYear(200));
        invertBinaryArray();
        fillArrayWithSequence();
        multiplySmallNumbers();
        fillDiagonal(5);
        generateArrayWithValue(6, 7);
    }

    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    public static void checkSumSign() {
        int a = 56;
        int b = -34;
        if (a + b < 0) {
            System.out.println("Сумма отрицательная");
        } else {
            System.out.println("Сумма положительная");
        }
    }

    public static void printColor() {
        int value = 98;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value > 100) {
            System.out.println("Зеленый");
        } else {
            System.out.println("Желтый");
        }
    }

    public static void compareNumbers() {
        int a = 5;
        int b = 45;
        if (a >= b){
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

    public static boolean checkSum(int a, int b) {
        return a + b >= 10 && a + b <= 20;
    }

    public static void printNumberSign(int a) {
        if (a >= 0) {
            System.out.println("Число является положительным");
        } else {
            System.out.println("Число является отрицательным");
        }
    }

    public static boolean isNegative(int a){
        return a < 0;
    }

    public static void repeatString(String text, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(text);
        }
    }

    public static boolean isLeapYear(int year){
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static void invertBinaryArray(){
        int[] nums = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1){
                nums[i] = 0;
            } else {
                nums[i] = 1;
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void fillArrayWithSequence(){
        int[] nums = new int[100];
        for (int i = 0; i < 100; i++){
            nums[i] = i + 1;
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void multiplySmallNumbers() {
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void fillDiagonal(int n) {
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || j == n - i -1){
                    arr[i][j] = 1;
                }
            }
            }
        System.out.println(Arrays.deepToString(arr));
    }

    public static void generateArrayWithValue(int len, int initialValue){
        int[] arr = new int[len];
        for (int i = 0; i < len; i++){
            arr[i] = initialValue;
        }
        System.out.println(Arrays.toString(arr));
    }
}