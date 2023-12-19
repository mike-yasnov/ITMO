package lab2.poke;

import lab2.moves.DoubleSlap;
import lab2.moves.MudBomb;
import lab2.moves.Psychic;
import lab2.moves.Scald;
import ru.ifmo.se.pokemon.Type;

public class Poliwrath extends Poliwhirl {
    public Poliwrath(String name, int level) {
        super(name, level);
        setType(Type.WATER, Type.FIGHTING);
        setStats(90, 95, 95, 70, 90, 70); // hp, att, def, spec_att, spec_def, speed
        setMove(new Psychic(), new Scald(), new MudBomb(), new DoubleSlap());
    }
}