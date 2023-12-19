package locations;

import exceptions.LocationNotExist;
import personalities.Skills;

import java.util.ArrayList;
import java.util.List;

public class Location implements Locatable {
    private final List<Skills> skills = new ArrayList<Skills>();

    private Places type;
    public String placeName;
    public String typeName;

    public Location(String name) throws LocationNotExist {
        this.placeName = name;
        System.out.println("Место - " + name  + " успешно создано!");
    }

    public void setType(Places type){
        this.type = type;
        typeName = "";
        switch (type){
            case Mountains: typeName = "Загадочные горы";
                break;
            case Airplane: typeName = "Самолет";
                break;
            case Mine: typeName = "Шахта";
                break;
            case AbstractWorld: typeName = "Загадочный мир";
                break;
        }
        System.out.println(placeName +" объявлена как " + typeName);
    }

    public Places getType() {
        return this.type;
    }

    public String getPlace() {
        return placeName == null ? "..." : placeName;
    }

    public List<Skills> getSkills() {
        return skills;
    }
    @Override
    public int hashCode() {
        return super.hashCode()+this.getPlace().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return "Место " + this.getPlace();
    }

}