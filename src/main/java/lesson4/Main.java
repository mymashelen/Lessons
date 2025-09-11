package lesson4;

import lesson4.animal.Animal;
import lesson4.animal.Bowl;
import lesson4.animal.Cat;
import lesson4.animal.Dog;
import lesson4.figures.Circle;
import lesson4.figures.GeometricFigure;
import lesson4.figures.Rectangle;
import lesson4.figures.Triangle;

public class Main {
    public static void main(String[] args) {
        Cat[] cats = {
                new Cat("Рыжик"),
                new Cat("Пушок"),
                new Cat("Снежок"),
                new Cat("Барсик")
        };

        Dog dog1 = new Dog("Бобик");
        Dog dog2 = new Dog("Джек");

        cats[0].run(100);
        cats[1].run(300);
        cats[2].swim(10);

        dog1.run(400);
        dog2.run(600);
        dog1.swim(5);
        dog2.swim(15);

        Bowl bowl = new Bowl(30);

        for (Cat cat : cats) {
            cat.eat(bowl, 10);
        }

        bowl.addFood(100);

        for (Cat cat : cats) {
            cat.eat(bowl, 10);
        }

        System.out.println("Всего животных: " + Animal.getAnimalCount());
        System.out.println("Из них собак: " + Dog.getDogCount());
        System.out.println("Из них котов: " + Cat.getCatCount());
        System.out.println();

        GeometricFigure circle = new Circle(10.0, "Черный", "Желтый");
        GeometricFigure rectangle = new Rectangle(3.0, 4.0, "Синий", "Зеленый");
        GeometricFigure triangle = new Triangle(3.0, 4.0, 5.0, "Красный", "Серый");

        circle.printInfo();
        rectangle.printInfo();
        triangle.printInfo();
    }
}
