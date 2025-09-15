package lesson5;

public class MyArrayDataException extends RuntimeException {
    public int row;
    public int column;
    public MyArrayDataException(int row, int column) {
        super("Преобразование невозможно, так как в ячейке [" + row + "]["
                + column + "] лежит текст или символ вместо числа.");
        this.row = row;
        this.column = column;
    }
}
