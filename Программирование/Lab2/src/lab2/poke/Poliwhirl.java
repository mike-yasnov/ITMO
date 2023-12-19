package lab2.poke;

import lab2.moves.MudBomb;
import lab2.moves.Psychic;
import lab2.moves.Scald;
import ru.ifmo.se.pokemon.Type;

public class Poliwhirl extends Poliwag {
    public Poliwhirl(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(65, 65, 65, 50, 50, 90); // hp, att, def, spec_att, spec_def, speed
        setMove(new Psychic(), new Scald(), new MudBomb());
    }
}
