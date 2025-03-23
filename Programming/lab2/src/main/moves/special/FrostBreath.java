package src.main.moves.special;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;
import java.lang.Math;

public class FrostBreath extends SpecialMove {
    public FrostBreath() {
        super(Type.ICE, 60, 90);
    }

    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
            System.out.println("Критический удар!");
            return 1.5;
    }

    @Override
    protected String describe() {
        return "использует Frost Breath";
    }
}
