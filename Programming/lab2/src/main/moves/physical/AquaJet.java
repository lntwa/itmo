package src.main.moves.physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class AquaJet extends PhysicalMove {
    public AquaJet () {
        super(Type.WATER, 40, 100);
    }

    @Override
    protected String describe() {
        return "использует AquaJet";
    }
}
