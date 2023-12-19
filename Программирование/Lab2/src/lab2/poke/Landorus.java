package lab2.poke;

import lab2.moves.BulkUp;
import lab2.moves.Confide;
import lab2.moves.FocusBlast;
import lab2.moves.StoneEdge;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Landorus extends Pokemon {
    public Landorus(String name, int level) {
        super(name, level);
        setType(Type.GROUND, Type.FLYING);
        setStats(89, 125, 90, 115, 80, 115); // hp, att, def, spec_att, spec_def, speed
        setMove(new BulkUp(), new StoneEdge(), new FocusBlast(), new Confide());
    }
}