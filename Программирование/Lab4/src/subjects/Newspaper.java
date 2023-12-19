package subjects;

public class Newspaper extends Subject{

    public Newspaper(String name) {
        super(name);
    }

    class Bulletin {
        private final String info;

        public Bulletin(String info) {
            this.info = info;
        }
        public String getInfo() {
            return this.info;
        }
    }
    public void printBulletins() {
        Bulletin bulletins[] = new Bulletin[6];
        bulletins[0] = new Bulletin("О нашем беспосадочном перелете");
        bulletins[1] = new Bulletin("О встрече в верхних слоях атмосферы с предательскими порывами ветра");
        bulletins[2] = new Bulletin("Об увиденной с высоты шахте, которую Лейк пробурил три дня назад на полпути к горам");
        bulletins[3] = new Bulletin("О загадочных снежных цилиндрах, замеченных ранее Амундсеном и Бэрдом");

        for (Bulletin bulletin : bulletins) {
            if (bulletin != null) {
                System.out.println(this.getName() + " напечатали бюлетени с информацией: " + bulletin.getInfo());
            }
        }
    }
}
