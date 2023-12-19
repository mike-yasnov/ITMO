package personalities;

import exceptions.*;
import locations.Location;

import java.util.ArrayList;
import java.util.List;

public class Person implements Humanable {

    private static class ValidateInfo {
        private static boolean validateLocation(Object location) throws LocationNotExist {
            return location instanceof Location;
        }

        private static boolean validateName(String name) throws InvalidName {
            return name.substring(0, 1).equals(name.substring(0, 1).toUpperCase());
        }

        public static boolean validateAge(Object age) throws InvalidAge {
            if (age instanceof Integer) {
                return (Integer) age > 0;
            }
            return false;
        }
    }

    private final List<Skills> skills = new ArrayList<Skills>();
    private final String name;
    private final Integer age;
    private Location location;

    public Person(String name, Integer age, Location location) throws InvalidAge, InvalidName, LocationNotExist {
        if (ValidateInfo.validateName(name)) {
            this.name = name;
        } else {
            throw new InvalidName(name);
        }
        if (ValidateInfo.validateAge(age)) {
            this.age = age;
        } else {
            throw new InvalidAge(String.format("%s не может являться возрастом.", age));
        }
        if (ValidateInfo.validateLocation(location)) {
            this.location = location;
        } else {
            throw new LocationNotExist(location);
        }
        System.out.println("Человек - " + name + " успешно создан!");
    }

    public Person(String name, Location location) throws InvalidName, LocationNotExist {
        if (ValidateInfo.validateName(name)) {
            this.name = name;
        } else {
            throw new InvalidName(name);
        }
        this.age = null;

        if (ValidateInfo.validateLocation(location)) {
            this.location = location;
        } else {
            throw new LocationNotExist(location);
        }
        System.out.println("Человек - " + name + " успешно создан!");
    }

    public void lookAfter(String name2, String object) {
        System.out.println(this.name + " и " + name2 + " следили за " + object);
    }

    public void looseWorld() {
        System.out.println(this.getName() + " в " + this.getAge() + "-летнем возрасте потерял мир и покой, присущий человеку с нормальным рассудком и живущему в согласии с природой и ее законами");
    }

    public void notice(String name2) {
        System.out.println(this.getName() + " и " + name2 + " заметили загадочные снежные цилиндры");
    }


    public void drill(Location location) {
        System.out.println(this.getName() + " пробурил " + location + " 3 дня назад");
    }

    public void notice() {
        System.out.println(this.getName() + " завидел впереди зубчатую линию таинственных кратеров и вершин");
    }

    public void scream() {
        System.out.printf(this.getName() + " так завопил, что ");
    }


    public List<Skills> getSkills() {
        return skills;
    }

    public void addSkill(Skills skill) {
        skills.add(skill);
        System.out.println("Объекту - \"" + this.getName() + "\" успешно присвоено умение " + skill.getName()
                + " (" + skill.getInfo() + ")");
    }

    public Location getLocation() {
        return location;
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
    public int getAge() {
        return age;
    }
}