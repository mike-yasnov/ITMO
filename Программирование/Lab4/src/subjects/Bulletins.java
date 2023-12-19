package subjects;

public class Bulletins extends Subject {
    private String info;

    public Bulletins(String name) {
        super(name);
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
