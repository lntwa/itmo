package src.main.moves.special;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Thunderbolt extends SpecialMove {
    public Thunderbolt() {
        super(Type.ELECTRIC, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.1) {
            Effect.paralyze(def);
        }
    }

    @Override
    protected String describe() {
        return "использует ThunderBolt";
    }
}
