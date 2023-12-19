package lab2.moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class DoubleSlap extends PhysicalMove {
    public DoubleSlap() {
        super(Type.NORMAL, 15, 85);
    }

    @Override
    protected double calcBaseDamage(Pokemon p1, Pokemon p2) {
        double start_power = this.power;
        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                if (3.0 / 8.0 > Math.random()) start_power += this.power;
            } else {
                if (1.0 / 8.0 > Math.random()) start_power += this.power;
            }
        }
        return (0.4 * (double) p1.getLevel() + 2.0) * start_power / 150.0;
    }

    @Override
    protected String describe() {
        return "Using Double Slap";
    }
}
