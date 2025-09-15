package lesson5;

public class Main {
    public static void main(String[] args) {
        String[][] array1 = {{"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };
        String[][] array2 = {{"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"}
        };
        String[][] array3 = {{"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "apple", "16"}
        };

        try {
            System.out.println("Сумма: " + getArraySum(array1));
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        }

        try {
            System.out.println("Сумма: " + getArraySum(array2));
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера: " + e.getMessage());
        }  catch (MyArrayDataException e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        }

        try {
            System.out.println("Сумма: " + getArraySum(array3));
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера: " + e.getMessage());
        }   catch (MyArrayDataException e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        }

        try {
            String value = array1[6][6];
            System.out.println("Значение: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Поймано ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
    }

    public static int getArraySum(String[][] array) throws MyArrayDataException, MyArraySizeException {
        if (array == null) {
            throw new MyArraySizeException("Массив не может быть null");
        }
        if (array.length != 4) {
            throw new MyArraySizeException("Количество строк должно быть равным 4. Получено: " + array.length);
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].length != 4) {
                throw new MyArraySizeException("Количество столбцов должно быть равным 4. Получено: " + array[i].length);
            }
        }

        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    int value = Integer.parseInt(array[i][j]);
                    sum = sum + value;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return sum;
    }
}
