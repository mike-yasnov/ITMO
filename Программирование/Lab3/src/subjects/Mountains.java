package subjects;

import locations.Location;

import java.security.PublicKey;

public class Mountains extends Subject{
    private Location location;

    public Mountains(String name) {
        super(name);
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public void unapproach() {
        System.out.println(this.getName() + " не приближались, несмотря на высокую скорость самолета");
    }
    public void grow() {
        System.out.println(this.getName() + " постепенно мрачно вырастали");
    }
}
