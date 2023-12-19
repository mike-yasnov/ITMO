package subjects;

public class Wind extends Subject {
    private int speed;

    public Wind(String name) {
        super(name);
    }

    public int getSpeed() {return this.speed;}
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void driveCylinders() {
        System.out.println(getName() + " гнал цилиндры со скоростью " + getSpeed() + " м/c");
    }
}
