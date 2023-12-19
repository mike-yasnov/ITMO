import exceptions.*;
import locations.*;
import personalities.*;
import subjects.*;


public class Main {
    public static void main(String[] args) throws LocationNotExist, InvalidAge {
        Location plane = new Location("самолет");
        plane.setType(Places.Airplane);
        System.out.println();

        Person author = new Person("Рассказчик", 54, plane);
        author.addSkill(new Skills("Потерять мир и покой", null));
        author.addSkill(new Skills("Следить", "за фантомами"));
        System.out.println();


        Person denfort = new Person("Дэнфорт", plane);
        denfort.addSkill(new Skills("Следить", "за фантомами"));
        System.out.println();

        Location abstractworld = new Location("абстрактный мир");
        abstractworld.setType(Places.AbstractWorld);
        Phantom phantoms = new Phantom("фантомы", abstractworld);
        System.out.println();

        Newspaper newspapers = new Newspaper("Газеты");
        System.out.println();

        Location mine = new Location("шахта");
        mine.setType(Places.Mine);
        Person lake = new Person("Лэйк", mine);
        lake.addSkill(new Skills("Бурить", null));
        System.out.println();

        Person amundson = new Person("Амундсон", plane);
        amundson.addSkill(new Skills("Заметить", "цилиндры"));
        System.out.println();

        Person berd = new Person("Берд", plane);
        berd.addSkill(new Skills("Заметить", "цилиндры"));
        System.out.println();

        Wind wind = new Wind("Ветер");
        wind.setSpeed(11);
        System.out.println();

        Person larson = new Person("Ларсон", plane);
        larson.addSkill(new Skills("Завидеть", null));
        larson.addSkill(new Skills("Завопить", null));
        System.out.println();

        Location location = new Location("Горы");
        location.setType(Places.Mountains);
        Mountains mountains = new Mountains("Горы");
        mountains.setLocation(location);

        Everyone everyone = new Everyone(plane);
        everyone.addSkill(new Skills("рассказать", "всему миру"));
        everyone.addSkill(new Skills("контролировать слова", null));
        everyone.addSkill(new Skills("кинуться", null));
        everyone.addSkill(new Skills("ощущать", "чудо"));



        // Действия в тексте
        System.out.println();
        author.looseWorld();
        author.lookAfter(denfort.getName(), phantoms.getName());
        phantoms.hide();
        everyone.tell();
        newspapers.printBulletins();
        lake.drill(lake.getLocation());
        amundson.notice(berd.getName());
        wind.driveCylinders();
        everyone.controlWords();
        larson.notice();
        larson.scream();
        everyone.rush();
        mountains.unapproach();
        mountains.grow();
        everyone.feel();


    }
}
