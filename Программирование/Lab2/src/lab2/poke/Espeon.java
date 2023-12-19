package lab2.poke;

import lab2.moves.BabyDollEyes;
import lab2.moves.Confusion;
import lab2.moves.Rest;
import lab2.moves.ShadowBall;
import ru.ifmo.se.pokemon.Type;

public class Espeon extends Eevee {
    public Espeon(String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC);
        setStats(65, 65, 60, 130, 95, 110); // hp, att, def, spec_att, spec_def, speed
        setMove(new Rest(), new ShadowBall(), new BabyDollEyes(), new Confusion());
    }
}