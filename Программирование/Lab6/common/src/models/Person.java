package models;

import java.util.Objects;

public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private String passportID;
    private EyeColor eyeColor; // Поле не может быть null
    private HairColor hairColor; // Поле не может быть null
    private Country nationality; // Поле не может быть null
    private Location location; // Поле не может быть null

    public Person(String name, String passportID, EyeColor eyeColor, HairColor hairColor, Country nationality, Location location) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть null или пустым");
        }
        if (eyeColor == null || hairColor == null || nationality == null || location == null) {
            throw new IllegalArgumentException("Поля eyeColor, hairColor, nationality и location не могут быть null");
        }
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    // Геттеры и сеттеры

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return CharSequence.compare(person.passportID, passportID) == 0 &&
                name.equals(person.name) &&
                eyeColor == person.eyeColor &&
                hairColor == person.hairColor &&
                nationality == person.nationality &&
                location.equals(person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passportID, eyeColor, hairColor, nationality, location);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + passportID +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }
    public Person(String name) {
        this.name = name;
    }

    public String getDataString(){
        String dataString = "";
        dataString += name;
        dataString += "&#%";
        dataString += passportID;
        dataString += "&#%";
        dataString += eyeColor.toString();
        dataString += "&#%";
        dataString += hairColor.toString();
        dataString += "&#%";
        dataString += nationality.toString();
        dataString += "&#%";
        dataString += location.getX();
        dataString += "&#%";
        dataString += location.getY();
        dataString += "&#%";
        dataString += location.getName();
        return dataString;
    }
    public static Person getFromString(String personString) {
        String name;
        String passportID;
        EyeColor eyeColor;
        HairColor hairColor;
        Country nationality;
        long locationX;
        int locationY;
        String locationName;
        String[] parts = personString.split("&#%");
        if (parts.length != 8) {
            return null;
        }
        name = parts[0];
        try{
            passportID = parts[1];
        } catch (IllegalArgumentException e) {
            passportID = null;
        }

        try{
            eyeColor= EyeColor.valueOf(parts[2]);
        } catch (IllegalArgumentException e) {
            eyeColor = null;
        }

        try {
            hairColor = HairColor.valueOf(parts[3]);
        } catch (IllegalArgumentException e) {
            hairColor = null;
        }

        try {
            nationality = Country.valueOf(parts[4]);
        } catch (IllegalArgumentException e) {
            nationality = null;
        }

        try{
            locationX = Long.parseLong(parts[5]);
        } catch (NumberFormatException e) {
            locationX=-1;
        }

        try {
            locationY=Integer.parseInt(parts[6]);
        } catch (NumberFormatException e) {
            locationY= Integer.parseInt(null);
        }
        locationName = parts[7];
        return new Person(name,passportID,eyeColor,hairColor,nationality,new Location(locationX,locationY,locationName));
    }
    public String getName() {
        return name;
    }
    public String getPassportID() {
        return passportID;
    }
    public EyeColor getEyeColor() {
        return eyeColor;
    }
    public HairColor getHairColor() {
        return hairColor;
    }
    public Country getNationality() {
        return nationality;
    }
    public Location getLocation() {
        return location;
    }
}
