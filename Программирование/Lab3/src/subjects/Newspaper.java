package subjects;

public class Newspaper extends Subject{
    private String info;

    public Newspaper(String name) {
        super(name);
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public void printBulletins() {
        System.out.println(this.getName() + " напечатали бюлетени с информацией: " + this.info);
    }
}
