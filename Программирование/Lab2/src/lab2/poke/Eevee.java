package lab2.poke;

import lab2.moves.BabyDollEyes;
import lab2.moves.Rest;
import lab2.moves.ShadowBall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Eevee extends Pokemon {
    public Eevee(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(55, 55, 50, 45, 65, 55); // hp, att, def, spec_att, spec_def, speed
        setMove(new Rest(), new ShadowBall(), new BabyDollEyes());
    }
}