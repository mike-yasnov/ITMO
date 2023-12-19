package personalities;

import exceptions.LocationNotExist;
import locations.Location;

import java.util.ArrayList;
import java.util.List;

public class Everyone implements Humanable {

    private static class ValidateLocation {
        private static boolean validateLocation(Object location) throws LocationNotExist {
            return location instanceof Location;
        }
    }

    private final List<Skills> skills = new ArrayList<Skills>();
    private Location location;

    public Everyone(Location location) throws LocationNotExist {
        if (ValidateLocation.validateLocation(location)) {
            this.location = location;
        } else {
            throw new LocationNotExist(location);
        }
    }

    public static void tell() {
        System.out.println("Все не стали рассказывать о переживаниях всему человечеству");
    }

    public static void controlWords() {
        System.out.println("Все не могли адекватно передавать охватившие нас чувства, а потом стали строго контролировать свои слова, введя своего рода цензуру.");
    }

    public static void rush() {
        System.out.print("все кинулись к иллюминаторам \n");
    }
    public static void feel() {
        System.out.println("Всех пронизывало непередаваемое ощущение чуда, переживаемое при виде этих залитых розоватым антарктическим светом громад на фоне облаков ледяной пыли, переливающейся всеми цветами радуги.");
    }
    @Override
    public String getName() {
        return "Все";
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void addSkill(Skills skills) {

    }

}

