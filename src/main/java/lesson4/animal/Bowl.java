package lesson4.animal;

public class Bowl {
    private int food;

    public  Bowl(int food) {
        this.food = food;
    }

    public void addFood(int food) {
        this.food += food;
    }

    public boolean decreaseFood(int amount) {
        if (amount <= food) {
            food = food - amount;
            return true;
        } else {
            return false;
        }
    }
}
