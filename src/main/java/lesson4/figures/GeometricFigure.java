package lesson4.figures;

public interface GeometricFigure {
    String getName();
    double getArea();
    double getPerimeter();
    String getFillColor();
    String getBorderColor();

    default void printInfo() {
        System.out.println("Название фигуры: " + getName());
        System.out.println("Периметр: " + getPerimeter());
        System.out.println("Площадь: " + getArea());
        System.out.println("Цвет фона: " + getFillColor());
        System.out.println("Цвет границ: " + getBorderColor());
        System.out.println();
    }
}
