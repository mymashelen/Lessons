package lesson4.animal;

public class Animal {
    private final String name;
    private final int runLimit;
    private final int swimLimit;

    private static int animalCount = 0;

    public Animal(String name, int runLimit, int swimLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.swimLimit = swimLimit;
        animalCount++;
    }

    public String getName() {
        return name;
    }

    public static int getAnimalCount() {
        return animalCount;
    }

    public void run(int runDistance) {
        if (runDistance <= runLimit) {
            System.out.println(name + " пробежал " + runDistance + " м.");
        }else{
            System.out.println(name + " не может пробежать " + runDistance +
                    " м., потому что его лимит " + runLimit + " м.");
        }
    }

    public void swim(int swimDistance) {
        if (swimDistance <= swimLimit) {
            System.out.println(name + " проплыл " + swimDistance + " м.");
        }else{
            System.out.println(name + " не может проплыть " + swimDistance + " м."+
                    " м., потому что его лимит " + swimLimit + " м.");
        }
    }
}
