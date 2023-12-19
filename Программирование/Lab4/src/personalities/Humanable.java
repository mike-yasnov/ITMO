package personalities;
import locations.Location;

public interface Humanable {
    String getName();
    int getAge();
    Location getLocation();
    void addSkill(Skills skills);
}
