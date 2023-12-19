package personalities;

import locations.Location;

import java.util.ArrayList;
import java.util.List;

public class Person implements Humanable {
    private final List<Skills> skills = new ArrayList<Skills>();
    private final String name;
    private final int age;


    public Person(String name, Integer age){
        this.name = name;
        this.age = age;
        System.out.println("Человек - " + name + " успешно создан!");
    }

    public static void lookAfter(String name1, String name2, String object) {
        System.out.println(name1 + " и " + name2 + " следили за " + object);
    }
    public static void looseWorld(String name, int age) {
        System.out.println(name + " в " + age + "-летнем возрасте потерял мир и покой, присущий человеку с нормальным рассудком и живущему в согласии с природой и ее законами");
    }
    public static void tell(String name1, String name2) {
        System.out.println(name1 + " и " + name2 + " рассказывают о переживаниях всему человечеству");
    }

    public static void notice(String name1, String name2) {
        System.out.println(name1 + " и " + name2 + " заметили загадочные снежные цилиндры");
    }

    public static void controlWords(String name1, String name2) {
        System.out.println(name1 + " и " + name2 + "не могли адекватно передавать охватившие нас чувства, а потом стали строго контролировать свои слова, введя своего рода цензуру.");
    }

    public static void drill(String name, Location location) {
        System.out.println(name + " пробурил " + location + " 3 дня назад");
    }

    public static void notice(String name) {
        System.out.println(name + " завидел впереди зубчатую линию таинственных кратеров и вершин");
    }

    public static void scream(String name) {
        System.out.println(name + " так завопил, что все кинулись к иллюминаторам");
    }

    public  static void feel(String name1, String name2) {
        System.out.println(name1 + "а и " + name2 + "а пронизывало непередаваемое ощущение чуда, переживаемое при виде этих залитых розоватым антарктическим светом громад на фоне облаков ледяной пыли, переливающейся всеми цветами радуги.");
    }
    public List<Skills> getSkills() {
        return skills;
    }

    public void addSkill(Skills skill) {
        skills.add(skill);
        System.out.println("Объекту - \"" + this.getName() + "\" успешно присвоено умение " + skill.getName()
                + " (" + skill.getInfo() + ")");
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return "Человек по имени " + this.getName();
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getAge() {return age;}
}