package subjects;

import subjects.enums.Color;
import subjects.enums.Country;

/**
 * Класс, представляющий человека.
 */
public class Person {
    private final String name;
    private final String passportID;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;
    /**
     * Создает новый экземпляр человека.
     *
     * @param name        имя человека
     * @param passportID  паспортные данные человека
     * @param eyeColor    цвет глаз человека
     * @param hairColor   цвет волос человека
     * @param nationality национальность человека
     * @param location    местоположение человека
     */
    public Person(String name, String passportID, Color eyeColor, Color hairColor,
                  Country nationality, Location location) {
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "Person(" +
                "name='" + name + '\'' +
                "\n passportID='" + passportID + '\'' +
                "\n eyeColor=" + eyeColor +
                "\n hairColor=" + hairColor +
                "\n nationality=" + nationality +
                "\n location=" + location +
                ')';
    }

}
