package src.main.moves.physical;

import ru.ifmo.se.pokemon.*;

public class Crunch extends PhysicalMove {
    public Crunch() {
        super(Type.DARK, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon def) {
        Effect e = new Effect().chance(0.1).stat(Stat.DEFENSE, -1);
        def.setCondition(e);
    }

    @Override
    protected String describe() {
        return "использует Crunch";
    }
}
