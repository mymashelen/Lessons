package lesson4.animal;

public class Cat extends Animal {
    private boolean fed;

    private static int catCount = 0;

    public Cat(String name) {
        super(name, 200, 0);
        this.fed = false;
        catCount++;
    }

    public boolean isFed() {
        return fed;
    }

    public static int getCatCount() {
        return catCount;
    }

    public void eat(Bowl bowl, int amount) {
        if (bowl.decreaseFood(amount)) {
            fed = true;
            System.out.println(getName() + " поел еды и теперь сыт");
        }else {
            System.out.println(getName() + " не смог поесть. Недостаточно еды в миске");
        }
    }

    @Override
    public void swim(int swimDistance) {
        System.out.println(getName() + " не умеет плавать");
    }
}
