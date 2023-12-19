package subjects;

import locations.Location;

public class Phantom extends Subject{
    private final Location location;

    public Phantom(String name, Location location) {
        super(name);
        this.location = location;
        System.out.println("Объект " + this.getName() + " успешно создан! Они находится в " + this.location);
    }

    public Location getLocation() {return location;}

    public void hide() {
        System.out.println(this.getName() + " таятся в " + this.location);
    }
}
