import locations.Location;
import locations.Places;
import personalities.Humanable;
import personalities.Person;
import personalities.Skills;
import subjects.Mountains;
import subjects.Newspaper;
import subjects.Phantom;
import subjects.Wind;


public class Main {
    public static void main(String[] args){
        Humanable author = new Person("Рассказчик", 54);
        author.addSkill(new Skills("Потерять мир и покой", null));
        author.addSkill(new Skills("Следить", "за фантомами"));
        System.out.println();

        Humanable denfort = new Person("Дэнфорт", 0);
        denfort.addSkill(new Skills("Следить", "за фантомами"));
        System.out.println();

        Location abstractworld = new Location("абстрактный мир");
        abstractworld.setType(Places.AbstractWorld);
        Phantom phantoms = new Phantom("фантомы", abstractworld);
        System.out.println();

        Newspaper newspapers = new Newspaper("Газеты");
        String info = "о нашем беспосадочном перелете; о встрече в верхних слоях атмосферы с предательскими порывами ветра; об увиденной с высоты шахте, которую Лейк пробурил три дня назад на полпути к горам, а также о загадочных снежных цилиндрах, замеченных ранее Амундсеном и Бэрдом";
        newspapers.setInfo(info);
        System.out.println();

        Location mine = new Location("шахта");
        mine.setType(Places.Mine);
        Humanable lake = new Person("Лэйк", 0);
        lake.addSkill(new Skills("Бурить", null));
        System.out.println();

        Humanable amundson = new Person("Амундсон", 0);
        amundson.addSkill(new Skills("Заметить", "цилиндры"));
        System.out.println();

        Humanable berd = new Person("Берд", 0);
        berd.addSkill(new Skills("Заметить", "цилиндры"));
        System.out.println();

        Wind wind = new Wind("Ветер");
        wind.setSpeed(11);
        System.out.println();

        Humanable larson = new Person("Ларсон", 0);
        larson.addSkill(new Skills("Завидеть", null));
        larson.addSkill(new Skills("Завопить", null));
        System.out.println();

        Location location = new Location("Горы");
        location.setType(Places.Mountains);
        Mountains mountains = new Mountains("Горы");
        mountains.setLocation(location);


        // Действия в тексте
        System.out.println();
        Person.looseWorld(author.getName(), author.getAge());
        Person.lookAfter(author.getName(), denfort.getName(), phantoms.getName());
        phantoms.hide();
        Person.tell(author.getName(), denfort.getName());
        newspapers.printBulletins();
        Person.drill(lake.getName(), mine);
        Person.notice(amundson.getName(), berd.getName());
        wind.driveCylinders();
        Person.controlWords(author.getName(), denfort.getName());
        Person.notice(larson.getName());
        Person.scream(larson.getName());
        mountains.unapproach();
        mountains.grow();
        Person.feel(author.getName(), denfort.getName());


    }
}
