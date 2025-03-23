package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.physical.DoubleEdge;
import src.main.moves.special.Confusion;
import src.main.moves.status.Confide;
import src.main.moves.status.DoubleTeam;

public class Jirachi extends Pokemon {
    public Jirachi(String name, int level) {
        super(name, level);
        setType(Type.STEEL, Type.PSYCHIC);
        setStats(100, 100, 100, 100, 100,100);
        setMove(new DoubleEdge(), new Confide(), new DoubleTeam(), new Confusion());
    }
}
