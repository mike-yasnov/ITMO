package lab2.poke;

import lab2.moves.Psychic;
import lab2.moves.Scald;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Poliwag extends Pokemon {
    public Poliwag(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(40, 50, 40, 40, 40, 90); // hp, att, def, spec_att, spec_def, speed
        setMove(new Psychic(), new Scald());
    }
}
