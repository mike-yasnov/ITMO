package lab2.moves;

import ru.ifmo.se.pokemon.*;

public class MudBomb extends SpecialMove {
    public MudBomb() {
        super(Type.GROUND, 65, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        if (Math.random() < 0.1) {
            Effect e = new Effect().turns(1).stat(Stat.ACCURACY, -6);
            p.addEffect(e);

        }
    }

    @Override
    protected String describe() {
        return "Using Mud Bomb";
    }
}

